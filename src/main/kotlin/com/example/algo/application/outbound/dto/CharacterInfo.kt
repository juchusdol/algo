package com.example.algo.application.outbound.dto

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.Setter

@AllArgsConstructor
@Getter
@Setter
data class CharacterInfo(
    private val fame: Int,
    override val characterId: String,
    override val characterName: String,
    override val level: Int,
    override val jobId: String,
    override val jobGrowId: String,
    override val jobName: String,
    override val jobGrowName: String,
    override val adventureName: String,
    override val guildId: String,
    override val guildName: String
): CharacterBaseInfo