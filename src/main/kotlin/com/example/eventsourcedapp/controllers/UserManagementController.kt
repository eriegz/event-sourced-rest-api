package com.example.eventsourcedapp.controllers

import com.example.eventsourcedapp.coreapi.CreateUserCommand
import com.example.eventsourcedapp.coreapi.DeleteUserCommand
import com.example.eventsourcedapp.coreapi.RetrieveUserQuery
import com.example.eventsourcedapp.coreapi.UpdateUserCommand
import com.example.eventsourcedapp.models.CreateUserRequest
import com.example.eventsourcedapp.models.UpdateUserRequest
import com.example.eventsourcedapp.query.UserView

import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import java.util.UUID
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("api/user")
class UserManagementController(
    val commandGateway: CommandGateway,
    val queryGateway: QueryGateway,
) {
    @PostMapping("register")
    fun createUser(
        @RequestBody createUserReqBody: CreateUserRequest,
    ): CompletableFuture<ResponseEntity<Map<String, String>>> {
        return commandGateway.send<UUID>(
            CreateUserCommand(
                createUserReqBody.username,
                createUserReqBody.password
            )
        ).thenApply { ResponseEntity.ok(mapOf("userId" to it.toString())) }
    }

    @GetMapping("{userId}")
    fun getUser(@PathVariable userId: UUID): CompletableFuture<UserView> {
        return queryGateway.query(
            RetrieveUserQuery(userId),
            ResponseTypes.instanceOf(UserView::class.java)
        )
    }

    @PutMapping("{userId}")
    fun updateUser(
        @PathVariable userId: UUID,
        @RequestBody updateUserReqBody: UpdateUserRequest,
    ): CompletableFuture<ResponseEntity<Any>> {
        return commandGateway.send<Any>(
            UpdateUserCommand(userId, updateUserReqBody.username, updateUserReqBody.password)
        ).thenApply { ResponseEntity.noContent().build() }
    }

    @DeleteMapping("{userId}")
    fun deleteUser(@PathVariable userId: UUID): Map<String, Any> {
        val result: CompletableFuture<Any> = commandGateway.send(DeleteUserCommand(userId))
        println("Result of deleting user ${userId}: ${result.get()}")
        return mapOf(
            "result" to "Called delete user for username $userId"
        )
    }
}
