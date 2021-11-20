package com.forbusypeople.budget.controllers;

import com.forbusypeople.budget.services.dtos.RoomsDto;
import com.forbusypeople.budget.services.properties.RoomsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomsController {

    private final RoomsService roomsService;

    @GetMapping
    public List<RoomsDto> getAllRooms() {
        return roomsService.getAll();
    }

    @PostMapping
    private UUID saveOrUpdateRoom(@RequestBody RoomsDto dto) {
        return roomsService.saveOrUpdate(dto);
    }

    @DeleteMapping("/{id}")
    private void inactiveRoom(@PathVariable("id") String id) {
        roomsService.inactiveRoom(UUID.fromString(id));
    }

}
