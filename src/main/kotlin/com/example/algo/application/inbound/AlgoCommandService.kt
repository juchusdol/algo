package com.example.algo.application.inbound

import com.example.algo.adapters.interfaces.rest.dto.request.CharacterRequest
import com.example.algo.application.outbound.AlgoService
import org.springframework.stereotype.Service

interface AlgoCommandService {
    fun b1003()
    fun insertCharacter(dto: CharacterRequest): String
}

@Service
class AlgoCommandServiceImpl(
    val service: AlgoService
) : AlgoCommandService {
    override fun b1003() {
    }

    override fun insertCharacter(dto: CharacterRequest): String {
        return service.insertCharacter(dto)
    }
}