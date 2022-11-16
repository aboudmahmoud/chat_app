package com.example.chatappwithktor.di

import com.example.chatappwithktor.data.remote.ChatSocketServerImplemts
import com.example.chatappwithktor.data.remote.ChatSucketServer
import com.example.chatappwithktor.data.remote.MessageServieImplantion
import com.example.chatappwithktor.data.remote.MessageServies
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.json.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.kotlinx.json.*

import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.cbor.*

import kotlinx.serialization.cbor.*


import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppMoudel {

    @Provides
    @Singleton
    fun ProvideHttpClient():HttpClient{
        return HttpClient(CIO){
            install(Logging)
            install(WebSockets)
            install(ContentNegotiation){
                json()

            }
        }
    }

    @Provides
    @Singleton
    fun MessageServies(client: HttpClient):MessageServies{
        return MessageServieImplantion(client)
    }

    @Provides
    @Singleton
    fun ChatSucckeServies(client: HttpClient): ChatSucketServer {
        return ChatSocketServerImplemts(client)
    }
}