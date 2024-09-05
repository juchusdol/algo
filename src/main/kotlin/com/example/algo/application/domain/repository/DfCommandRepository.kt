package com.example.algo.application.domain.repository

import com.example.algo.application.outbound.dto.CharacterBase

interface DfCommandRepository {
    fun saveCharacterTimeLine(request: CharacterBase)
    fun deleteCharacterBaseById(id: String)
}