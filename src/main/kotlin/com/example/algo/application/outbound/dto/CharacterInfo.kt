package com.example.algo.application.outbound.dto


data class CharacterInfo(
    var serverId: String,
    override var characterId: String,
    override var characterName: String,
    override var level: Int,
    override var jobId: String,
    override var jobGrowId: String,
    override var jobName: String,
    override var jobGrowName: String,
    var fame: Int
): CharacterBase