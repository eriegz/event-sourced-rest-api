package com.example.eventsourcedapp.coreapi

class UsernameTakenException(message: String) : Exception(message)

class UsernameNotFound(message: String) : Exception(message)
