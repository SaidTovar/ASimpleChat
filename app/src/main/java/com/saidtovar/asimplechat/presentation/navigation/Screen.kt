package com.saidtovar.asimplechat.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Serializable
data class ChatScreen(val chatId: Long)