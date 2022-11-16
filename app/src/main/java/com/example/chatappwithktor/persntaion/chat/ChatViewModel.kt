package com.example.chatappwithktor.persntaion.chat

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatappwithktor.data.remote.ChatSucketServer
import com.example.chatappwithktor.data.remote.MessageServies
import com.example.chatappwithktor.utils.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messageSerives: MessageServies,
    private val chatChokcetServer: ChatSucketServer,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _messageText = mutableStateOf("")
    val messegeText: State<String> =_messageText

    private val _chatstate = mutableStateOf<ChatState>(ChatState())
    val chatstate: State<ChatState> = _chatstate

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()

fun connectChat() {
    getAllMessge()
    savedStateHandle.get<String>("userName")?.let {
        urerName->
        viewModelScope.launch {
           val result= chatChokcetServer.initSesation(urerName)
            when(result){
                is Resources.Error -> {
                    _toastEvent.emit(result.message ?: "Unkown Error ")
                }
                is Resources.Succes ->{
                    chatChokcetServer.observerMssages().onEach {
                        meesage ->
                        val newList=chatstate.value.messages.toMutableList().apply {
                            add(0,meesage)
                        }

                        _chatstate.value=chatstate.value.copy(
                            messages=newList
                        )
                    }.launchIn(viewModelScope)
                }
            }
        }
    }
}
    fun onMessageChange(messge:String){
        _messageText.value=messge
    }

    fun disconnectServer(){
        viewModelScope.launch { chatChokcetServer.closeSection() }
    }

    fun getAllMessge(){
        viewModelScope.launch {
            _chatstate.value=chatstate.value.copy(isLoading = true)
             val result=messageSerives.getAllMessages()
            _chatstate.value = chatstate.value.copy(
                isLoading = false,
                messages = result
            )
        }

    }

    fun sendMessge(){
        viewModelScope.launch {
            if(messegeText.value.isNotBlank()){
                chatChokcetServer.sendMessage(messegeText.value)
            }
           }
    }

    override fun onCleared() {
        super.onCleared()
        disconnectServer()
    }
}