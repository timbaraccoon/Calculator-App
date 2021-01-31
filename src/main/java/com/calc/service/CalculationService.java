package com.calc.service;

import com.calc.controller.dto.TimeInterval;
import com.calc.entity.Calculation;

import java.util.List;

public interface CalculationService {

    Calculation process(String expression);

    List<Calculation> findAllInTimeInterval(TimeInterval interval);

    List<Calculation> findByOperation(String operation);

}
