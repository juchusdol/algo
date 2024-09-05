package com.example.algo.adapters.infrastructure.mongo

import com.example.algo.application.domain.model.CharacterModel
import com.example.algo.application.domain.repository.CommandRepository
import com.example.algo.application.domain.repository.DfCommandRepository
import com.example.algo.application.domain.repository.DfQueryRepository
import com.example.algo.application.outbound.dto.CharacterBase
import com.example.algo.application.outbound.dto.CharacterTimeLine
import lombok.extern.slf4j.Slf4j
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Slf4j
@Repository
class DfQueryRepositoryImpl(
    private val characterRepository: SpringDataMongoCharacterRepositoryForDf,
    private val template: MongoTemplate
) : DfQueryRepository {

    override fun findCharacterByCharacterId(id: String): CharacterBase? {
        return characterRepository.findCharacterBaseByCharacterId(id)
    }

    override fun findCharacterAllByAdventureName(name: String): List<CharacterBase> {
        val criteria = Criteria("adventureName")
        criteria.`is`(name)
        val query = Query(criteria)

        println(query)

        val result = template.find(query, CharacterTimeLine::class.java, "characterBase")

        return result
    }
}