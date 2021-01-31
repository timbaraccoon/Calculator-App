package com.calc.controller.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeInterval {

    private final LocalDateTime from;
    private final LocalDateTime to;
}
