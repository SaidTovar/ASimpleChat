package com.saidtovar.asimplechat.domain.repository

import com.saidtovar.asimplechat.domain.model.Message
import com.saidtovar.asimplechat.domain.model.RecentChat
import kotlinx.coroutines.flow.Flow

interface SmsRepository {

    fun getRecentChats(): Flow<List<RecentChat>>

    fun refreshRecentChats()

    fun getChatById(id: Long): Flow<List<Message>>

    fun getSmsList(chatId: Long): Flow<List<Message>>

    fun sendMessage(message: String, address: String)

}