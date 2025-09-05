package com.saidtovar.asimplechat.data.repository

import com.saidtovar.asimplechat.data.local.sms.SmsContentProviderDataSource
import com.saidtovar.asimplechat.data.mappers.toMessage
import com.saidtovar.asimplechat.data.mappers.toRecentChat
import com.saidtovar.asimplechat.domain.model.Message
import com.saidtovar.asimplechat.domain.model.RecentChat
import com.saidtovar.asimplechat.domain.repository.SmsRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SmsRepositoryImpl @Inject constructor(
    private val smsContentProviderDataSource: SmsContentProviderDataSource
) : SmsRepository {

    override fun refreshRecentChats() {
        smsContentProviderDataSource.refreshrecentChats()
    }

    override fun getRecentChats(): Flow<List<RecentChat>> =
        smsContentProviderDataSource.recentChats
            .map { it.map { recentChatEntity -> recentChatEntity.toRecentChat() } }

    override fun getChatById(id: Long): Flow<List<Message>> {
        return smsContentProviderDataSource.getChatById(id)
            .map { it.map { smsEntity -> smsEntity.toMessage() } }
    }

    override fun getSmsList(chatId: Long): Flow<List<Message>> {
        return smsContentProviderDataSource.getSmsList(chatId)
            .map { it.map { smsEntity -> smsEntity.toMessage() } }
    }

    override fun sendMessage(message: String, address: String) {
        smsContentProviderDataSource.sendMessage(message, address)
    }

}