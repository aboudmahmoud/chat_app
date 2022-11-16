package com.example.chatappwithktor.persntaion.chat


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.chatappwithktor.domain.model.Message
import com.example.chatappwithktor.ui.theme.Maron
import com.example.chatappwithktor.ui.theme.NormaPruple
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chatscreen(
    userName: String?,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.toastEvent.collectLatest { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val obsver = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.connectChat()
            } else if (event == Lifecycle.Event.ON_STOP) {
                viewModel.disconnectServer()
            }
        }
        lifecycleOwner.lifecycle.addObserver(obsver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(obsver)
        }
    }

    val state = viewModel.chatstate.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            reverseLayout = true
        ) {
            item {
                Spacer(modifier = Modifier.height(32.dp))

            }
            items(state.messages) { message ->
                val isOwnMessg = message.userName == userName
                Box(
                    contentAlignment = if (isOwnMessg) {
                        Alignment.CenterEnd
                    } else {
                        Alignment.CenterStart
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .width(200.dp)
                            .drawBehind {
                                val cornerRadius = 10.dp.toPx()
                                val triangleHieght = 20.dp.toPx()
                                val triangelWidth = 25.dp.toPx()
                                val trianglePath = Path().apply {
                                    if (isOwnMessg) {
                                        moveTo(size.width, size.height - cornerRadius)
                                        lineTo(size.width, size.height + triangleHieght)
                                        lineTo(
                                            size.width - triangelWidth,
                                            size.height - cornerRadius
                                        )
                                        close()
                                    } else {
                                        moveTo(0f, size.height - cornerRadius)
                                        lineTo(0f, size.height + triangleHieght)
                                        lineTo(triangelWidth, size.height - cornerRadius)
                                        close()
                                    }
                                }
                                drawPath(
                                    path = trianglePath,
                                    color = if (isOwnMessg) Maron else NormaPruple
                                )
                            }
                            .background(
                                color = if (isOwnMessg) Maron else NormaPruple,
                                shape = RoundedCornerShape(10.dp)

                            )
                            .padding(8.dp)

                    ) {
                        Text(
                            text = message.userName,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = message.text,
                            color = Color.White
                        )
                        Text(
                            text = message.formattedTime,
                            color = Color.White,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }


                }
                Spacer(modifier = Modifier.height(32.dp))
            }

        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = viewModel.messegeText.value,
                onValueChange = viewModel::onMessageChange,
                placeholder = {
                    Text(text = "Entar Messeg")
                },
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = viewModel::sendMessge) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send"
                )
            }

        }
    }
}