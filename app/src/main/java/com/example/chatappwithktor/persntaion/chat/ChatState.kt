package com.example.chatappwithktor.persntaion.chat

import com.example.chatappwithktor.domain.model.Message

data class ChatState(
    val messages:List<Message> = emptyList(),
    val isLoading:Boolean = false
)
