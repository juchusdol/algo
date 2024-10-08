package com.example.algo.application.outbound.dto

import org.bson.types.ObjectId


data class CharacterInfo(
    override var _id: ObjectId?,
    var serverId: String,
    override var characterId: String,
    override var characterName: String,
    override var level: Int,
    override var jobId: String,
    override var jobGrowId: String,
    override var jobName: String,
    override var jobGrowName: String,
    var fame: Int
) : CharacterBase