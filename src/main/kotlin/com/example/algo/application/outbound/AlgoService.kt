package com.example.algo.application.outbound

import com.example.algo.adapters.interfaces.rest.dto.request.CharacterRequest
import com.example.algo.adapters.interfaces.rest.dto.response.CharacterResponse

interface AlgoService {
    fun insertCharacter(dto: CharacterRequest): String
    fun getCharacters():List<CharacterResponse>
    fun getCharacter(name: String, boss: Int):List<CharacterResponse>
}