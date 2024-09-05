package com.example.algo.adapters.interfaces.rest.outbound

import com.example.algo.adapters.interfaces.rest.dto.request.CharacterRequest
import com.example.algo.adapters.interfaces.rest.dto.response.CharacterResponse
import com.example.algo.application.domain.model.CharacterModel
import com.example.algo.application.domain.model.Damage
import com.example.algo.application.domain.model.Power
import com.example.algo.application.domain.model.Status
import com.example.algo.application.domain.repository.CommandRepository
import com.example.algo.application.domain.repository.DfCommandRepository
import com.example.algo.application.domain.repository.DfQueryRepository
import com.example.algo.application.domain.repository.QueryRepository
import com.example.algo.application.outbound.AlgoService
import com.example.algo.application.outbound.dto.CharacterBase
import com.example.algo.application.outbound.dto.CharacterTimeLine
import org.springframework.stereotype.Service
import java.util.*

var dp = mutableListOf<Int>()

@Service
class AlgoServiceImpl(
    private val commandRepository: CommandRepository,
    private val dfCommandRepository: DfCommandRepository,
    private val dfQueryRepository: DfQueryRepository,
    private val queryRepository: QueryRepository
) : AlgoService {
    override fun insertCharacter(dto: CharacterRequest): String {
        var random = Random()
        commandRepository.createCharacter(
            CharacterModel(
                name = dto.name,
                status = Status(
                    hp = random.nextInt(110000),
                    mp = random.nextInt(110000),
                    str = random.nextInt(62000),
                    dex = random.nextInt(62000),
                    int = random.nextInt(62000),
                    luk = random.nextInt(62000),
                ),
                power = Power(
                    minimum = random.nextInt(10000000) + 40000000,
                    maximum = random.nextInt(10000000) + 50000000,
                ),
                damage = Damage(
                    normal = random.nextInt(100),
                    boss = random.nextInt(400),
                    critical = random.nextInt(110),
                    gongMa = random.nextInt(100),
                    bangMu = random.nextInt(9700).div(100.0),
                ),
            )
        )
        /*
        commandRepository.createCharacter(CharacterModel(
            name = dto.name,
            status = Status(
                hp = dto.status.hp,
                mp = dto.status.mp,
                str = dto.status.str,
                dex = dto.status.dex,
                int = dto.status.int,
                luk = dto.status.luk,
            ),
            power = Power(
                minimum = dto.power.minimum,
                maximum = dto.power.maximum,
            ),
            damage = Damage(
                normal = dto.damage.normal,
                boss = dto.damage.boss,
                critical = dto.damage.critical,
                gongMa = dto.damage.gongMa,
                bangMu = dto.damage.bangMu,
            ),
        ))
         */
        return dto.name
    }

    override fun getCharacters(): List<CharacterResponse> {
        return queryRepository.getCharacter()
    }

    override fun getCharacter(name: String, boss: Int): List<CharacterResponse> {
        return queryRepository.getCharacter(name, boss)
    }

    override fun saveCharacter(request: CharacterBase) {
        dfCommandRepository.saveCharacterTimeLine(request)
    }

    override fun findCharacterById(id: String): CharacterBase? {
        return dfQueryRepository.findCharacterByCharacterId(id)
    }

    override fun deleteCharacterById(id: String) {
        dfCommandRepository.deleteCharacterBaseById(id)
    }

    override fun getCharactersByAdventureName(name: String): List<CharacterBase>? {
        return dfQueryRepository.findCharacterAllByAdventureName(name)
    }

    fun b1003() {
        dp.add(0, 0)
        dp.add(1, 1)
        dp.add(2, 1)

        for (i in 3 until 41) {
            dp.add(i, dp[i - 1] + dp[i - 2])
        }

        var i = readLine()!!.toInt()
        for (j in 0 until i) {
            var target = readLine()!!.toInt()
            when (target) {
                0 -> {
                    println("1 0")
                }

                1 -> {
                    println("0 1")
                }

                else -> {
                    println("${dp[target - 1]} ${dp[target]}")
                }
            }
        }
    }

    fun b2839() {

    }
}