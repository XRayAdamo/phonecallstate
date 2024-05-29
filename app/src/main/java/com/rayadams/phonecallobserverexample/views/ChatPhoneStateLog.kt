package com.rayadams.phonecallobserverexample.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rayadams.phonecallobserverexample.views.viewmodels.ChatPhoneStateLogViewModel

@Composable
fun ChatPhoneStateLog(modifier: Modifier = Modifier, viewModel: ChatPhoneStateLogViewModel = hiltViewModel()) {
    ChatPhoneStateLogContent(
        modifier = modifier,
        list = viewModel.list
    )
}

@Composable
private fun ChatPhoneStateLogContent(modifier: Modifier, list: List<String>) {
    Column(modifier = modifier.background(Color.Cyan).padding(16.dp)) {
        Text("Chat Phone State Log:")
        LazyColumn(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
        ) {
            list.forEach {
                item {
                    Text(text = it)
                }
            }
        }
    }
}


@Preview(name = "en LTR", locale = "en", showBackground = true, backgroundColor = 0xFFFFFFFF)
@Preview(name = "Tablet", locale = "en", showBackground = true, backgroundColor = 0xFFFFFFFF, widthDp = 1280, heightDp = 768)
@Preview(name = "en LTR 2f", locale = "en", fontScale = 2f, showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ChatPhoneStateLogContentPreview() {
    ChatPhoneStateLogContent(
        modifier = Modifier,
        list = listOf("1", "2", "3")
    )
}
