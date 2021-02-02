package com.calc.service;

import com.calc.controller.dto.TimeInterval;
import com.calc.dao.CalculationRepository;
import com.calc.entity.Calculation;
import com.calc.service.components.ExpressionEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CalculationServiceImpl implements CalculationService {

    private final CalculationRepository repository;
    private final ExpressionEvaluator expressionEvaluator;

    @Autowired
    public CalculationServiceImpl(CalculationRepository repository, ExpressionEvaluator expressionEvaluator) {
        this.repository = repository;
        this.expressionEvaluator = expressionEvaluator;
    }




    @Override
    public Calculation process(String expression) {
        Calculation record = createCalculation(expression);
        repository.save(record);
        System.out.println(record);

        return record;
    }

    @Override
    public List<Calculation> findAllInTimeInterval(TimeInterval interval) {

        if (interval.getFrom() == null && interval.getTo() == null) {
            return repository.findAll();
        }
        if (interval.getFrom() == null) {
            return repository.findByTimeBefore(interval.getTo());
        }
        if (interval.getTo() == null) {
            return repository.findByTimeAfter(interval.getFrom());
        }
        return repository.findByTimeBetween(interval.getFrom(),
                                                        interval.getTo());
    }

    @Override
    public List<Calculation> findByOperation(String operation) {
        return repository.findByExpressionContainingIgnoreCase(operation);
    }

    private Calculation createCalculation(String expression) {
        Calculation record = new Calculation();
        record.setExpression(expression);
        record.setResult(expressionEvaluator.evaluate(expression));
        record.setTime(LocalDateTime.now());

        return record;
    }



}
