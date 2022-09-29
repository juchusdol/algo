package com.example.algo.application.domain.repository

import com.example.algo.application.domain.model.CharacterModel

interface CommandRepository {
    fun createCharacter(request: CharacterModel)
}