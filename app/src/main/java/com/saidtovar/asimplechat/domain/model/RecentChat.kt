package com.saidtovar.asimplechat.domain.model

data class RecentChat(
    val id: Long,
    val nameChat: String,
    val isMe: Boolean,
    val lastMessage: String,
    val lastMessageDate: String,
    val threadId: Long,
)
