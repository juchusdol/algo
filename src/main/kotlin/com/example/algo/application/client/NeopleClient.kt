package com.example.algo.application.client

import com.example.algo.application.outbound.dto.*
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "neopleClient", url = "\${domains.neople}")
interface NeopleClient {
    @GetMapping("/servers")
    fun getServers(
        @RequestParam apikey: String
    ): ResponseEntity<DFBody<ServerInfo>>

    @GetMapping("/servers/{serverId}/characters")
    fun getCharacters(
        @RequestParam apikey: String,
        @PathVariable serverId: String,
        @RequestParam characterName: String,
        @RequestParam wordType: String
    ): ResponseEntity<DFBody<CharacterInfo>>

    @GetMapping("/servers/{serverId}/characters/{characterId}")
    fun getCharacterInfo(
        @RequestParam apikey: String,
        @PathVariable serverId: String,
        @PathVariable characterId: String
    ): ResponseEntity<CharacterBaseInfo>

    @GetMapping("/servers/{serverId}/characters/{characterId}/timeline")
    fun getCharacterTimeLine(
        @RequestParam apikey: String,
        @PathVariable serverId: String,
        @PathVariable characterId: String,
        @RequestParam limit: Int,
        @RequestParam code: Int
    ): ResponseEntity<CharacterTimeLine>
}