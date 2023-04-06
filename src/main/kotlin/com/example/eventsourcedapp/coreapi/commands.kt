package com.example.eventsourcedapp.coreapi

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.UUID

data class CreateUserCommand(
    val username: String,
    val password: String,
)

data class UpdateUserCommand(
    @TargetAggregateIdentifier
    val userId: UUID,
    val username: String,
    val password: String,
)

data class DeleteUserCommand(
    @TargetAggregateIdentifier
    val userId: UUID,
)
