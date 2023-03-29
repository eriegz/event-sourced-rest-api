package com.example.eventsourcedapp.query

import org.springframework.data.jpa.repository.JpaRepository

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class UserView(
    @Id val username: String,
    val password: String
) {
    constructor() : this("", "")
}

interface UserViewRepository : JpaRepository<UserView, String>
