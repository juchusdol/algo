package com.example.algo.adapters.infrastructure.mongo

import com.example.algo.adapters.interfaces.rest.dto.response.*
import com.example.algo.application.domain.model.CharacterModel
import com.example.algo.application.domain.repository.QueryRepository
import lombok.extern.slf4j.Slf4j
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Repository

@Slf4j
@Repository
class QueryRepositoryImpl(
    private val characterRepository: SpringDataMongoCharacterRepository,
    private val template: MongoTemplate
): QueryRepository {
    val characterCollection = "character"

    override fun getCharacter(): List<CharacterResponse> {
        val agg = Aggregation.newAggregation(
            Aggregation.match(Criteria())
        )
        val query = template.aggregate(
            agg,
            characterCollection,
            CharacterModel::class.java
        ).mappedResults

        return getCharacterResponseOfEntity(query)
    }

    override fun getCharacter(name: String, boss: Int): List<CharacterResponse> {
        val agg = Aggregation.newAggregation(
            Aggregation.match(Criteria.where("damage.boss").`is`(boss))
        )

        val query = template.aggregate(
            agg,
            characterCollection,
            CharacterModel::class.java
        ).mappedResults

        return getCharacterResponseOfEntity(query)
    }

    fun getCharacterResponseOfEntity(query: List<CharacterModel>): List<CharacterResponse> {
        return query.map { CharacterResponse(
            name = it.name,
            status = Status(
                hp = it.status.hp,
                mp = it.status.mp,
                str = it.status.str,
                dex = it.status.dex,
                int = it.status.int,
                luk = it.status.luk,
            ),
            power = Power(
                minimum = it.power.minimum,
                maximum = it.power.maximum,
            ),
            damage = Damage(
                normal = it.damage.normal,
                boss = it.damage.boss,
                critical = it.damage.critical,
                gongMa = it.damage.gongMa,
                bangMu = it.damage.bangMu,
            )
        )}
    }
}