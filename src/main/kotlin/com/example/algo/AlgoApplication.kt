package com.example.algo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AlgoApplication

fun main(args: Array<String>) {
    runApplication<AlgoApplication>(*args)
    //val service = AlgoServiceImpl()
}