package com.example.algo.application.domain.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

data class Status(
    @Id
    val _id: ObjectId = ObjectId(),
    val hp: Int = 0,
    val mp: Int = 0,
    val str: Int = 0,
    val dex: Int = 0,
    val int: Int = 0,
    val luk: Int = 0,
)

data class Power(
    @Id
    val _id: ObjectId = ObjectId(),
    val minimum: Int = 0,
    val maximum: Int = 0,
)

data class Damage(
    @Id
    val _id: ObjectId = ObjectId(),
    val normal: Int = 0,
    val boss: Int = 0,
    val critical: Int = 0,
    val gongMa: Int = 0,
    val bangMu: Double = 0.0,
)

@Document(collection = "character")
data class CharacterModel(
    @Id
    val name: String = "",
    val status: Status,
    val power: Power,
    val damage: Damage,
)