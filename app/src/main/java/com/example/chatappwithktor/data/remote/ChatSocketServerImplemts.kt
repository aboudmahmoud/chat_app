package com.example.chatappwithktor.data.remote

import com.example.chatappwithktor.data.remote.dto.MassgeDto
import com.example.chatappwithktor.domain.model.Message
import com.example.chatappwithktor.utils.Resources
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.utils.io.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ChatSocketServerImplemts(
    private val client: HttpClient
) : ChatSucketServer {

    private var socket: WebSocketSession? = null

    override suspend fun initSesation(userName: String): Resources<Unit> {
        return try {
            socket =
                client.webSocketSession { url( "${ChatSucketServer.EndPoints.ChatSocketRoute.url}?userName=${userName}" ) }
            if (socket?.isActive == true) {
                Resources.Succes(Unit)
            } else {
                Resources.Error("Couldent Estableh Connection", null)
            }
        } catch (e: Exception) {
            Resources.Error(e.message!!, null)
        }
    }

    override suspend fun sendMessage(message: String) {
        try {
            socket?.send(Frame.Text(message))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun observerMssages(): Flow<Message> {
        return try{
            socket?.incoming
                ?.receiveAsFlow()
                ?.filter { it is Frame.Text }
                ?.map {
                    val json = ( it as? Frame.Text) ?.readText() ?:""
                    val messageDto= Json.decodeFromString<MassgeDto>(json)
                    messageDto.toMessage()
                }?:flow{

            }
        }catch (e:Exception){
            e.printStackTrace()
            flow {}
        }
    }

    override suspend fun closeSection() {
        socket?.close()
    }
}