package com.example.eventsourcedapp.query

import com.example.eventsourcedapp.coreapi.RetrieveUserQuery
import com.example.eventsourcedapp.coreapi.UserCreatedEvent
import com.example.eventsourcedapp.coreapi.UserDeletedEvent
import com.example.eventsourcedapp.coreapi.UserNotFound
import com.example.eventsourcedapp.coreapi.UserUpdatedEvent

import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler

import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserProjector {
    private final var repository: UserViewRepository

    constructor(repository: UserViewRepository) {
        this.repository = repository
    }

    @EventHandler
    fun on(event: UserCreatedEvent) {
        val userView = UserView(event.userId.toString(), event.username, event.password)
        println("Saving user projection to database: ${userView}")
        repository.save(userView)
    }

    @QueryHandler
    fun handle(query: RetrieveUserQuery): UserView {
        var result = repository.findById(query.userId.toString()).orElse(null)
        if (result == null) {
            throw UserNotFound()
        }
        println("DB query result: ${result}")
        return result
    }

    @EventHandler
    fun on(event: UserUpdatedEvent) {
        val userView = repository.findById(event.userId.toString())
        if (userView.isPresent) {
            repository.save(
                UserView(event.userId.toString(), event.username, event.password)
            )
        }
    }

    @EventHandler
    fun on(event: UserDeletedEvent) {
        println("Trying to delete user ${event.userId}")
        val userView = repository.findById(event.userId.toString())
        if (userView.isPresent) {
            println("Found user ${event.userId} in database...")
        }
        repository.deleteById(event.userId.toString())
    }
}
