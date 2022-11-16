package com.example.chatappwithktor.utils

sealed class Resources<T>
    (
    val data: T? = null,
    val message: String? = null,
) {
    class Succes<T>(data: T?) : Resources<T>(data = data)
    class Error<T>(Message: String,data: T?) : Resources<T>(data=data,message = Message)
}
