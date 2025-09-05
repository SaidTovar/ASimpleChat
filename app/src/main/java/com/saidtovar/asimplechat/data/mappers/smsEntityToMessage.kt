package com.saidtovar.asimplechat.data.mappers

import com.saidtovar.asimplechat.data.local.sms.SmsEntity
import com.saidtovar.asimplechat.domain.model.Message
import com.saidtovar.asimplechat.utils.toDate

fun SmsEntity.toMessage(): Message {
    return Message(
        id = this.id,
        chatId = this.id,
        message = this.body,
        isMe = this.type == 2,
        time = this.date.toDate(),
        address = this.address
    )
}