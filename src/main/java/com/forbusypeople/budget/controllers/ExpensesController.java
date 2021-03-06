package com.forbusypeople.budget.controllers;

import com.forbusypeople.budget.services.ExpensesService;
import com.forbusypeople.budget.services.dtos.ExpensesDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/expenses")
public class ExpensesController {

    private final ExpensesService expensesService;

    public ExpensesController(ExpensesService expensesService) {
        this.expensesService = expensesService;
    }

    @GetMapping
    public List<ExpensesDto> getAllExpenses() {
        return expensesService.getAllExpenses();
    }

    @GetMapping("/filter")
    private List<ExpensesDto> getFilteredExpenses(@RequestParam Map<String, String> filter) {
        return expensesService.getFilteredExpenses(filter);
    }

    @PostMapping
    public void setExpenses(@RequestBody ExpensesDto expensesDto) {
        expensesService.setExpenses(expensesDto);
    }

    @PutMapping
    public void updateExpenses(@RequestBody ExpensesDto expensesDto) {
        expensesService.updateExpenses(expensesDto);
    }

    @DeleteMapping
    public void deleteExpenses(@RequestBody ExpensesDto expensesDto) {
        expensesService.deleteExpenses(expensesDto);
    }

}
