package com.example.algo.adapters.interfaces.rest.dto.response

data class StarForceDTO(
    var enforceCount: Double = 0.0,
    var cost: String = "",
    var standardDeviation: String = "",
    var median: String = "",
    var top10: String = "",
    var low10: String = "",
    var destroyCount: Double = 0.0
)