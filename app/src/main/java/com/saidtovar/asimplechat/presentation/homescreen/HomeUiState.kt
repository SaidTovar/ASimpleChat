package com.saidtovar.asimplechat.presentation.homescreen

import com.saidtovar.asimplechat.domain.model.RecentChat

data class HomeUiState(
    val recentChats: List<RecentChat> = emptyList(),
    val filteredChats: List<RecentChat> = emptyList(),
    val ispermissionGranted: Boolean = false,
    val showNewChatDialog: Boolean = false,
    val address: String = "",
    val message: String = "",
)