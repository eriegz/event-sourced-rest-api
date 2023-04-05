package com.example.eventsourcedapp.exceptionhandling

import com.example.eventsourcedapp.controllers.UserManagementController
import com.example.eventsourcedapp.coreapi.RestExceptionDetails

import org.axonframework.commandhandling.CommandExecutionException

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(assignableTypes = [UserManagementController::class])
class RestExceptionHandling {
    @ExceptionHandler(CommandExecutionException::class)
    fun handleAxonCommandExceptions(e: CommandExecutionException): ResponseEntity<Map<String, Any>> {
        val details: RestExceptionDetails? = e.getDetails<RestExceptionDetails>().orElse(null)
        if (details == null) {
            throw e
        }
        return ResponseEntity.status(details.httpCode).body(
            mapOf("error" to details.message)
        )
    }
}
