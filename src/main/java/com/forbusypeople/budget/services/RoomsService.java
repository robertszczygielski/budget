package com.forbusypeople.budget.services;

import com.forbusypeople.budget.enums.RoomsType;
import com.forbusypeople.budget.mappers.RoomsMapper;
import com.forbusypeople.budget.repositories.RoomsRepository;
import com.forbusypeople.budget.services.dtos.RoomsDto;
import com.forbusypeople.budget.services.users.UserLogInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomsService {

    private final RoomsRepository roomsRepository;
    private final UserLogInfoService userLogInfoService;
    private final RoomsMapper roomsMapper;

    @Transactional
    public UUID saveOrUpdate(RoomsDto dto) {
        return Objects.isNull(dto.getId())
                ? saveRoom(dto)
                : updateRoom(dto);
    }

    private UUID saveRoom(RoomsDto dto) {
        var user = userLogInfoService.getLoggedUserEntity();

        var entity = roomsMapper.fromDtoToEntity(dto, user);
        var savedRoom = roomsRepository.save(entity);
        return savedRoom.getId();
    }

    private UUID updateRoom(RoomsDto dot) {
        var entityOptional = roomsRepository.findById(dot.getId());
        if (entityOptional.isPresent()) {
            roomsMapper.fromDtoToEntity(dot, entityOptional.get());
        }
        return dot.getId();
    }

    @Transactional
    public void inactiveRoom(UUID roomId) {
        var entityOptional = roomsRepository.findById(roomId);
        if (entityOptional.isPresent()) {
            var entity = entityOptional.get();
            entity.setCost(BigDecimal.ZERO);
        }
    }

    public List<RoomsDto> getAll() {
        var user = userLogInfoService.getLoggedUserEntity();
        var allRooms = roomsRepository.findAllByUser(user);
        if (allRooms.isPresent()) {
            var rooms = allRooms.get();
            return rooms.stream()
                    .map(room -> roomsMapper.formEntityToDto(room))
                    .collect(Collectors.toList());
        } else {
            return Arrays.asList(RoomsType.values()).stream()
                    .map(roomsType -> RoomsDto.builder()
                            .type(roomsType)
                            .cost(BigDecimal.ZERO)
                            .build())
                    .collect(Collectors.toList());
        }
    }
}
