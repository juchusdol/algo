package com.example.algo.adapters.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class RestTemplateConfiguration {

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplateBuilder()
            .messageConverters(
                MappingJackson2HttpMessageConverter(
                    ObjectMapper()
                        .registerModule(
                            KotlinModule.Builder().configure(KotlinFeature.NullIsSameAsDefault, true).build()
                        )
                        .registerModule(JavaTimeModule())
                )
            )
            .build()
    }
}

inline fun <reified T> typeReference() = object : ParameterizedTypeReference<T>() {}