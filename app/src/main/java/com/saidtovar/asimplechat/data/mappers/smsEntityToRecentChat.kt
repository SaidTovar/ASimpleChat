package com.saidtovar.asimplechat.data.mappers

import com.saidtovar.asimplechat.data.local.sms.SmsEntity
import com.saidtovar.asimplechat.domain.model.RecentChat
import com.saidtovar.asimplechat.utils.toDate

fun SmsEntity.toRecentChat(): RecentChat {

    return RecentChat(
        id = this.id,
        nameChat = this.address,
        isMe = this.type == 2,
        lastMessage = this.body,
        lastMessageDate = this.date.toDate(),
        threadId = this.threadId,
    )

}