package com.saidtovar.asimplechat.data.local.sms

import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.telephony.SmsManager
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class SmsContentProviderDataSource @Inject constructor(
    private val contentResolver: ContentResolver,
    private val appContext: android.content.Context
) {

    private val SMS_URI: Uri = "content://sms/".toUri()

    var recentChats = MutableStateFlow(emptyList<SmsEntity>())
        private set

    private val messages = MutableStateFlow(emptyList<SmsEntity>())
    private var index:Long = 0

    private val observer = object : ContentObserver(Handler(Looper.getMainLooper())) {
        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            refreshrecentChats()
            refreshMessages()
        }
    }

    init {
        // Registrar observer
        contentResolver.registerContentObserver(
            SMS_URI,
            true,
            observer
        )

        // Cargar mensajes iniciales
        refreshrecentChats()
    }

    fun getChatById(id: Long): Flow<List<SmsEntity>> {
        index = id
        refreshMessages()
        return messages
    }

    fun getSmsList(chatId: Long): Flow<List<SmsEntity>> = flow {
        emit(
            messages.value.filter { it.threadId == chatId }
        )
    }

    fun refreshrecentChats() {
        recentChats.value = getAllSms()
    }

    private fun refreshMessages() {
        messages.value = getSmsByThread(index.toString())
    }

    fun getAllSms(): List<SmsEntity> {

        if (ContextCompat.checkSelfPermission(appContext, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            return emptyList()
        }

        val smsList = mutableListOf<SmsEntity>()

        val projection = arrayOf(
            "_id",
            "address",
            "body",
            "date",
            "type", // 1 = INBOX, 2 = SENT
            "thread_id"
        )

        val sortOrder = "date DESC"

        val cursor: Cursor? = contentResolver.query(
            SMS_URI,
            projection,
            null,
            null,
            sortOrder
        )

        cursor?.use {
            val idIdx = it.getColumnIndexOrThrow("_id")
            val addressIdx = it.getColumnIndexOrThrow("address")
            val bodyIdx = it.getColumnIndexOrThrow("body")
            val dateIdx = it.getColumnIndexOrThrow("date")
            val typeIdx = it.getColumnIndexOrThrow("type")
            val threadIdIdx = it.getColumnIndexOrThrow("thread_id")

            while (it.moveToNext()) {
                smsList.add(
                    SmsEntity(
                        id = it.getLong(idIdx),
                        address = it.getString(addressIdx) ?: "",
                        body = it.getString(bodyIdx) ?: "",
                        date = it.getLong(dateIdx),
                        type = it.getInt(typeIdx),
                        threadId = (it.getString(threadIdIdx) ?: "0").toLong()
                    )
                )
            }
        }

        return smsList
    }

    fun getSmsByThread(threadId: String): List<SmsEntity> {
        val smsList = mutableListOf<SmsEntity>()

        val cursor: Cursor? = contentResolver.query(
            SMS_URI,
            arrayOf("_id", "address", "body", "date", "type", "thread_id"),
            "thread_id=?",
            arrayOf(threadId),
            "date ASC"
        )

        cursor?.use {
            val idIdx = it.getColumnIndexOrThrow("_id")
            val addressIdx = it.getColumnIndexOrThrow("address")
            val bodyIdx = it.getColumnIndexOrThrow("body")
            val dateIdx = it.getColumnIndexOrThrow("date")
            val typeIdx = it.getColumnIndexOrThrow("type")
            val threadIdIdx = it.getColumnIndexOrThrow("thread_id")

            while (it.moveToNext()) {
                smsList.add(
                    SmsEntity(
                        id = it.getLong(idIdx),
                        address = it.getString(addressIdx) ?: "",
                        body = it.getString(bodyIdx) ?: "",
                        date = it.getLong(dateIdx),
                        type = it.getInt(typeIdx),
                        threadId = (it.getString(threadIdIdx) ?: "0").toLong()
                    )
                )
            }
        }

        return smsList
    }

    fun sendMessage(message: String, address: String) {

        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(
            address,
            null,
            message,
            null,
            null
        )
    }

}