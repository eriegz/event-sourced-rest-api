package com.example.eventsourcedapp.query

import com.example.eventsourcedapp.coreapi.RetrieveUserQuery
import com.example.eventsourcedapp.coreapi.UserCreatedEvent

import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler

import org.springframework.stereotype.Component

@Component
class UserProjector {

    private final var repository: UserViewRepository

    constructor(repository: UserViewRepository) {
        this.repository = repository
    }

    @EventHandler
    fun on(event: UserCreatedEvent) {
        val userView = UserView(event.username, event.password)
        repository.save(userView)
    }

    @QueryHandler
    fun handle(query: RetrieveUserQuery): UserView {
        return repository.findById(query.username).orElse(null)
    }
}
