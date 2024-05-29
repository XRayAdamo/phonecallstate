package com.rayadams.phonecallobserverexample.repositories

import com.rayadams.phonecallobserverexample.models.MessageModel
import com.rayadams.phonecallobserverexample.models.MessageType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Timer
import kotlin.concurrent.schedule

interface ChatRepository {
    val messages: Flow<List<MessageModel>>

    fun sendMessage(message: String, isSystemMessage: Boolean)
}

class ChatRepositoryImpl(private val phoneCallStatusRepository: PhoneCallStatusRepository) : ChatRepository {

    private val _messages = MutableStateFlow<List<MessageModel>>(emptyList())
    override val messages = _messages.asStateFlow()

    init {
        CoroutineScope(Dispatchers.Main).launch {
            phoneCallStatusRepository.outgoingCallAccepted.collect {
                sendDisconnectedMessage()
            }
        }
        CoroutineScope(Dispatchers.Main).launch {
            phoneCallStatusRepository.outgoingCallEnded.collect {
                sendConnectingMessage()
            }
        }
        CoroutineScope(Dispatchers.Main).launch {
            phoneCallStatusRepository.incomingCallAccepted.collect {
                sendDisconnectedMessage()
            }
        }
        CoroutineScope(Dispatchers.Main).launch {
            phoneCallStatusRepository.incomingCallEnded.collect {
                sendConnectingMessage()
            }
        }

    }

    private fun sendDisconnectedMessage() {
        sendMessage(
            message = "Phone call is active. Disconnecting from chat",
            isSystemMessage = true
        )
    }

    private fun sendConnectingMessage() {
        sendMessage(
            message = "Phone call is not active. Connecting to chat",
            isSystemMessage = true
        )
    }

    override fun sendMessage(message: String, isSystemMessage: Boolean) {
        _messages.value = messages.value + MessageModel(
            message = message,
            type = if (isSystemMessage) MessageType.SYSTEM else MessageType.OUTGOING,
            time = SimpleDateFormat.getTimeInstance().format(Calendar.getInstance().time),
        )

        if (!isSystemMessage) {
            //simulate answer
            Timer().schedule(100L) {

                _messages.value = messages.value + MessageModel(
                    message = "Answer to : $message",
                    type = MessageType.INCOMING,
                    time = SimpleDateFormat.getTimeInstance().format(Calendar.getInstance().time),
                )
            }
        }
    }
}
