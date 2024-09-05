package com.example.algo.application.outbound.dto

import org.bson.types.ObjectId

interface CharacterBase {
    var _id: ObjectId?
    var characterId: String
    var characterName: String
    var level: Int
    var jobId: String
    var jobGrowId: String
    var jobName: String
    var jobGrowName: String
}

