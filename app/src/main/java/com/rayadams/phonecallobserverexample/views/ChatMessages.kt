package com.rayadams.phonecallobserverexample.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rayadams.phonecallobserverexample.models.MessageModel
import com.rayadams.phonecallobserverexample.models.MessageType
import com.rayadams.phonecallobserverexample.views.viewmodels.ChatMessagesViewModel

@Composable
fun ChatMessages(modifier: Modifier = Modifier, viewModel: ChatMessagesViewModel = hiltViewModel()) {
    ChatMessagesContent(
        messages = viewModel.messages.value,
        modifier = modifier
    )
}

@Composable
private fun ChatMessagesContent(
    messages: List<MessageModel>,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    Column(modifier = modifier.padding(8.dp)) {
        LazyColumn(
            reverseLayout = true,
            horizontalAlignment = Alignment.Start,
            state = listState,
            modifier = Modifier
                .fillMaxSize()
        ) {
            messages.reversed().forEach {
                item {
                    Column {
                        Row(
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .fillMaxWidth()
                        ) {
                            if (it.type == MessageType.INCOMING) {
                                Text(it.time, modifier = Modifier.padding(end = 8.dp))
                            }
                            Text(
                                text = it.message,
                                fontWeight = if (it.type == MessageType.SYSTEM) FontWeight.Bold else FontWeight.Normal,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                textAlign = when (it.type) {
                                    MessageType.INCOMING -> TextAlign.Start
                                    MessageType.OUTGOING -> TextAlign.End
                                    MessageType.SYSTEM -> TextAlign.Center
                                },
                            )
                            if (it.type == MessageType.OUTGOING) {
                                Text(it.time, modifier = Modifier.padding(start = 8.dp))
                            }

                        }
                        HorizontalDivider()
                    }
                }
            }
        }
        LaunchedEffect(key1 = Unit) {
            if (messages.isNotEmpty()) {
                listState.scrollToItem(messages.size - 1)
            }
        }
    }
}

@Preview(name = "en LTR", locale = "en", showBackground = true, backgroundColor = 0xFFFFFFFF)
@Preview(name = "Tablet", locale = "en", showBackground = true, backgroundColor = 0xFFFFFFFF, widthDp = 1280, heightDp = 768)
@Preview(name = "en LTR 2f", locale = "en", fontScale = 2f, showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ChatMessagesContentPreview() {
    ChatMessagesContent(
        messages = listOf(
            MessageModel(
                "Hello",
                "12:00",
                type = MessageType.INCOMING

            ),
            MessageModel(
                "Phone call is active",
                "12:02",
                type = MessageType.SYSTEM
            ),
            MessageModel(
                "How are you?",
                "12:04",
                type = MessageType.OUTGOING
            ),
            MessageModel(
                "I'm fine, thanks",
                "12:05",
                type = MessageType.INCOMING
            ),
            MessageModel(
                "How are you?",
                "12:06",
                type = MessageType.OUTGOING
            ),
            MessageModel(
                "I'm fine, thanks",
                "12:08",
                type = MessageType.INCOMING
            ),
        ),
        modifier = Modifier.padding(8.dp)
    )
}
