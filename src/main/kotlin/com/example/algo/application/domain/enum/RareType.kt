package com.example.algo.application.domain.enum

enum class RareType(
    val eventCode: String = "0"
) {
    RARE("0"),        // 레어
    EPIC("1"),        // 에픽
    UNIQUE("2"),      // 유니크
    LEGENDARY("3"),   // 레전더리
}