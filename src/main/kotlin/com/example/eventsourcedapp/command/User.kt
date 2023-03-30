package com.example.eventsourcedapp.command

import com.example.eventsourcedapp.coreapi.CreateUserCommand
import com.example.eventsourcedapp.coreapi.DeleteUserCommand
import com.example.eventsourcedapp.coreapi.UpdateUserCommand
import com.example.eventsourcedapp.coreapi.UserCreatedEvent
import com.example.eventsourcedapp.coreapi.UserDeletedEvent
import com.example.eventsourcedapp.coreapi.UserUpdatedEvent
import com.example.eventsourcedapp.coreapi.UsernameNotFound
import com.example.eventsourcedapp.coreapi.UsernameTakenException
import com.example.eventsourcedapp.services.UserService

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

import java.util.UUID

@Aggregate
data class User(
    @AggregateIdentifier
    var userId: UUID? = null,
    var username: String? = null,
    var password: String? = null,
    var userService: UserService? = null
) {
    constructor() : this(null, null, null, null)

    @CommandHandler
    constructor(command: CreateUserCommand, userService: UserService) : this(
        UUID.randomUUID(),
        command.username,
        command.password
    ) {
        if (userService.usernameExists(command.username)) {
            // TODO: Ensure this message gets back to the client:
            throw UsernameTakenException("This username is already taken")
        }
        AggregateLifecycle.apply(
            UserCreatedEvent(
                userId!!,
                command.username,
                // TODO: Hash this password:
                command.password
            )
        )
    }

    @CommandHandler
    fun handle(command: UpdateUserCommand, userService: UserService) {
        if (!userService.usernameExists(command.username)) {
            // TODO: Ensure this message gets back to the client:
            throw UsernameNotFound("Username not found")
        }
        AggregateLifecycle.apply(
            UserUpdatedEvent(
                command.userId,
                command.username,
                // TODO: Hash this password:
                command.password
            )
        )
    }

    @CommandHandler
    fun handle(command: DeleteUserCommand) {
        // TODO: Figure out some way to throw an error if user doesn't exist:
        AggregateLifecycle.apply(UserDeletedEvent(command.userId))
    }

    @EventSourcingHandler
    fun on(event: UserCreatedEvent) {
        println("Sourcing UserCreatedEvent...")
        userId = event.userId
        username = event.username
        password = event.password
    }

    @EventSourcingHandler
    fun on(event: UserUpdatedEvent) {
        println("Sourcing UserUpdatedEvent...")
        username = event.username
        password = event.password
    }

    @EventSourcingHandler
    fun on(event: UserDeletedEvent) {
        println("Deleting user aggregate...")
        AggregateLifecycle.markDeleted()
    }
}
