package com.saidtovar.asimplechat.domain.model

data class Message(
    val id: Long,
    val message: String,
    val time: String,
    val isMe: Boolean,
    val isSeen: Boolean = true,
    val chatId: Long = 0,
    val address: String = ""
)
