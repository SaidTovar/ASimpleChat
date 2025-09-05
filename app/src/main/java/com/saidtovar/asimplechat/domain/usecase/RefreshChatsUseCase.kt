package com.saidtovar.asimplechat.domain.usecase

import com.saidtovar.asimplechat.domain.repository.SmsRepository
import jakarta.inject.Inject

class RefreshChatsUseCase @Inject constructor(
    private val repository: SmsRepository
){

    operator fun invoke() = repository.refreshRecentChats()

}