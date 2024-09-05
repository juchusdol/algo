package com.example.algo.adapters.infrastructure.mongo

import com.example.algo.application.domain.model.CharacterModel
import com.example.algo.application.domain.repository.CommandRepository
import com.example.algo.application.outbound.dto.CharacterTimeLine
import lombok.extern.slf4j.Slf4j
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository

@Slf4j
@Repository
class CommandRepositoryImpl(
    private val characterRepository: SpringDataMongoCharacterRepository,
    private val template: MongoTemplate
) : CommandRepository {

    override fun createCharacter(request: CharacterModel) {
        characterRepository.insert(
            request
        )
    }
}