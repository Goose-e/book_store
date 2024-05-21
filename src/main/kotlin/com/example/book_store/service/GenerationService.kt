package com.example.book_store.service

import org.springframework.stereotype.Service
import java.time.LocalDateTime.now
import java.time.ZoneOffset
import kotlin.random.Random

@Service
class GenerationService {
    companion object {
        fun generateEntityId() = (now().toEpochSecond(ZoneOffset.UTC).toString()).toLong()+Random.nextLong(10000)
        fun generateCode() = Random.nextInt(100, 100000).toString() + Random.nextBits(10)
    }
}