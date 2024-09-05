package com.example.algo.application.outbound

import com.example.algo.adapters.interfaces.rest.dto.request.CharacterRequest
import com.example.algo.adapters.interfaces.rest.dto.response.CharacterResponse
import com.example.algo.adapters.interfaces.rest.dto.response.StarForceDTO
import com.example.algo.application.domain.enum.StarForceEvent
import com.example.algo.application.outbound.dto.CharacterBase
import com.example.algo.application.outbound.dto.CharacterTimeLine

interface AlgoService {
    fun insertCharacter(dto: CharacterRequest): String
    fun getCharacters(): List<CharacterResponse>
    fun getCharacter(name: String, boss: Int): List<CharacterResponse>
    fun saveCharacter(request: CharacterBase)
    fun findCharacterById(id: String): CharacterBase?
    fun deleteCharacterById(id: String)
    fun getCharactersByAdventureName(name: String): List<CharacterBase>?
}