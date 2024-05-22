package com.example.algo.application.outbound.dto

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.Setter

@AllArgsConstructor
@Getter
@Setter
data class TimeLine(
    private val date: DateObject,
    private val next: String,
    private val rows: List<TimeLineData>,
)
