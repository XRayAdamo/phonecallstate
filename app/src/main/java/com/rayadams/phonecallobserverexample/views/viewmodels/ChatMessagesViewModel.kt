package com.rayadams.phonecallobserverexample.views.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayadams.phonecallobserverexample.models.MessageModel
import com.rayadams.phonecallobserverexample.repositories.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatMessagesViewModel @Inject constructor(private val chatRepository: ChatRepository):ViewModel() {
    var messages = mutableStateOf<List<MessageModel>>(emptyList())
        private set
    init {
        viewModelScope.launch {
            chatRepository.messages.collect {
                messages.value = it
            }
        }
    }
}