package com.saidtovar.asimplechat.presentation.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saidtovar.asimplechat.domain.usecase.GetRecentChatsUseCase
import com.saidtovar.asimplechat.domain.usecase.RefreshChatsUseCase
import com.saidtovar.asimplechat.domain.usecase.SentMessage
import com.saidtovar.asimplechat.utils.StateData
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRecentChatsUseCase: GetRecentChatsUseCase,
    private val refreshChats: RefreshChatsUseCase,
    private val sentMessage: SentMessage
) : ViewModel() {

    var uiState = MutableStateFlow(HomeUiState())
        private set

    init {

        loadData()

    }

    private fun loadData() {

        viewModelScope.launch {

            getRecentChatsUseCase().collect { recentChats ->

                when(recentChats){

                    is StateData.Error -> {

                        uiState.update { currentState ->

                            currentState.copy(recentChats = emptyList())

                        }

                    }

                    is StateData.Loading -> {

                        uiState.update { currentState ->

                            currentState.copy(recentChats = emptyList())

                        }

                    }

                    is StateData.Success -> {

                        uiState.update { currentState ->

                            currentState.copy(

                                recentChats = recentChats.data

                            )

                        }

                    }

                }
            }

        }

    }

    fun onPermissionGranted() {

        if (uiState.value.ispermissionGranted){
            return
        }

        uiState.update { currentState ->

            currentState.copy(
                ispermissionGranted = true
            )

        }

        refreshChats()

    }

    fun onNewChatClick(){

        uiState.update { currentState ->

            currentState.copy(
                showNewChatDialog = true
            )

        }

    }

    fun onDismissDialog(){

        uiState.update { currentState ->

            currentState.copy(
                showNewChatDialog = false
            )

        }

    }

    fun onSendMessage(){

        if (uiState.value.address.trim().isEmpty() ||
            uiState.value.message.trim().isEmpty()){
            return
        }

        sentMessage(
            uiState.value.message,
            uiState.value.address
        )

        uiState.update { currentState ->

            currentState.copy(
                showNewChatDialog = false,
                address = "",
                message = ""
            )

        }

    }

    fun onAddressChange(address: String){

        uiState.update { currentState ->

            currentState.copy(
                address = address
            )

        }

    }

    fun onMessageChange(message: String){

        uiState.update { currentState ->

            currentState.copy(
                message = message
            )

        }

    }

}