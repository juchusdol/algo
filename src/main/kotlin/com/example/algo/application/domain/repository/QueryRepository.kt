package com.example.algo.application.domain.repository

import com.example.algo.adapters.interfaces.rest.dto.response.CharacterResponse

interface QueryRepository {
    fun getCharacter(): List<CharacterResponse>
    fun getCharacter(name: String, boss: Int): List<CharacterResponse>
}