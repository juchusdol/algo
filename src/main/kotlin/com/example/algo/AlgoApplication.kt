package com.example.algo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class AlgoApplication

fun main(args: Array<String>) {
    runApplication<AlgoApplication>(*args)
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

fun lotto45() {
    println("lotto45 실행")
    val numbers = Array(46) {0}
    while(true) {
        val read = readln()
        if(read == "end")
            break
        val data = read.split(" ")
        var i = 0
        data.forEach {
            i++
            if(i != 7)
                numbers[it.toInt()]++
        }
    }
    for(i in 1 until numbers.size) {
        println("$i : ${numbers[i]}")
    }
}

fun lotto45Calc() {
    println("lotto45Calc 실행")
    val numbers = mutableListOf<String>()
    while(true) {
        val read = readln()
        if(read == "end")
            break
        val data = read.split(" ").map { it.toInt() }
        if(data.contains(1)) {
            numbers.add(read)
        }
    }
    numbers.sort()
    numbers.forEach { println(it) }
}

fun lotto720() {
    println("lotto720 실행")
    val numbers1 = Array(10) {0}
    val numbers10 = Array(10) {0}
    val numbers100 = Array(10) {0}
    val numbers1000 = Array(10) {0}
    val numbers10000 = Array(10) {0}
    val numbers100000 = Array(10) {0}
    while(true) {
        val read = readln()
        if(read == "end")
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
    for(i in numbers1.indices) {
        println("1의 자리 $i : ${numbers1[i]}")
    }

    for(i in numbers10.indices) {
        println("10의 자리 $i : ${numbers10[i]}")
    }

    for(i in numbers100.indices) {
        println("100의 자리 $i : ${numbers100[i]}")
    }

    for(i in numbers1000.indices) {
        println("1000의 자리 $i : ${numbers1000[i]}")
    }

    for(i in numbers10000.indices) {
        println("10000의 자리 $i : ${numbers10000[i]}")
    }

    for(i in numbers100000.indices) {
        println("100000의 자리 $i : ${numbers100000[i]}")
    }
}