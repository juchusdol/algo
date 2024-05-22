package com.example.algo.application.outbound.dto

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.Setter

@AllArgsConstructor
@Getter
@Setter
data class ServerInfo(
    private val serverId : String,
    private val serverName: String
)
