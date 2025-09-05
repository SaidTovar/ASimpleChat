package com.saidtovar.asimplechat.domain.usecase

import com.saidtovar.asimplechat.domain.model.RecentChat
import com.saidtovar.asimplechat.domain.repository.SmsRepository
import com.saidtovar.asimplechat.utils.StateData
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRecentChatsUseCase @Inject constructor(
    private val repository: SmsRepository
){

    operator fun invoke(): Flow<StateData<List<RecentChat>>> = flow {

        try {

            emit(StateData.Loading())

            repository.getRecentChats().collect { recentChats ->

                emit(

                    StateData.Success(
                        recentChats.groupBy { it.nameChat }
                            .map { (nameChat, messages) ->
                                val lastMessage = messages.maxByOrNull { it.lastMessageDate }!!
                                RecentChat(
                                    nameChat = nameChat,
                                    lastMessage = lastMessage.lastMessage,
                                    id = lastMessage.id,
                                    isMe = lastMessage.isMe,
                                    lastMessageDate = lastMessage.lastMessageDate,
                                    threadId = lastMessage.threadId
                                )
                            }
                            .sortedByDescending { it.id }
                    )

                )
            }

        } catch (e: Exception) {

            e.printStackTrace()

            emit(StateData.Error("No se pudo obtener los chats recientes."))

        }


    }

}

