package com.rayadams.phonecallobserverexample.views.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayadams.phonecallobserverexample.repositories.PhoneCallStatusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ChatPhoneStateLogViewModel @Inject constructor(
    private val phoneCallStatusRepository: PhoneCallStatusRepository
) : ViewModel() {

    private val _list = mutableStateListOf<String>()
    val list: List<String> = _list
    private val formatter = SimpleDateFormat.getTimeInstance()

    init {
        viewModelScope.launch {
            phoneCallStatusRepository.incomingCallAccepted.collect {
                addToLog("onIncomingCallAccepted")
            }
        }
        viewModelScope.launch {
            phoneCallStatusRepository.outgoingCallAccepted.collect {
                addToLog("onOutgoingCallStarted")
            }
        }

        viewModelScope.launch {
            phoneCallStatusRepository.outgoingCallEnded.collect {
                addToLog("onOutgoingCallEnded")
            }
        }

        viewModelScope.launch {
            phoneCallStatusRepository.incomingCallEnded.collect {
                addToLog("onIncomingCallEnded")
            }
        }

        viewModelScope.launch {
            phoneCallStatusRepository.incomingCallRinging.collect {
                addToLog("onIncomingCallRinging")
            }
        }
    }

    private fun addToLog(value: String) {
        val time = Calendar.getInstance().time

        val current = formatter.format(time)
        _list.add("$current - $value")
    }

}