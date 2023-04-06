package com.example.eventsourcedapp.exceptionhandling

import com.example.eventsourcedapp.controllers.UserManagementController
import com.example.eventsourcedapp.coreapi.RestExceptionDetails

import org.axonframework.commandhandling.CommandExecutionException
import org.axonframework.queryhandling.QueryExecutionException

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

data class ErrorResponseBody(
    val error: String,
)

@RestControllerAdvice(assignableTypes = [UserManagementController::class])
class RestExceptionHandling {
    fun sendErrorResponse(code: HttpStatus, message: String) =
        ResponseEntity.status(code).body(ErrorResponseBody(message))

    @ExceptionHandler(CommandExecutionException::class)
    fun handleCommandExceptions(e: CommandExecutionException): ResponseEntity<ErrorResponseBody> {
        val details: RestExceptionDetails? = e.getDetails<RestExceptionDetails>().orElse(null)
        if (details == null) {
            throw e
        }
        return sendErrorResponse(details.httpCode, details.message)
    }

    @ExceptionHandler(QueryExecutionException::class)
    fun handleQueryExceptions(e: QueryExecutionException): ResponseEntity<ErrorResponseBody> {
        val details: RestExceptionDetails? = e.getDetails<RestExceptionDetails>().orElse(null)
        if (details == null) {
            throw e
        }
        return sendErrorResponse(details.httpCode, details.message)
    }
}
