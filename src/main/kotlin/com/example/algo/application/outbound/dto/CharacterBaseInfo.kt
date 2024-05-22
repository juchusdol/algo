package com.example.algo.application.outbound.dto

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.Setter

@AllArgsConstructor
@Getter
@Setter
interface CharacterBaseInfo {
    val characterId: String
    val characterName: String
    val level: Int
    val jobId: String
    val jobGrowId: String
    val jobName: String
    val jobGrowName: String
    val adventureName: String
    val guildId: String
    val guildName: String
}

