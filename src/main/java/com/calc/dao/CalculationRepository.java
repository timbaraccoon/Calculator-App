package com.calc.dao;

import com.calc.entity.Calculation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CalculationRepository extends JpaRepository<Calculation, Long> {

    List<Calculation> findCalculationByTimeBetween(LocalDateTime from, LocalDateTime to);
    List<Calculation> findCalculationByTimeAfter(LocalDateTime from);
    List<Calculation> findCalculationByTimeBefore(LocalDateTime to);

}
