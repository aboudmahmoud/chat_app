package com.example.chatappwithktor.data.remote

import com.example.chatappwithktor.domain.model.Message

interface MessageServies {
    suspend fun getAllMessages():List<Message>
    companion object{
        const val BASE_URL="http://192.168.0.194:8080"
    }

    sealed class EndPoints(val url:String){
        object GetAllMessages:EndPoints("${BASE_URL}/messages")
    }
}