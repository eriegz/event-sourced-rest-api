package com.example.eventsourcedapp.command

import com.example.eventsourcedapp.coreapi.CreateUserCommand
import com.example.eventsourcedapp.coreapi.DeleteUserCommand
import com.example.eventsourcedapp.coreapi.UpdateUserCommand
import com.example.eventsourcedapp.coreapi.UserCreatedEvent
import com.example.eventsourcedapp.coreapi.UserDeletedEvent
import com.example.eventsourcedapp.coreapi.UserUpdatedEvent

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
data class User(
    @AggregateIdentifier
    var username: String? = null,
    var password: String? = null
) {
    constructor() : this(null, null)

    @CommandHandler
    constructor(command: CreateUserCommand) : this(command.username, command.password) {
        // TODO: Validate input here
        AggregateLifecycle.apply(
            UserCreatedEvent(
                command.username,
                // TODO: Hash this password:
                command.password
            )
        )
    }

    @CommandHandler
    fun handle(command: UpdateUserCommand) {
        // TODO: Validate input here
        AggregateLifecycle.apply(
            UserUpdatedEvent(
                command.username,
                // TODO: Hash this password:
                command.password
            )
        )
    }

    @CommandHandler
    fun handle(command: DeleteUserCommand) {
        // TODO: Validate input here / handle username not found exceptions
        AggregateLifecycle.apply(UserDeletedEvent(command.username))
    }

    @EventSourcingHandler
    fun on(event: UserCreatedEvent) {
        username = event.username
        password = event.password
    }

    @EventSourcingHandler
    fun on(event: UserUpdatedEvent) {
        username = event.username
        password = event.password
    }

    @EventSourcingHandler
    fun on(event: UserDeletedEvent) {
        AggregateLifecycle.markDeleted()
    }
}
