package com.example.eventsourcedapp.coreapi

data class UserCreatedEvent(
    val username: String,
    val password: String
)

data class UserUpdatedEvent(
    val username: String,
    val password: String
)

data class UserDeletedEvent(
    val username: String
)
