package com.example.algo.adapters.interfaces.rest.inbound

import com.example.algo.adapters.interfaces.rest.dto.request.CharacterRequest
import com.example.algo.adapters.interfaces.rest.dto.response.CharacterResponse
import com.example.algo.application.inbound.AlgoCommandService
import com.example.algo.application.inbound.AlgoQueryService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RequestMapping(value=["/algo"])
@RestController
class AlgoController(
    private val query: AlgoQueryService,
    private val command: AlgoCommandService,
) {
    @PostMapping
    fun insertCharacter(@RequestBody dto: CharacterRequest):ResponseEntity<Void> {
        val name = command.insertCharacter(dto)

        val uriComponents = UriComponentsBuilder
            .newInstance()
            .path("/algo/$name")
            .build()

        return ResponseEntity
            .created(uriComponents.toUri())
            .contentType(MediaType.APPLICATION_JSON)
            .build()
    }

    @GetMapping
    fun getCharacters():ResponseEntity<List<CharacterResponse>> {
        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(query.getCharacters())
    }

    @GetMapping(value = ["/all"])
    fun getCharacter(@RequestParam name: String, @RequestParam boss: Int):ResponseEntity<List<CharacterResponse>> {
        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(query.getCharacter(name, boss))
    }
}