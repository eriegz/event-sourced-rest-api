package com.example.eventsourcedapp.services

import com.example.eventsourcedapp.query.UserViewRepository
import org.springframework.stereotype.Service

@Service
class UserService(val repository: UserViewRepository) {
    fun usernameAlreadyTaken(username: String): Boolean {
        return repository.findByUsername(username) != null
    }
}
