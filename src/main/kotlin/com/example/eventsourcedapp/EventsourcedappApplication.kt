package com.example.eventsourcedapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EventsourcedappApplication

fun main(args: Array<String>) {
    runApplication<EventsourcedappApplication>(*args)
}
