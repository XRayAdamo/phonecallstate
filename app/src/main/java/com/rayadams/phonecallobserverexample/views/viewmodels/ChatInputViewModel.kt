package com.rayadams.phonecallobserverexample.views.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayadams.phonecallobserverexample.repositories.ChatRepository
import com.rayadams.phonecallobserverexample.repositories.PhoneCallStatusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChatInputViewModel @Inject constructor(
    private val phoneCallStatusRepository: PhoneCallStatusRepository,
    private val chatRepository: ChatRepository
) : ViewModel() {
    var newMessage by mutableStateOf("")
        private set

    var isPhoneCallActive by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            phoneCallStatusRepository.incomingCallAccepted.collect {
                isPhoneCallActive = true
            }
        }
        viewModelScope.launch {
            phoneCallStatusRepository.outgoingCallAccepted.collect {
                isPhoneCallActive = true
            }
        }

        viewModelScope.launch {
            phoneCallStatusRepository.outgoingCallEnded.collect {
                isPhoneCallActive = false
            }
        }

        viewModelScope.launch {
            phoneCallStatusRepository.incomingCallEnded.collect {
                isPhoneCallActive = false
            }
        }
    }

    fun sendMessage() {
        chatRepository.sendMessage(newMessage, false)
        newMessage = ""
    }

    fun setNewMessageValue(message: String) {
        newMessage = message
    }
}
