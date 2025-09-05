package com.saidtovar.asimplechat.presentation.chatscreen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saidtovar.asimplechat.domain.model.Message
import com.saidtovar.asimplechat.domain.usecase.GetChatUseCase
import com.saidtovar.asimplechat.domain.usecase.SentMessage
import com.saidtovar.asimplechat.utils.StateData
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

data class ChatUiState(
    val chatId: Long,
    val contactName: String,
    val messages: List<Message>,
    val currentMessage: String
)

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getChatUseCase: GetChatUseCase,
    private val sentMessage: SentMessage,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val chatId2: Long = checkNotNull(savedStateHandle["chatId"])

    var uiState = MutableStateFlow(
        ChatUiState(
            chatId = chatId2,
            contactName = "",
            messages = emptyList(),
            currentMessage = ""
        )
    )

    init {
        loadData()
        Log.d("ChatViewModel", "chatId: $chatId2")
    }

    private fun loadData() {

        viewModelScope.launch(Dispatchers.IO) {

            getChatUseCase(chatId2).collect { messages ->

                when (messages) {
                    is StateData.Loading -> {
                        Log.d("ChatViewModel", "Loading")
                    }
                    is StateData.Success -> {
                        Log.d("ChatViewModel", "Success: ${messages.data}")
                        uiState.value = uiState.value.copy(
                            messages = messages.data,
                            contactName = messages.data.firstOrNull()?.isMe.toString(),
                            currentMessage = uiState.value.currentMessage,
                            chatId = uiState.value.chatId,
                        )
                    }
                    is StateData.Error -> {
                        Log.d("ChatViewModel", "Error: $messages")
                    }
                }

            }

        }

    }

    fun onMessageChange(newMessage: String) {
        uiState.value = uiState.value.copy(currentMessage = newMessage)
    }

    fun sendMessage() {

        viewModelScope.launch {

            if (uiState.value.currentMessage.isNotEmpty()) {

                Log.d("ChatViewModel", "Message sent: ${uiState.value.currentMessage}")

                sentMessage(
                    address = uiState.value.messages.firstOrNull()?.address.toString(),
                    message = uiState.value.currentMessage,
                )

                uiState.value = uiState.value.copy(currentMessage = "")

            }

        }

    }

}