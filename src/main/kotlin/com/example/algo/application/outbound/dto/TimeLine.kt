package com.example.algo.application.outbound.dto

data class TimeLine(
    var date: DateObject,
    var next: String?,
    var rows: List<TimeLineRow>,
)