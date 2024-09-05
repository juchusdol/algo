package com.example.algo.adapters.interfaces.rest.dto.response

data class Status(
    var hp: Int = 0,
    var mp: Int = 0,
    var str: Int = 0,
    var dex: Int = 0,
    var int: Int = 0,
    var luk: Int = 0,
)

data class Power(
    var minimum: Int = 0,
    var maximum: Int = 0,
)

data class Damage(
    var normal: Int = 0,
    var boss: Int = 0,
    var critical: Int = 0,
    var gongMa: Int = 0,
    var bangMu: Double = 0.0,
)

data class CharacterResponse(
    var name: String = "",
    val status: Status,
    val power: Power,
    val damage: Damage,
)