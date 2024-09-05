package com.example.algo.application.outbound.dto

import org.bson.types.ObjectId


data class CharacterEpicStatistics(
    override var _id: ObjectId?,
    override var characterId: String,
    override var characterName: String,
    override var level: Int,
    override var jobId: String,
    override var jobGrowId: String,
    override var jobName: String,
    override var jobGrowName: String,
    var adventureName: String,
    var epicCount: Int = 0,
    var mistgearCount: Int = 0,
    var beginningCount: Int = 0,
    val groupByChannels: MutableList<GroupByChannel> = ArrayList()
) : CharacterBase