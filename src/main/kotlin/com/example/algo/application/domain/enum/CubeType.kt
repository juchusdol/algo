package com.example.algo.application.domain.enum

enum class CubeType(
    val eventCode: String = "0"
) {
    MASTER("0"),    // 장인
    GM("1"),        // 명장
    RED("2"),       // 레드큐브
    BLACK("3"),     // 블랙큐브
}