package com.example.algo.application.outbound.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class DateObject(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    var start:LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    var end:LocalDateTime,
)
