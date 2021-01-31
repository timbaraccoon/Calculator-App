package com.calc.dao;

import com.calc.entity.Calculation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CalculationRepository extends JpaRepository<Calculation, Long> {

    List<Calculation> findByTimeBetween(LocalDateTime from, LocalDateTime to);

    List<Calculation> findByTimeAfter(LocalDateTime from);

    List<Calculation> findByTimeBefore(LocalDateTime to);

    List<Calculation> findByExpressionContainingIgnoreCase(String operation);

}
