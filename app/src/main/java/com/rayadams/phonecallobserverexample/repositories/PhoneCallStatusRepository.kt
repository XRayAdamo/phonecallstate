package com.rayadams.phonecallobserverexample.repositories

import com.rayadams.phonecallobserverexample.helpers.EventStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface PhoneCallStatusRepository {
    val incomingCallAccepted: StateFlow<Unit>
    val incomingCallEnded: StateFlow<Unit>
    val outgoingCallAccepted: StateFlow<Unit>
    val outgoingCallEnded: StateFlow<Unit>
    val incomingCallRinging: StateFlow<Unit>

    fun onIncomingCallAcceptedNotifyObservers()
    fun onIncomingCallEndedNotifyObservers()
    fun onOutgoingCallAcceptedNotifyObservers()
    fun onOutgoingCallEndedNotifyObservers()
    fun onIncomingCallRinging()
}

class PhoneCallStatusRepositoryImpl: PhoneCallStatusRepository{
    private var _incomingCallAccepted = EventStateFlow()
    override val incomingCallAccepted = _incomingCallAccepted.asStateFlow()

    private var _outgoingCallAccepted = EventStateFlow()
    override val outgoingCallAccepted = _outgoingCallAccepted.asStateFlow()

    private var _incomingCallEnded = EventStateFlow()
    override val incomingCallEnded = _incomingCallEnded.asStateFlow()

    private var _outgoingCallEnded = EventStateFlow()
    override val outgoingCallEnded = _outgoingCallEnded.asStateFlow()

    private var _incomingCallRinging = EventStateFlow()
    override val incomingCallRinging = _incomingCallRinging.asStateFlow()

    override fun onIncomingCallEndedNotifyObservers() {
        _incomingCallEnded.notifyObservers()
    }

    override fun onIncomingCallAcceptedNotifyObservers() {
        _incomingCallAccepted.notifyObservers()
    }

    override fun onOutgoingCallAcceptedNotifyObservers() {
        _outgoingCallAccepted.notifyObservers()
    }

    override fun onIncomingCallRinging() {
        _incomingCallRinging.notifyObservers()
    }

    override fun onOutgoingCallEndedNotifyObservers() {
        _outgoingCallEnded.notifyObservers()
    }

}