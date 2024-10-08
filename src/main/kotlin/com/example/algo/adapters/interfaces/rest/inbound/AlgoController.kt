package com.example.algo.adapters.interfaces.rest.inbound

import com.example.algo.adapters.interfaces.rest.dto.request.CharacterRequest
import com.example.algo.adapters.interfaces.rest.dto.response.AmplificationDTO
import com.example.algo.adapters.interfaces.rest.dto.response.CharacterResponse
import com.example.algo.adapters.interfaces.rest.dto.response.CubeDTO
import com.example.algo.adapters.interfaces.rest.dto.response.StarForceDTO
import com.example.algo.application.domain.enum.CubeType
import com.example.algo.application.domain.enum.RareType
import com.example.algo.application.domain.enum.StarForceEvent
import com.example.algo.application.inbound.AlgoCommandService
import com.example.algo.application.inbound.AlgoQueryService
import com.example.algo.application.outbound.dto.*
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import java.math.BigDecimal
import java.math.BigInteger
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

@RequestMapping(value = ["/algo"])
@RestController
class AlgoController(
    private val query: AlgoQueryService,
    private val command: AlgoCommandService,
) {
    @PostMapping
    fun insertCharacter(@RequestBody dto: CharacterRequest): ResponseEntity<Void> {
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
    fun getCharacters(): ResponseEntity<List<CharacterResponse>> {
        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(query.getCharacters())
    }

    @GetMapping(value = ["/all"])
    fun getCharacter(@RequestParam name: String, @RequestParam boss: Int): ResponseEntity<List<CharacterResponse>> {
        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(query.getCharacter(name, boss))
    }

    @GetMapping(value = ["/star-force"])
    fun getStarForceChance(
        @RequestParam req: Int,
        @RequestParam count: Int,
        @RequestParam step: Int,
        @RequestParam target: Int,
        @RequestParam destroy: Boolean,
        @RequestParam catch: Boolean,
        @RequestParam superior: Boolean,
        @RequestParam(defaultValue = "NONE") event: StarForceEvent
    ): ResponseEntity<StarForceDTO> {
        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(query.getStarForceChance(req, count, step, target, destroy, catch, event, superior))
    }

    @GetMapping(value = ["/star-force/cost"])
    fun getStarForceChance(
        @RequestParam req: Int,
        @RequestParam step: Int
    ): ResponseEntity<BigDecimal> {
        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(query.getStarForceCost(req, step))
    }

    @GetMapping(value = ["/cube/level-up"])
    fun getCubeLevelUp(
        @RequestParam cube: CubeType,
        @RequestParam count: Int,
        @RequestParam req: Int,
        @RequestParam base: RareType,
        @RequestParam target: RareType,
        @RequestParam event: Boolean
    ): ResponseEntity<CubeDTO> {
        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(query.getCubeLevelUp(req, cube, count, base, target, event))
    }

    @GetMapping(value = ["/enforce-value"])
    fun getEnforceValue(
        @RequestParam req: Int,
        @RequestParam step: Int,
    ): ResponseEntity<List<CharacterResponse>> {
        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(query.getTest())
    }

    @GetMapping(value = ["/test"])
    fun getTest(): ResponseEntity<List<CharacterResponse>> {
        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(query.getTest())
    }

    @GetMapping("/server/info")
    fun getServerInfo(): ResponseEntity<DFBody<ServerInfo>> {
        /*
        var body = service.getServers();
        for (ServerInfo info : body.getRows()) {
            logger.info(info.getServerName());
        }*/
        val value = query.getServers()
        return query.getServers().let {
            ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(it)
        }

    }

    @GetMapping("/server/{serverId}/characters")
    fun getCharacters(
        @PathVariable serverId: String,
        @RequestParam characterName: String
    ): ResponseEntity<DFBody<CharacterInfo>> {
        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                query.getCharacters(
                    serverId = serverId,
                    characterName = characterName
                )
            )
    }

    @GetMapping("/server/{serverId}/characters/{characterId}")
    fun getCharacter(
        @PathVariable serverId: String,
        @PathVariable characterId: String
    ): ResponseEntity<CharacterBase> {
        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                query.getCharacter(
                    serverId = serverId,
                    characterId = characterId
                )
            )
    }

    @GetMapping("/server/{serverId}/characters/{characterId}/time-line")
    fun getCharacterTimeLine(
        @PathVariable serverId: String,
        @PathVariable characterId: String,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") startDate: LocalDateTime?,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") endDate: LocalDateTime?,
    ): ResponseEntity<CharacterTimeLine> {
        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                query.getCharacterTimeLine(
                    serverId = serverId,
                    characterId = characterId,
                    startDate = startDate,
                    endDate = endDate
                )
            )
    }

    @GetMapping("/server/{serverId}/characters/{characterId}/time-line/calc")
    fun calcCharacterTimeLine(
        @PathVariable serverId: String,
        @PathVariable characterId: String,
    ): ResponseEntity<CharacterTimeLine> {
        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                query.calcCharacterTimeLine(
                    serverId = serverId,
                    characterId = characterId
                )
            )
    }

    @GetMapping("/characters/{characterId}")
    fun character(
        @PathVariable characterId: String,
    ): ResponseEntity<CharacterBase> {
        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                query.character(
                    characterId = characterId
                )
            )
    }

    @GetMapping("/adventure/{name}")
    fun charactersByAdventureName(
        @PathVariable name: String
    ): ResponseEntity<List<CharacterBase>> {
        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(query.getCharactersByAdventureName(name))
    }

    @GetMapping("/adventure/{name}/statistics/drop")
    fun dropStatisticsByAdventureName(
        @PathVariable name: String
    ): ResponseEntity<List<CharacterBase>> {
        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(query.getEpicStatisticsByAdventureName(name))
    }

    @GetMapping("/amplification")
    fun getAmplificationValue(
        count: Int,
        step: Int,
        target: Int,
        safety: Boolean,
        weapon: Boolean,
        crystalPrice: Int
    ): ResponseEntity<AmplificationDTO> {
        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                query.getAmplificationValue(
                    count,
                    step,
                    target,
                    safety,
                    weapon,
                    crystalPrice
                )
            )
    }

    @GetMapping("/amplification/ticket")
    fun getAmplificationValueByTicket(
        count: Int,
        base: BigInteger,
        probability: Int,
    ): ResponseEntity<AmplificationDTO> {
        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                query.getAmplificationValueByTicket(
                    count,
                    base,
                    probability
                )
            )
    }
}