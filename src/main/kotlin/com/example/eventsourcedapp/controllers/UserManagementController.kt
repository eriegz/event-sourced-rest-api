package com.example.eventsourcedapp.controllers

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import com.example.eventsourcedapp.models.CreateUserRequest

@RestController
@RequestMapping("api/user")
class UserManagementController {

    @PostMapping("register")
    fun createUser(@RequestBody createUserReqBody: CreateUserRequest): Map<String, Any> {
        println("newUser:")
        return mapOf(
            "result" to "Called create user with username ${createUserReqBody.username}"
        )
    }

    @GetMapping("{username}")
    fun getUser(@PathVariable username: String): Map<String, Any> {
        return mapOf(
            "result" to "Called get user for username $username"
        )
    }

    @PutMapping("{username}")
    fun updateUser(@PathVariable username: String): Map<String, Any> {
        return mapOf(
            "result" to "Called update user for username $username"
        )
    }

    @DeleteMapping("{username}")
    fun deleteUser(@PathVariable username: String): Map<String, Any> {
        return mapOf(
            "result" to "Called delete user for username $username"
        )
    }
}
