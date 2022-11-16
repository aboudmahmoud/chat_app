package com.example.chatappwithktor.domain.model

data class Message(
    val text:String,
    val formattedTime:String,
    val userName:String
)
