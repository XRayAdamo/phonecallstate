package com.rayadams.phonecallobserverexample.models

data class MessageModel(val message: String, val time: String, val type: MessageType)

enum class MessageType {
    INCOMING,
    OUTGOING,
    SYSTEM
}