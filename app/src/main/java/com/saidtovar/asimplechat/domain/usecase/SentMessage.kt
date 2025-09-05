package com.saidtovar.asimplechat.domain.usecase

import com.saidtovar.asimplechat.domain.repository.SmsRepository
import jakarta.inject.Inject

class SentMessage @Inject constructor(
    private val repository: SmsRepository
){

    operator fun invoke(message: String, address: String) {

        repository.sendMessage(message, address)

    }
}
