package com.forbusypeople.budget.controllers;

import com.forbusypeople.budget.services.CyclicalExpensesService;
import com.forbusypeople.budget.services.dtos.CyclicalExpensesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cyclical")
@RequiredArgsConstructor
public class CyclicalExpensesController {

    private final CyclicalExpensesService cyclicalExpensesService;

    @GetMapping("/expenses")
    public List<CyclicalExpensesDto> getAllCyclicalExpenses() {
        return cyclicalExpensesService.getAllCyclicalExpenses();
    }

    @PostMapping("/expenses")
    public List<UUID> saveCyclicalExpenses(@RequestBody List<CyclicalExpensesDto> dtos) {
        return cyclicalExpensesService.saveCyclicalExpenses(dtos);
    }

}
