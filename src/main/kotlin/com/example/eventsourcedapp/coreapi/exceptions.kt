package com.example.eventsourcedapp.coreapi

import org.axonframework.commandhandling.CommandExecutionException
import org.springframework.http.HttpStatus

data class RestExceptionDetails(
    val message: String,
    val httpCode: HttpStatus,
)

class UsernameAlreadyTakenException() : CommandExecutionException(
    null,
    null,
    RestExceptionDetails("This username is already taken", HttpStatus.BAD_REQUEST)
)

class UserNotFound() : CommandExecutionException(
    null,
    null,
    RestExceptionDetails("User not found", HttpStatus.NOT_FOUND)
)
