package com.example.eventsourcedapp.query

import org.springframework.data.jpa.repository.JpaRepository

import java.util.UUID

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class UserView(
    // TODO:
    //  Figure out some way to persist these values as UUIDs instead of Strings. When I persist them via JPA as UUIDs,
    //  I'm not able to query them back out of the database. It's only when persisting them as strings that I'm able to
    //  query them back out again as strings.
    @Id val userId: String?,
    val username: String?,
    val password: String?
) {
    constructor() : this(null, null, null)
}

interface UserViewRepository : JpaRepository<UserView, String> {
    fun findByUsername(username: String): UserView?
}
