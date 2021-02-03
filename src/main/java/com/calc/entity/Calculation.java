package com.calc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Calculation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "expression", nullable = false)
    private String expression;

    @Column(name = "result", nullable = false)
    private BigDecimal result;

    @Column(name = "time", nullable = false)
    private LocalDateTime time;

}
