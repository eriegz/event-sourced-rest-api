package com.example.eventsourcedapp.services

import com.example.eventsourcedapp.query.UserViewRepository
import org.springframework.stereotype.Service

@Service
class UserService(val repository: UserViewRepository) {
    fun usernameExists(username: String): Boolean {
        return repository.findByUsername(username) != null
    }
}
