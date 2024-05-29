package com.rayadams.phonecallobserverexample.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rayadams.phonecallobserverexample.views.viewmodels.ChatInputViewModel

@Composable
fun ChatInput(modifier: Modifier = Modifier, viewModel: ChatInputViewModel = hiltViewModel()) {
    ChatInputContent(
        modifier = modifier,
        newMessage = viewModel.newMessage,
        isPhoneCallActive = viewModel.isPhoneCallActive,
        setNewMessage = viewModel::setNewMessageValue,
        sendMessage = viewModel::sendMessage
    )
}

@Composable
private fun ChatInputContent(
    modifier: Modifier,
    newMessage: String,
    isPhoneCallActive: Boolean,
    setNewMessage: (String) -> Unit,
    sendMessage: () -> Unit,
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            TextField(value = newMessage,
                modifier = Modifier.weight(1f),
                enabled = !isPhoneCallActive,
                onValueChange = {
                    setNewMessage(it)
                })
            IconButton(onClick = sendMessage, enabled = newMessage.isNotEmpty() && !isPhoneCallActive) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
            }
        }
    }
}

@Preview(name = "en LTR", locale = "en", showBackground = true, backgroundColor = 0xFFFFFFFF)
@Preview(name = "Tablet", locale = "en", showBackground = true, backgroundColor = 0xFFFFFFFF, widthDp = 1280, heightDp = 768)
@Preview(name = "en LTR 2f", locale = "en", fontScale = 2f, showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ChatInputContentPreview() {
    ChatInputContent(
        modifier = Modifier,
        newMessage = "new message",
        isPhoneCallActive = false,
        setNewMessage = {},
        sendMessage = {}
    )
}
