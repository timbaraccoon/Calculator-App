package com.calc.controller;

import com.calc.controller.dto.TimeInterval;
import com.calc.entity.Calculation;
import com.calc.service.CalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestCalcController {

    private final CalculationService calculationService;

    @Autowired
    public RestCalcController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @PostMapping("/calculate")
    public Calculation processExpression(@RequestBody String expression) {
        return calculationService.process(expression);
    }

    @GetMapping("/timeInterval")
    public List<Calculation> getAllinInterval(@RequestBody TimeInterval interval) {
        return calculationService.findAllInTimeInterval(interval);
    }

    @GetMapping("/operation")
    public List<Calculation> getListByOperation(@RequestBody String operation) {
        return calculationService.findByOperation(operation);
    }



    @GetMapping("/interval")
    public TimeInterval getInterval(@RequestBody TimeInterval interval) {
        return interval;
    }


}
