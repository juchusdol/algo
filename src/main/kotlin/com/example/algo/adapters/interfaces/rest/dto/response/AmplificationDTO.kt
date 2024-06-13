package com.example.algo.adapters.interfaces.rest.dto.response


data class AmplificationDTO(
    var amplificationCount: Double = 0.0,
    var cost: String = "",
    var crystal: String = "",
    var standardDeviation: String = "",
    var median: String = "",
    var top10: String = "",
    var low10: String = "",
    var failCount: Double = 0.0,
    var destroyedCount: Double = 0.0,
    var crystalPrice: String = ""
)
