package com.example.algo.application.inbound

import com.example.algo.adapters.interfaces.rest.dto.response.CharacterResponse
import com.example.algo.adapters.interfaces.rest.dto.response.StarForceDTO
import com.example.algo.application.domain.enum.StarForceEvent
import com.example.algo.application.outbound.AlgoService
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.BigInteger
import java.text.DecimalFormat
import kotlin.math.pow

interface AlgoQueryService {
    fun b1003()
    fun getCharacters(): List<CharacterResponse>
    fun getCharacter(name: String, boss: Int): List<CharacterResponse>
    fun getStarForceChance(req: Int, count: Int, step: Int, target: Int, destroy:Boolean, catch:Boolean, event: StarForceEvent): StarForceDTO
    fun getStarForceCost(req: Int, step: Int): BigDecimal
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

    override fun getStarForceChance(req: Int, count: Int, step: Int, target: Int, destroy:Boolean, catch:Boolean, event: StarForceEvent): StarForceDTO {
        var returnValues = mutableListOf<StarForceDTO>()
        var costs = mutableListOf<BigInteger>()
        val format = DecimalFormat("#,###")

        for(i in 1 .. count) {
            var stepValue = step
            var returnValue = StarForceDTO()
            var cost = BigInteger.ZERO
            while(stepValue != target) {
                cost += calcEnforceValue(req, stepValue, destroy, event).toBigInteger()
                //println("cost : $cost")
                returnValue.EnforceCount++
                //println("EnforceCount : " + returnValue.cost)

                val mathValue = Math.random() * 100
                //println("mathValue : " + returnValue.cost)
                var successFlg: Int // 0 = 성공 1은 실패 2는 터짐
                //println("probability : " + calcProbability(stepValue, true))
                var probValue = calcProbability(stepValue, catch, event)
                //println(probValue)
                if(mathValue < probValue) {
                    successFlg = 0
                } else if (mathValue > 100-calcDestroy(stepValue, destroy)) {
                    successFlg = 2
                    returnValue.destroyCount++
                    //println("************ 터짐 *************")
                } else {
                    successFlg = 1
                }

                when (successFlg) {
                    0 -> { // 성공시
                        if(event == StarForceEvent.ONE_PLUS_ONE && stepValue <= 10)
                            stepValue += 2
                        else
                            stepValue++
                    }

                    1 -> { // 실패시
                        when(stepValue) {
                            in 0..10 -> {
                                stepValue
                            }

                            15, 20 -> {
                                stepValue
                            }

                            else -> {
                                stepValue--
                            }
                        }
                    }
                    2 -> { // 터짐
                        stepValue = 12
                    }
                }
                //println("stepValue : $stepValue")
            }
            costs.add(cost)
            returnValue.cost = format.format(cost)
            returnValues.add(returnValue)
        }

        return StarForceDTO(
            returnValues.sumOf { it.EnforceCount }/count,
            format.format(costs.sumOf { it }.div(count.toBigInteger())),
            returnValues.sumOf { it.destroyCount }/count,
        )
    }

    override fun getStarForceCost(req: Int, step: Int): BigDecimal {
        return calcEnforceValue(req, step, false, StarForceEvent.NONE).toBigDecimal()
    }

    fun calcEnforceValue(req: Int, step: Int, destroy: Boolean, event: StarForceEvent): Int {
        var returnValue = 1000
        val reqValue = req*req*req
        val stepValue: Double = (step+1).toDouble()
        val eventValue = if(event == StarForceEvent.DISCOUNT || event == StarForceEvent.SHINING) 0.7 else 1.0
        return when (step) {
            in 0..9 -> {
                ((returnValue+reqValue*stepValue/25) * eventValue).toInt()
            }

            in 10..11-> {
                ((returnValue+reqValue*stepValue.pow(2.7)/400) * eventValue).toInt()
            }

            in 12..14-> {
                if(destroy) (returnValue+reqValue*stepValue.pow(2.7)/400 + (returnValue+reqValue*stepValue.pow(2.7)/400) * eventValue).toInt()
                else ((returnValue+reqValue*stepValue.pow(2.7)/400) * eventValue).toInt()
            }

            else -> {
                if(destroy) (returnValue+reqValue*stepValue.pow(2.7)/200 + (returnValue+reqValue*stepValue.pow(2.7)/200 * eventValue)).toInt()
                else ((returnValue+reqValue*stepValue.pow(2.7)/200) * eventValue).toInt()
            }
        }
    }

    fun calcProbability(step: Int, catch: Boolean, event: StarForceEvent): Double {
        val stepValue = step * 5
        val weight = if(catch) {
            1.05
        } else {
            1.0
        }
        return when (step) {
            in 0..2 -> {
                (95.0 - stepValue) * weight
            }

            in 3..4 -> {
                (100.0 - stepValue) * weight
            }

            5 -> {
                if(event == StarForceEvent.SUCCESS || event == StarForceEvent.SHINING) 100.0
                else (100.0 - stepValue) * weight
            }

            in 6..9 -> {
                (100.0 - stepValue) * weight
            }

            10 -> {
                if(event == StarForceEvent.SUCCESS || event == StarForceEvent.SHINING) 100.0
                else (100.0 - stepValue) * weight
            }

            in 11..14 -> {
                (100.0 - stepValue) * weight
            }

            15 -> {
                if(event == StarForceEvent.SUCCESS || event == StarForceEvent.SHINING) 100.0
                else 30.0 * weight
            }

            in 16.. 21 -> {
                30.0 * weight
            }

            22 -> {
                3.0 * weight
            }

            23 -> {
                2.0 * weight
            }

            else -> {
                1.0 * weight
            }
        }
    }

    fun calcDestroy(step: Int, destroy: Boolean): Double {
        return when (step) {
            in 0..11 -> {
                0.0
            }
            12 -> {
                if(destroy) 0.0 else 0.6
            }
            13 -> {
                if(destroy) 0.0 else 1.3
            }
            14 -> {
                if(destroy) 0.0 else 1.4
            }
            in 15..17 -> {
                if(destroy) 0.0 else 2.1
            }
            in 18..19 -> {
                2.8
            }
            in 20..21 -> {
                7.0
            }
            22-> {
                19.4
            }
            23-> {
                29.4
            }
            else-> {
                39.6
            }
        }
    }
}