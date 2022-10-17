package com.example.algo.adapters.interfaces.rest.inbound

import com.example.algo.adapters.interfaces.rest.dto.request.CharacterRequest
import com.example.algo.adapters.interfaces.rest.dto.response.CharacterResponse
import com.example.algo.adapters.interfaces.rest.dto.response.CubeDTO
import com.example.algo.adapters.interfaces.rest.dto.response.StarForceDTO
import com.example.algo.application.domain.enum.CubeType
import com.example.algo.application.domain.enum.RareType
import com.example.algo.application.domain.enum.StarForceEvent
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
import java.math.BigDecimal

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

    @GetMapping(value= ["/star-force"])
    fun getStarForceChance(
        @RequestParam req: Int,
        @RequestParam count: Int,
        @RequestParam step: Int,
        @RequestParam target: Int,
        @RequestParam destroy: Boolean,
        @RequestParam catch: Boolean,
        @RequestParam(defaultValue = "NONE") event: StarForceEvent
    ) : ResponseEntity<StarForceDTO> {
        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(query.getStarForceChance(req, count, step, target, destroy, catch, event))
    }

    @GetMapping(value= ["/star-force/cost"])
    fun getStarForceChance(
        @RequestParam req: Int,
        @RequestParam step: Int
    ) : ResponseEntity<BigDecimal> {
        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(query.getStarForceCost(req, step))
    }

    @GetMapping(value= ["/cube/level-up"])
    fun getCubeLevelUp(
        @RequestParam cube: CubeType,
        @RequestParam count: Int,
        @RequestParam req: Int,
        @RequestParam base: RareType,
        @RequestParam target: RareType,
        @RequestParam event: Boolean
    ) : ResponseEntity<CubeDTO> {
        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(query.getCubeLevelUp(req, cube, count, base, target, event))
    }
}