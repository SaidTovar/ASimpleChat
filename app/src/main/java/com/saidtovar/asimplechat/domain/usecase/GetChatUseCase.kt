package com.saidtovar.asimplechat.domain.usecase

import com.saidtovar.asimplechat.domain.model.Message
import com.saidtovar.asimplechat.domain.repository.SmsRepository
import com.saidtovar.asimplechat.utils.StateData
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetChatUseCase @Inject constructor(
    private val repository: SmsRepository
){

    operator fun invoke(chatId: Long): Flow<StateData<List<Message>>> = flow {

        try {

            emit(StateData.Loading())

            repository.getChatById(chatId).collect { messages ->

                emit(StateData.Success(messages))

            }

        } catch (e: Exception){
            emit(StateData.Error(e.message.toString()))
        }


    }

}