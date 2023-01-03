package com.example.algo.application.inbound

import com.example.algo.adapters.interfaces.rest.dto.response.*
import com.example.algo.application.domain.enum.CubeType
import com.example.algo.application.domain.enum.RareType
import com.example.algo.application.domain.enum.StarForceEvent
import com.example.algo.application.outbound.AlgoService
import org.springframework.stereotype.Service
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.OutputStreamWriter
import java.math.BigDecimal
import java.math.BigInteger
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.pow
import kotlin.math.roundToInt

interface AlgoQueryService {
    fun b1003()
    fun getCharacters(): List<CharacterResponse>
    fun getCharacter(name: String, boss: Int): List<CharacterResponse>
    fun getStarForceChance(req: Int, count: Int, step: Int, target: Int, destroy:Boolean, catch:Boolean, event: StarForceEvent, superior: Boolean): StarForceDTO
    fun getStarForceCost(req: Int, step: Int): BigDecimal
    fun getCubeLevelUp(req: Int, cube: CubeType, count: Int, base: RareType, target: RareType, event: Boolean): CubeDTO
    fun getTest(): List<CharacterResponse>
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

    override fun getStarForceChance(req: Int, count: Int, step: Int, target: Int, destroy:Boolean, catch:Boolean, event: StarForceEvent, superior: Boolean): StarForceDTO {
        var returnValues = mutableListOf<StarForceDTO>()
        var costs = mutableListOf<BigInteger>()
        val format = DecimalFormat("#,###")

        /*
        val dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
        val home = System.getProperty("user.home")
        val path = "$home/Documents/test"
        val fileName = "Star Force Test__$dateTime.txt"
        val file = File(path, fileName)
        val bwFile = BufferedWriter(FileWriter(file))
        */

        for(i in 1 .. count) {
            var stepValue = step
            var returnValue = StarForceDTO()
            var cost = BigInteger.ZERO
            var failedStack = 0
            while(stepValue != target) {
                cost += if(!superior) {
                    calcEnforceValue(req, stepValue, destroy, event).toBigInteger()
                } else {
                    calcSuperiorEnforceValue(req, stepValue).toBigInteger()
                }
                //println("cost : $cost")
                returnValue.enforceCount++
                //println("EnforceCount : " + returnValue.cost)
                val mathValue = Math.random() * 100
//                var text = "mathValue : $mathValue\n"
//                bwFile.write(text)
//                bwFile.flush()
                //println("mathValue : " + returnValue.cost)
                var successFlg: Int // 0 = 성공 1은 실패 2는 터짐
//                text = "probability : " + calcProbability(stepValue, catch, event) + "\n"
//                bwFile.write(text)
//                bwFile.flush()
                var probValue = if(!superior) {
                    calcProbability(stepValue, catch, event)
                } else {
                    calcSuperiorProbability(stepValue, catch)
                }
                var destroyValue = if(!superior)
                    100-calcDestroy(stepValue, destroy)
                else
                    100-calcSuperiorDestroy(stepValue)
                //println(probValue)
                if (failedStack == 2){
                    successFlg = 0
//                    text = "$stepValue 에서 확정 강화 성공\n"
//                    bwFile.write(text)
//                    bwFile.flush()
                }
                else if(mathValue < probValue) {
                    successFlg = 0
//                    text = "$stepValue 에서 강화 성공\n"
//                    bwFile.write(text)
//                    bwFile.flush()
                } else if (mathValue > destroyValue) {
                    successFlg = 2
                    returnValue.destroyCount++
//                    text = "$stepValue 에서 터짐\n"
//                    bwFile.write(text)
//                    bwFile.flush()
                    failedStack = 0
                    //println("************ 터짐 *************")
                } else {
                    successFlg = 1
//                    text = "$stepValue 에서 실패\n"
//                    bwFile.write(text)
//                    bwFile.flush()
                }

                when (successFlg) {
                    0 -> { // 성공시
                        failedStack = 0
                        if(event == StarForceEvent.ONE_PLUS_ONE && stepValue <= 10 && !superior)
                            stepValue += 2
                        else
                            stepValue++
                    }

                    1 -> { // 실패시
                        when(stepValue) {
                            in 0..15, 20 -> {
                                if(!superior)
                                    stepValue
                                else {
                                    stepValue--
                                }
                            }

                            else -> {
                                stepValue--
                                failedStack++
                            }
                        }
                    }
                    2 -> { // 터짐
                        stepValue = if(!superior)
                            12
                        else
                            5
                        failedStack = 0
                    }
                }
                //println("stepValue : $stepValue")
            }
            costs.add(cost)
            returnValue.cost = format.format(cost)
            returnValues.add(returnValue)
        }

        /*var text = "Total Cost : " + format.format(costs.sumOf { it }.div(count.toBigInteger()))+ "\n"
        bwFile.write(text)
        bwFile.flush()
        bwFile.close()*/

        var avg = costs.sumOf { it }.div(count.toBigInteger())
        var stDevs = mutableListOf<BigInteger>()

        for(cost in costs) {
            stDevs.add((cost-avg).pow(2))
        }

        var median = costs.sorted()[(count/2.0).roundToInt()]
        var top = costs.sorted()[(count*0.1).roundToInt()]
        var low = costs.sorted()[(count*0.9).roundToInt()]

        var stDevAvg = stDevs.sumOf { it }.div(count.toBigInteger())

        return StarForceDTO(
            returnValues.sumOf { it.enforceCount }/count,
            format.format(avg),
            format.format(stDevAvg.sqrt()),
            format.format(median),
            format.format(top),
            format.format(low),
            returnValues.sumOf { it.destroyCount }/count,
        )
    }

    override fun getStarForceCost(req: Int, step: Int): BigDecimal {
        return calcEnforceValue(req, step, false, StarForceEvent.NONE).toBigDecimal()
    }

    override fun getCubeLevelUp(req: Int, cube: CubeType, count: Int, base: RareType, target: RareType, event: Boolean): CubeDTO {
        val counts = mutableListOf<Int>()
        val MASTER_CUBE = 4900000
        val BLACK_CUBE = 22600000
        val RED_CUBE = 12500000
        val format = DecimalFormat("#,###")
        for (i in 1 .. count) {
            var stack = 0
            var current = base
            while(current != target) {
                var prob = getCubeLevelUpProb(cube, current)
                stack++
                val mathValue = Math.random() * 100
                if(mathValue <= prob) {
                    current = when(current) {
                        RareType.RARE -> {
                            RareType.EPIC
                        }

                        RareType.EPIC -> {
                            RareType.UNIQUE
                        }

                        else -> {
                            RareType.LEGENDARY
                        }
                    }
                }
            }
            counts.add(stack)
        }
        val cost = when(cube) {
            CubeType.MASTER -> {
                format.format(counts.average() * MASTER_CUBE + counts.average() * getCubeCost(req))
            }

            CubeType.RED -> {
                format.format(counts.average() * RED_CUBE + counts.average() * getCubeCost(req))
            }

            CubeType.BLACK -> {
                format.format(counts.average() * BLACK_CUBE + counts.average() * getCubeCost(req))
            }

            else -> {
                ""
            }
        }
        return CubeDTO(counts.average(), cost)
    }

    fun calcEnforceValue(req: Int, step: Int, destroy: Boolean, event: StarForceEvent): Int {
        var returnValue = 1000
        val reqValue = req*req*req
        val stepValue: Double = (step+1).toDouble()
        val eventValue = if(event == StarForceEvent.DISCOUNT || event == StarForceEvent.SHINING) 0.7 else 1.0
        return when (step) {
            in 0..9 -> {
                ((1000 + (returnValue+reqValue*stepValue/25)) * eventValue).toInt()
            }

            10 -> {
                ((1000 + ((returnValue+reqValue*(stepValue.pow(2.7))/400))) * eventValue).toInt()
            }

            11 -> {
                ((1000 + ((returnValue+reqValue*(stepValue.pow(2.7))/220))) * eventValue).toInt()
            }

            12 -> {
                ((1000 + ((returnValue+reqValue*(stepValue.pow(2.7))/150))) * eventValue).toInt()
            }

            13 -> {
                ((1000 + ((returnValue+reqValue*(stepValue.pow(2.7))/110))) * eventValue).toInt()
            }

            14 -> {
                ((1000 + ((returnValue+reqValue*(stepValue.pow(2.7))/75))) * eventValue).toInt()
            }

            in 15 .. 16 -> {
                if(destroy) ((1000 + ((returnValue+reqValue*(stepValue.pow(2.7))/200))).toInt() + ((1000 + ((returnValue+reqValue*(stepValue.pow(2.7))/200))) * eventValue).toInt())
                else ((1000 + ((returnValue+reqValue*(stepValue.pow(2.7))/200))) * eventValue).toInt()
            }

            else -> {
                ((1000 + ((returnValue+reqValue*(stepValue.pow(2.7))/200))) * eventValue).toInt()
            }
        }
    }

    override fun getTest(): List<CharacterResponse> {
        var res = CharacterResponse("", Status(), Power(), Damage())
        var resList = mutableListOf<CharacterResponse>()
        resList.add(res)
        res.damage.bangMu = 40.0
        return resList
    }

    fun calcSuperiorEnforceValue(req: Int, step: Int): Int {
        return when (req) {
            80 -> {
                5956000
            }

            110 -> {
                18507900
            }

            else -> {
                55382200
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

    fun calcSuperiorProbability(step: Int, catch: Boolean): Double {
        val weight = if(catch) {
            1.05
        } else {
            1.0
        }
        return when (step) {
            in 0..1 -> {
                50.0 * weight
            }

            2 -> {
                45.0 * weight
            }

            in 3..8 -> {
                40.0 * weight
            }

            9 -> {
                37.0 * weight
            }

            in 10..11 -> {
                35.0 * weight
            }

            12 -> {
                3.0 * weight
            }

            13 -> {
                2.0 * weight
            }

            else -> {
                1.0 * weight
            }
        }
    }

    fun calcDestroy(step: Int, destroy: Boolean): Double {
        return when (step) {
            in 0..14 -> {
                0.0
            }
            in 15..16 -> {
                if(destroy) 0.0 else 2.1
            }

            17 -> {
                2.1
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

    fun calcSuperiorDestroy(step: Int): Double {
        return when (step) {
            in 0..4 -> {
                0.0
            }
            5 -> {
                1.8
            }
            6 -> {
                3.0
            }
            7 -> {
                4.2
            }
            8 -> {
                6.0
            }
            9 -> {
                9.5
            }
            10 -> {
                13.0
            }
            11 -> {
                16.3
            }
            12 -> {
                48.5
            }
            13 -> {
                49.0
            }
            else-> {
                49.5
            }
        }
    }

    fun getCubeLevelUpProb(cube: CubeType, base: RareType): Double {
        return when(cube) {
            CubeType.BLACK -> {
                when (base) {
                    RareType.RARE -> {
                        15.0
                    }

                    RareType.EPIC -> {
                        3.5
                    }

                    RareType.UNIQUE -> {
                        1.2
                    }
                    else -> {
                        0.0
                    }
                }
            }

            CubeType.RED -> {
                when (base) {
                    RareType.RARE -> {
                        6.0
                    }

                    RareType.EPIC -> {
                        1.8
                    }

                    RareType.UNIQUE -> {
                        0.3
                    }
                    else -> {
                        0.0
                    }
                }
            }

            CubeType.MASTER -> {
                when (base) {
                    RareType.RARE -> {
                        4.7619
                    }

                    RareType.EPIC -> {
                        1.1858
                    }

                    else -> {
                        0.0
                    }
                }
            }

            CubeType.GM -> {
                when (base) {
                    RareType.RARE -> {
                        7.9994
                    }

                    RareType.EPIC -> {
                        1.6959
                    }

                    RareType.UNIQUE -> {
                        0.1996
                    }
                    else -> {
                        0.0
                    }
                }
            }
        }
    }

    fun getCubeCost(req: Int):Int {
        return when(req) {
            80 -> {
                16000
            }

            90 -> {
                20200
            }

            100 -> {
                25000
            }

            110 -> {
                30200
            }

            120 -> {
                36000
            }

            130 -> {
                338000
            }

            140 -> {
                392000
            }

            150 -> {
                450000
            }

            160 -> {
                512000
            }

            200 -> {
                800000
            }

            else -> {
                0
            }
        }
    }
}