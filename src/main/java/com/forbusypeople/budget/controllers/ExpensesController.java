package com.forbusypeople.budget.controllers;

import com.forbusypeople.budget.services.ExpensesService;
import com.forbusypeople.budget.services.dtos.ExpensesDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
