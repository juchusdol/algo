package com.example.algo.application.outbound.dto

import com.fasterxml.jackson.annotation.JsonFormat
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.Setter
import java.time.LocalDateTime

@AllArgsConstructor
@Getter
@Setter
data class DateObject(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private val start:LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private val end:LocalDateTime,
)
