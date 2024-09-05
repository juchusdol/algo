package com.example.algo.adapters.infrastructure.mongo

import com.example.algo.application.domain.model.CharacterModel
import com.example.algo.application.domain.repository.CommandRepository
import com.example.algo.application.domain.repository.DfCommandRepository
import com.example.algo.application.outbound.dto.CharacterBase
import com.example.algo.application.outbound.dto.CharacterTimeLine
import lombok.extern.slf4j.Slf4j
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository

@Slf4j
@Repository
class DfCommandRepositoryImpl(
    private val characterRepository: SpringDataMongoCharacterRepositoryForDf,
    private val template: MongoTemplate
) : DfCommandRepository {

    override fun saveCharacterTimeLine(request: CharacterBase) {
        characterRepository.save(request)
    }

    override fun deleteCharacterBaseById(id: String) {
        characterRepository.deleteCharacterBaseByCharacterId(id)
    }
}