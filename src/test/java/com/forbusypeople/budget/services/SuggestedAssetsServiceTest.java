package com.forbusypeople.budget.services;

import com.forbusypeople.budget.enums.AssetCategory;
import com.forbusypeople.budget.enums.RoomsType;
import com.forbusypeople.budget.services.dtos.AssetDto;
import com.forbusypeople.budget.services.dtos.PropertyDto;
import com.forbusypeople.budget.services.dtos.RoomsDto;
import com.forbusypeople.budget.services.properties.PropertyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SuggestedAssetsServiceTest {

    @Mock
    private PropertyService propertyService;

    private SuggestedAssetsService service;

    @BeforeEach
    public void setUp() {
        service = new SuggestedAssetsService(propertyService);
    }

    @Test
    void shouldGetSuggestAssetsForOnlyRentedRooms() {
        // given
        var roomsAndCostsForProperty1 = new HashMap<RoomsType, BigDecimal>() {{
            put(RoomsType.ROOM_L, new BigDecimal("5"));
            put(RoomsType.ROOM_M, new BigDecimal("4"));
        }};
        var roomsAndCostsForProperty2 = new HashMap<RoomsType, BigDecimal>() {{
            put(RoomsType.ROOM_XL, new BigDecimal("8"));
            put(RoomsType.ROOM_L, new BigDecimal("5"));
            put(RoomsType.ROOM_S, new BigDecimal("2"));
        }};

        List<RoomsDto> roomsForProperty1 = getRentedRoomsForProperty(roomsAndCostsForProperty1);
        List<RoomsDto> roomsForProperty2 = getRentedRoomsForProperty(roomsAndCostsForProperty2);

        var property1 = PropertyDto.builder()
                .city("citi1")
                .house("house1")
                .street("street1")
                .rooms(roomsForProperty1)
                .build();
        var property2 = PropertyDto.builder()
                .city("citi2")
                .house("house2")
                .street("street2")
                .rooms(roomsForProperty2)
                .build();

        var allProperties = asList(property1, property2);
        when(propertyService.findAllProperties(false)).thenReturn(allProperties);

        // when
        List<AssetDto> suggestedAssets = service.getAllRentRooms();

        // then
        assertThat(suggestedAssets).hasSize(5);
        assertAll(
                () -> suggestedAssets.forEach(
                        asset -> assertThat(asset.getCategory()).isEqualTo(AssetCategory.RENT)),
                () -> suggestedAssets.forEach(
                        asset -> assertThat(asset.getDescription())
                                .contains("City:")
                                .contains("House:")
                                .contains("Street:")
                )
        );

    }

    @Test
    void shouldGetNoSuggestAssetsForNotRentedRooms() {
        // given
        var roomsAndCostsForProperty1 = new HashMap<RoomsType, BigDecimal>() {{
            put(RoomsType.ROOM_L, new BigDecimal("5"));
            put(RoomsType.ROOM_M, new BigDecimal("4"));
        }};
        var roomsAndCostsForProperty2 = new HashMap<RoomsType, BigDecimal>() {{
            put(RoomsType.ROOM_XL, new BigDecimal("8"));
            put(RoomsType.ROOM_L, new BigDecimal("5"));
            put(RoomsType.ROOM_S, new BigDecimal("2"));
        }};

        List<RoomsDto> roomsForProperty1 = getNotRentedRoomsForProperty(roomsAndCostsForProperty1);
        List<RoomsDto> roomsForProperty2 = getNotRentedRoomsForProperty(roomsAndCostsForProperty2);

        var property1 = PropertyDto.builder()
                .city("citi1")
                .house("house1")
                .street("street1")
                .rooms(roomsForProperty1)
                .build();
        var property2 = PropertyDto.builder()
                .city("citi2")
                .house("house2")
                .street("street2")
                .rooms(roomsForProperty2)
                .build();

        var allProperties = asList(property1, property2);
        when(propertyService.findAllProperties(false)).thenReturn(allProperties);

        // when
        List<AssetDto> suggestedAssets = service.getAllRentRooms();

        // then
        assertThat(suggestedAssets).hasSize(0);

    }

    private List<RoomsDto> getRentedRoomsForProperty(HashMap<RoomsType, BigDecimal> roomsAndCosts) {
        return roomsAndCosts.entrySet().stream()
                .map(it -> RoomsDto.builder()
                        .cost(it.getValue())
                        .type(it.getKey())
                        .rent(true)
                        .build())
                .collect(Collectors.toList());
    }

    private List<RoomsDto> getNotRentedRoomsForProperty(HashMap<RoomsType, BigDecimal> roomsAndCosts) {
        return roomsAndCosts.entrySet().stream()
                .map(it -> RoomsDto.builder()
                        .cost(it.getValue())
                        .type(it.getKey())
                        .rent(false)
                        .build())
                .collect(Collectors.toList());
    }
}