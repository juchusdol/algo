package com.example.algo.application.domain.repository

import com.example.algo.application.outbound.dto.CharacterBase

interface DfQueryRepository {
    fun findCharacterByCharacterId(id: String): CharacterBase?
    fun findCharacterAllByAdventureName(name: String): List<CharacterBase>?
}