package com.example.algo.application.inbound

import com.example.algo.adapters.interfaces.rest.dto.response.CharacterResponse
import com.example.algo.application.outbound.AlgoService
import org.springframework.stereotype.Service

interface AlgoQueryService {
    fun b1003()
    fun getCharacters(): List<CharacterResponse>
    fun getCharacter(name: String, boss: Int): List<CharacterResponse>
}

@Service
class AlgoQueryServiceImpl (
    val service: AlgoService
        ) : AlgoQueryService {
    override fun b1003() {
    }

    override fun getCharacters(): List<CharacterResponse> {
        return service.getCharacters()
    }

    override fun getCharacter(name: String, boss: Int): List<CharacterResponse> {
        return service.getCharacter(name, boss)
    }
}