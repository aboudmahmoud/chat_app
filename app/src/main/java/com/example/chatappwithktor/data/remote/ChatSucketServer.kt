package com.example.chatappwithktor.data.remote


import com.example.chatappwithktor.domain.model.Message
import com.example.chatappwithktor.utils.Resources
import kotlinx.coroutines.flow.Flow


interface ChatSucketServer {
    suspend fun initSesation(
        userName:String
    ): Resources<Unit>

    suspend fun sendMessage(message:String)
   fun observerMssages(): Flow<Message>
   suspend fun closeSection()

    companion object{
        const val BASE_URL="ws://192.168.0.194:8080"
    }

    sealed class EndPoints(val url:String){
        object ChatSocketRoute:EndPoints("${BASE_URL}/chat-socket")
    }
}