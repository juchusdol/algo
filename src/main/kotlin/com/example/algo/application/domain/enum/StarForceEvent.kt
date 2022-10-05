package com.example.algo.application.domain.enum

enum class StarForceEvent(
    val eventCode: String = "4"
) {
    ONE_PLUS_ONE("0"),  // 10 이하 1+1
    SUCCESS("1"),       // 5 10 15 100%
    DISCOUNT("2"),      // 30% 할인
    SHINING("3"),       // 샤타포스 (30% + 5 10 15 100%)
    NONE("4")           // 없음
}