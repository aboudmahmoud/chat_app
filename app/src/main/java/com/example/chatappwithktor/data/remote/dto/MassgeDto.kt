package com.example.chatappwithktor.data.remote.dto

import com.example.chatappwithktor.domain.model.Message
import kotlinx.serialization.Serializable
import java.text.DateFormat
import java.util.*


@Serializable
data class MassgeDto(
  val text:String,
  val timestamp: Long,
  val userName:String,
  val id:String
){
  fun toMessage(): Message{
    val date = Date(timestamp)
    val forrmatedDate=DateFormat.getDateInstance(DateFormat.DEFAULT).format(date)
    return Message(
      text=text,
      formattedTime = forrmatedDate,
      userName = userName
    )
  }
}
