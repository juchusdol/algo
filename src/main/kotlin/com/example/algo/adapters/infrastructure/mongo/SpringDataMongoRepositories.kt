package com.example.algo.adapters.infrastructure.mongo

import com.example.algo.application.domain.model.CharacterModel
import org.springframework.data.mongodb.repository.MongoRepository

interface SpringDataMongoCharacterRepository : MongoRepository<CharacterModel, String>