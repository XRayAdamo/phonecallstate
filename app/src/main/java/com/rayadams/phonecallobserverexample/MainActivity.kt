package com.rayadams.phonecallobserverexample

import android.content.IntentFilter
import android.os.Bundle
import android.telephony.TelephonyManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.rayadams.phonecallobserverexample.helpers.NavigationPath
import com.rayadams.phonecallobserverexample.helpers.PhoneCallReceiver
import com.rayadams.phonecallobserverexample.repositories.PhoneCallStatusRepository
import com.rayadams.phonecallobserverexample.ui.theme.PhoneCallObserverExampleTheme
import com.rayadams.phonecallobserverexample.views.ChatScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var phoneCallStatusRepository: PhoneCallStatusRepository

    private val phoneCallReceiver = PhoneCallReceiver(
        onIncomingCallAccepted = {
            phoneCallStatusRepository.onIncomingCallAcceptedNotifyObservers()
        },
        onIncomingCallEnded = {
            phoneCallStatusRepository.onIncomingCallEndedNotifyObservers()
        },
        onOutgoingCallEnded = {
            phoneCallStatusRepository.onOutgoingCallEndedNotifyObservers()
        },
        onOutgoingCallAccepted = {
            phoneCallStatusRepository.onOutgoingCallAcceptedNotifyObservers()
        },
        onIncomingCallRinging = {
            phoneCallStatusRepository.onIncomingCallRinging()
        }
    )

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val phoneAccessPermission = rememberPermissionState(
                android.Manifest.permission.READ_PHONE_STATE
            )

            PhoneCallObserverExampleTheme {
                val navController: NavHostController = rememberNavController()

                LaunchedEffect(key1 = true) {
                    initHooks()
                }

                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    if (!phoneAccessPermission.status.isGranted) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            Text("Please click button to grant permission")
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { phoneAccessPermission.launchPermissionRequest() }) {
                                Text("Grant Permission")
                            }
                        }
                    } else {
                        NavigationView(navController)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        if (phoneCallReceiver.isRegistered) {
            unregisterReceiver(phoneCallReceiver)
        }
        super.onDestroy()
    }

    private fun initHooks() {
        registerReceiver(phoneCallReceiver, IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED))
        phoneCallReceiver.isRegistered = true
    }

    @Composable
    fun NavigationView(navController: NavHostController) {
        NavHost(navController = navController, startDestination = NavigationPath.MAIN_SCREEN) {
            composable(NavigationPath.MAIN_SCREEN) {
                ChatScreen()
            }
        }
    }

}

