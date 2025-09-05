package com.saidtovar.asimplechat.data.local.sms

data class SmsEntity(
    val id: Long,
    val address: String,
    val body: String,
    val date: Long,
    val type: Int, // 1 = INBOX, 2 = SENT
    val threadId: Long
)