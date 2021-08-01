package com.forbusypeople.budget.controllers;

import com.forbusypeople.budget.enums.MonthsEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/calendar")
public class CalendarController {

    @GetMapping("/months")
    public List<MonthsEnum> getMonths() {
        return Arrays.asList(MonthsEnum.values());
    }

    @GetMapping("/months/days")
    public Map<MonthsEnum, String> getMonthsWithDays() {
        return MonthsEnum.getMonthsWithMaxDays();
    }
}
