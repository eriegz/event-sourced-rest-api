package com.example.eventsourcedapp.coreapi

import java.util.UUID

data class UserCreatedEvent(
    val userId: UUID,
    val username: String,
    val password: String
)

data class UserUpdatedEvent(
    val userId: UUID,
    val username: String,
    val password: String
)

data class UserDeletedEvent(
    val userId: UUID
)
