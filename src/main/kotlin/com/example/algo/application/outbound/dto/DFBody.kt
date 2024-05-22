package com.example.algo.application.outbound.dto

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.io.Serializable

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
data class DFBody<T>(
    private val row : List<T> = mutableListOf(),
) : Serializable
