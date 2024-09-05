package com.example.algo.application.outbound.dto

data class DFBody<T>(
    var rows: List<T> = mutableListOf()
)
