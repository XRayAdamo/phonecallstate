package com.rayadams.phonecallobserverexample.helpers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager


/**
 * Class to use with Phone call receivers
 */
class PhoneCallReceiver(
    var onOutgoingCallAccepted: () -> Unit,
    var onOutgoingCallEnded: () -> Unit,
    var onIncomingCallAccepted: () -> Unit,
    var onIncomingCallEnded: () -> Unit,
    var onIncomingCallRinging: () -> Unit
) :
    BroadcastReceiver() {
    var isRegistered = false

    override fun onReceive(context: Context?, intent: Intent?) {
        val state: Int = when (intent?.extras?.getString(TelephonyManager.EXTRA_STATE)) {
            TelephonyManager.EXTRA_STATE_IDLE -> TelephonyManager.CALL_STATE_IDLE
            TelephonyManager.EXTRA_STATE_OFFHOOK -> TelephonyManager.CALL_STATE_OFFHOOK
            TelephonyManager.EXTRA_STATE_RINGING -> TelephonyManager.CALL_STATE_RINGING
            else -> 0
        }
        onCallStateChanged(state)
    }

    private fun onCallStateChanged(state: Int?) {
        if (lastState == state) {
            return
        }

        when (state) {
            TelephonyManager.CALL_STATE_RINGING -> {
                isIncoming = true
                onIncomingCallRinging()
            }

            TelephonyManager.CALL_STATE_OFFHOOK -> {
                if (!isIncoming) {
                    onOutgoingCallAccepted()
                } else {
                    onIncomingCallAccepted()
                }
            }

            TelephonyManager.CALL_STATE_IDLE -> {
                if (isIncoming) {
                    onIncomingCallEnded()
                } else {
                    onOutgoingCallEnded()
                }

                isIncoming = false
            }
        }

        lastState = state as Int
    }

    companion object {
        private var lastState = TelephonyManager.CALL_STATE_IDLE
        private var isIncoming: Boolean = false

    }
}
