package com.forbusypeople.budget.controllers;

import com.forbusypeople.budget.services.dtos.ExpensesDto;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpensesController {

    @GetMapping
    public List<ExpensesDto> getAllExpenses() {
        throw new NotYetImplementedException();
    }

    @PostMapping
    public void setExpenses(@RequestBody ExpensesDto expensesDto) {
        throw new NotYetImplementedException();
    }

    @PutMapping
    public void updateExpenses(@RequestBody ExpensesDto expensesDto) {
        throw new NotYetImplementedException();
    }

    @DeleteMapping
    public void deleteExpenses(@RequestBody ExpensesDto expensesDto) {
        throw new NotYetImplementedException();
    }
    
}
