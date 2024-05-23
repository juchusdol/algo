package com.example.algo.application.outbound.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class TimeLineRow(
    var code: Int,
    var name: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    var date: LocalDateTime,
    var data: TimeLineData
)
