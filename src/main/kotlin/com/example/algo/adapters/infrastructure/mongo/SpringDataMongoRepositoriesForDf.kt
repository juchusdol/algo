package com.example.algo.adapters.infrastructure.mongo

import com.example.algo.application.outbound.dto.CharacterBase
import org.springframework.data.mongodb.repository.MongoRepository

interface SpringDataMongoCharacterRepositoryForDf : MongoRepository<CharacterBase, String> {
    fun findCharacterBaseByCharacterId(characterId: String): CharacterBase?
    fun deleteCharacterBaseByCharacterId(characterId: String)
}