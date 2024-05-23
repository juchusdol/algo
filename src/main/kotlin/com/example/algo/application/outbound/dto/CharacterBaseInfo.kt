package com.example.algo.application.outbound.dto


data class CharacterBaseInfo(
    override var characterId: String,
    override var characterName: String,
    override var level: Int,
    override var jobId: String,
    override var jobGrowId: String,
    override var jobName: String,
    override var jobGrowName: String,
    var adventureName: String,
    var guildId: String,
    var guildName: String,
): CharacterBase