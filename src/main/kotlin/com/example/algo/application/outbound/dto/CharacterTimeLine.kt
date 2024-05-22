package com.example.algo.application.outbound.dto

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.Setter

@AllArgsConstructor
@Getter
@Setter
data class CharacterTimeLine(
    private val characterId: String,
    private val characterName: String,
    private val level: Int,
    private val jobId: String,
    private val jobGrowId: String,
    private val jobName: String,
    private val jobGrowName: String,
    private val adventureName: String,
    private val guildId: String,
    private val guildName: String,
    private val timeline: TimeLine,
)
