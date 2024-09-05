package com.example.algo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class AlgoApplication

fun main(args: Array<String>) {
    runApplication<AlgoApplication>(*args)
    //socarProblem()
    //kakaoWinter1()
    //val service = AlgoServiceImpl()
    /*
    println("1. lotto45 2.lotto720 3.lotto45Calc")
    var temp = readLine()
    when(temp) {
        "1" -> {
            lotto45()
        }
        "3" -> {
            lotto45Calc()
        }
        "2" -> {
            lotto720()
        }
    }
    */
}


fun kakaoWinter1() {
    //val friends = arrayOf("joy", "brad", "alessandro", "conan", "david")
    val friends = arrayOf("muzi", "ryan", "frodo", "neo")
    //val gifts = arrayOf("alessandro brad", "alessandro joy", "alessandro conan", "david alessandro", "alessandro david")
    val gifts = arrayOf(
        "muzi frodo",
        "muzi frodo",
        "ryan muzi",
        "ryan muzi",
        "ryan muzi",
        "frodo muzi",
        "frodo ryan",
        "neo muzi"
    )
    println(
        kakaoWinter1Solution(
            friends = friends,
            gifts = gifts
        )
    )
}

fun kakaoWinter1Solution(friends: Array<String>, gifts: Array<String>): Int {
    var answer = 0

    val giftValues = HashMap<String, Int>()
    val giftGraph = HashMap<String, HashMap<String, Int>>()
    val giftTake = HashMap<String, HashMap<String, Int>>()

    friends.forEach {
        giftValues[it] = 0
    }

    friends.forEach {
        giftGraph[it] = giftValues.clone() as HashMap<String, Int>
        giftTake[it] = giftValues.clone() as HashMap<String, Int>
    }

    gifts.forEach {
        val target = it.split(" ")
        val from = target[0]
        val to = target[1]

        giftValues[from] = giftValues[from]!! + 1
        giftValues[to] = giftValues[to]!! - 1

        giftGraph[from]!![to] = giftGraph[from]!![to]!!.plus(1)

        if (giftGraph[from]!![to]!! < giftGraph[to]!![from]!!) {
            giftTake[to]!![from] = 1
            giftTake[from]!![to] = 0
        } else if (giftGraph[from]!![to]!! == giftGraph[to]!![from]!!) {
            if (giftValues[from]!! < giftValues[to]!!) {
                giftTake[to]!![from] = 1
                giftTake[from]!![to] = 0
            } else if (giftValues[from]!! > giftValues[to]!!) {
                giftTake[from]!![to] = 1
                giftTake[to]!![from] = 0
            } else {
                giftTake[from]!![to] = 0
                giftTake[to]!![from] = 0
            }
        } else {
            giftTake[from]!![to] = 1
            giftTake[to]!![from] = 0
        }
    }

    giftGraph.forEach { graph ->
        run {
            val filtered = graph.value.filter {
                it.key != graph.key
                it.value == giftGraph[it.key]!![graph.key]
            }
            filtered.forEach { filter ->
                run {
                    if(giftValues[graph.key]!! < giftValues[filter.key]!!) {
                        giftTake[filter.key]!![graph.key] = 1
                    } else if(giftValues[graph.key]!! > giftValues[filter.key]!!) {
                        giftTake[graph.key]!![filter.key] = 1
                    }
                }
            }
        }
    }

    giftTake.forEach { take ->
        run {
            val value = take.value.filter {
                it.value == 1
            }.count()
            if (answer < value)
                answer = value
        }
    }

    return answer
}

fun socarProblem() {
    //val tickets = arrayOf("A 1", "B 2", "C 5", "D 3")
    val tickets = arrayOf("A 2", "B 1")
    val roll = 5
    // ["B", "C", "B", "C"], ["A", "A", "A", "B"], ["D", "D", "C", "D"]
    // [["A", "A", "A"], ["A", "B", "B"], ["A", "B", "B"], ["A", "B", "B"]]
    val shopData1 = arrayOf("A", "A", "A")
    val shopData2 = arrayOf("A", "B", "B")
    val shopData3 = arrayOf("A", "B", "B")
    val shopData4 = arrayOf("A", "B", "B")
    val shop = arrayOf(shopData1, shopData2, shopData3, shopData4)
    val money = 19
    println(
        solution(
            tickets = tickets,
            roll = roll,
            shop = shop,
            money = money
        )
    )
}

fun solution(tickets: Array<String>, roll: Int, shop: Array<Array<String>>, money: Int): Int {
    var answer = 0

    val ticketMaps = HashMap<String, Int>()
    val countMaps = HashMap<String, Int>()
    tickets.forEach {
        val split = it.split(" ")
        ticketMaps[split[0]] = split[1].toInt()
        countMaps[split[0]] = 0
    }

    var benchMark = (money / 3 / roll) + 1

    println("benchMark :: $benchMark")

    if (benchMark > shop.size)
        benchMark = shop.size - 1
    else if (benchMark == shop.size)
        benchMark--

    println("benchMark :: $benchMark")
    for (i in 0..benchMark) {
        shop[i].map {
            countMaps[it] = countMaps[it]!! + 1
        }
        val filtered = countMaps.filter { (countMaps[it.key]!! / 3) > 0 }
        var cost = money
        var goldenTemp = 0
        filtered.map {
            val value = filtered[it.key]!! / 3
            val ticketCost = ticketMaps[it.key]!! * 3 * value
            val reRoll = i * roll
            if (cost >= ticketCost + reRoll) {
                goldenTemp += value
                cost -= ticketCost
                cost -= reRoll
            }
        }
        if (answer < goldenTemp)
            answer = goldenTemp
    }

    return answer
}

fun lotto45() {
    println("lotto45 실행")
    val numbers = Array(46) { 0 }
    while (true) {
        val read = readln()
        if (read == "end")
            break
        val data = read.split(" ")
        var i = 0
        data.forEach {
            i++
            if (i != 7)
                numbers[it.toInt()]++
        }
    }
    for (i in 1 until numbers.size) {
        println("$i : ${numbers[i]}")
    }
}

fun lotto45Calc() {
    println("lotto45Calc 실행")
    val numbers = mutableListOf<String>()
    while (true) {
        val read = readln()
        if (read == "end")
            break
        val data = read.split(" ").map { it.toInt() }
        if (data.contains(1)) {
            numbers.add(read)
        }
    }
    numbers.sort()
    numbers.forEach { println(it) }
}

fun lotto720() {
    println("lotto720 실행")
    val numbers1 = Array(10) { 0 }
    val numbers10 = Array(10) { 0 }
    val numbers100 = Array(10) { 0 }
    val numbers1000 = Array(10) { 0 }
    val numbers10000 = Array(10) { 0 }
    val numbers100000 = Array(10) { 0 }
    while (true) {
        val read = readln()
        if (read == "end")
            break
        var data = read.toInt()
        val data1 = data.mod(10)
        numbers1[data1]++

        val data10 = data.div(10).mod(10)
        numbers10[data10]++

        val data100 = data.div(100).mod(10)
        numbers100[data100]++

        val data1000 = data.div(1000).mod(10)
        numbers1000[data1000]++

        val data10000 = data.div(10000).mod(10)
        numbers10000[data10000]++

        val data100000 = data.div(100000).mod(10)
        numbers100000[data100000]++

    }
    for (i in numbers1.indices) {
        println("1의 자리 $i : ${numbers1[i]}")
    }

    for (i in numbers10.indices) {
        println("10의 자리 $i : ${numbers10[i]}")
    }

    for (i in numbers100.indices) {
        println("100의 자리 $i : ${numbers100[i]}")
    }

    for (i in numbers1000.indices) {
        println("1000의 자리 $i : ${numbers1000[i]}")
    }

    for (i in numbers10000.indices) {
        println("10000의 자리 $i : ${numbers10000[i]}")
    }

    for (i in numbers100000.indices) {
        println("100000의 자리 $i : ${numbers100000[i]}")
    }
}