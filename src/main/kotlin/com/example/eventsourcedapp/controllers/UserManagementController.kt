package com.example.eventsourcedapp.controllers

import com.example.eventsourcedapp.coreapi.CreateUserCommand
import com.example.eventsourcedapp.coreapi.RetrieveUserQuery
import com.example.eventsourcedapp.models.CreateUserRequest
import com.example.eventsourcedapp.query.UserView

import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("api/user")
class UserManagementController(
    val commandGateway: CommandGateway,
    val queryGateway: QueryGateway
) {
    @PostMapping("register")
    fun createUser(@RequestBody createUserReqBody: CreateUserRequest): Map<String, Any> {
        val result: CompletableFuture<Any> = commandGateway.send(
            CreateUserCommand(
                createUserReqBody.username,
                createUserReqBody.password
            )
        )
        println("Result of creating new user ${createUserReqBody.username}: ${result.get()}")
        return mapOf(
            "result" to "Called create user with username ${createUserReqBody.username}"
        )
    }

    @GetMapping("{username}")
    fun getUser(@PathVariable username: String): CompletableFuture<UserView> {
        return queryGateway.query(
            RetrieveUserQuery(username),
            ResponseTypes.instanceOf(UserView::class.java)
        )
    }

    @PutMapping("{username}")
    fun updateUser(@PathVariable username: String): Map<String, Any> {
        // TODO: Implement command
        return mapOf(
            "result" to "Called update user for username $username"
        )
    }

    @DeleteMapping("{username}")
    fun deleteUser(@PathVariable username: String): Map<String, Any> {
        // TODO: Implement command
        return mapOf(
            "result" to "Called delete user for username $username"
        )
    }
}
