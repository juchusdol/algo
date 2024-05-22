package com.example.algo.application.outbound.dto

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.Setter


@AllArgsConstructor
@Getter
@Setter
data class TimeLineData(
    private val itemId: String,
    private val itemName: String,
    private val itemRarity: String,
    private val channelName: String,
    private val channelNo: Int,
    private val dungeonName: String,
    private val mistGear: Boolean,
)
