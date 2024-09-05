package com.example.algo.application.outbound.dto

data class GroupByChannel(
    var name: String = "",
    val groupByNos: MutableList<GroupByChannelNo> = mutableListOf(),
    var dropCount: Int = 0,
    var mistgearCount: Int = 0,
    var beginningCount: Int = 0
)
