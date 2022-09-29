package com.example.algo.adapters.interfaces.rest.dto.request

data class Status(
    val hp: Int = 0,
    val mp: Int = 0,
    val str: Int = 0,
    val dex: Int = 0,
    val int: Int = 0,
    val luk: Int = 0,
    )

data class Power(
    val minimum: Int = 0,
    val maximum: Int = 0,
)

data class Damage(
    val normal: Int = 0,
    val boss: Int = 0,
    val critical: Int = 0,
    val gongMa: Int = 0,
    val bangMu: Double = 0.0,
)

data class CharacterRequest(
    val name: String = "",
    val status: Status,
    val power: Power,
    val damage: Damage,
)