package com.example.chatappwithktor.data.remote

import com.example.chatappwithktor.data.remote.dto.MassgeDto
import com.example.chatappwithktor.domain.model.Message
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*


class MessageServieImplantion(
    private val client: HttpClient
):MessageServies {
    override suspend fun getAllMessages(
    ): List<Message> {
      return try {
          client.get(MessageServies.EndPoints.GetAllMessages.url).body<List<MassgeDto>>().map{
              it.toMessage()
          }
      }catch (e:Exception){
          emptyList()
      }
    }
}