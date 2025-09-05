package com.saidtovar.asimplechat.presentation.chatscreen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.saidtovar.asimplechat.domain.model.Message
import com.saidtovar.asimplechat.presentation.components.ItemFirstLetterProfile
import kotlinx.coroutines.delay


@Composable
fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()

    val listMessages = uiState.messages

    Scaffold (
        topBar = {
            ChatTopBar(
                nameContact = listMessages.firstOrNull()?.address ?: "",
                onBack = onBack
            )
        },
        bottomBar = {
            ChatBottomBar(
                mensaje = uiState.currentMessage,
                onChangeMenssage = {
                    //currentMessage = it
                    viewModel.onMessageChange(it)
                },
                onSendMessage = {
                    //currentMessage = ""
                    viewModel.sendMessage()
                }
            )
        }
    ){ paddingValues ->

        ChatContent(
            paddingValues = paddingValues,
            messages = listMessages
        )
    }
}

@Composable
fun ChatTopBar(
    nameContact: String = "Jesica Paola Rodriguez",
    onBack: () -> Unit
) {

    Row (
        modifier = Modifier.background(Color.Black)
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){

        IconButton(
            onClick = onBack,
        ) {

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )

        }

        ItemFirstLetterProfile(
            name = nameContact
        )

        Text(
            text = nameContact,
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 8.dp)
        )
    }

}

@Composable
fun ChatContent(
    paddingValues: PaddingValues,
    messages: List<Message>,
) {

    val listState = rememberLazyListState()

    LaunchedEffect(key1 = messages.size) {
        listState.animateScrollToItem(0)
    }

    LazyColumn(
        state = listState,
        reverseLayout = true,
        modifier = Modifier.padding(paddingValues)
    ){

        items(messages.reversed(), key = {it.id}) { message ->
            MessageItem(message)
        }

    }

}

@Composable
fun MessageItem(message: Message) {

    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = if (message.isMe) Alignment.End else Alignment.Start
    ){

        Text(
            text = message.message,
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .background(
                    color = if (message.isMe) Color.Blue else Color.DarkGray,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp)
        )

        Row (
            modifier = Modifier.padding( horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){

            Text(
                text = message.time,
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            if (message.isMe) {

                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Seen",
                    tint = if (message.isSeen) Color.Green else Color.Gray,
                    modifier = Modifier.size(16.dp)
                )

            }

        }

    }

}

@Composable
fun ChatBottomBar(
    mensaje: String = "",
    onChangeMenssage: (String) -> Unit = {},
    onSendMessage: () -> Unit = {},
) {

    ConstraintLayout (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp)

    ) {

        val (
            idIcons,
            idTextInput,
            idIconSend
        ) = createRefs()

        var isWriting by remember { mutableStateOf(false) }

        val sizeTextMessage by animateDpAsState(
            targetValue = if (isWriting) 280.dp else 200.dp,
            label = ""
        )

        val focusRequester = remember { FocusRequester() }

        val layoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }

        LaunchedEffect(key1 = isWriting) {

            if (isWriting && mensaje.isBlank()) {
                delay(5000L)

                isWriting = false

            }

        }

        val numberOfLines = layoutResult.value?.lineCount ?: 0

        //Icons
        Row (modifier = Modifier
            .animateContentSize()
            .padding(end = 5.dp)
            .constrainAs(idIcons) {

                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)

            }

        ){

            Icon(
                if (isWriting) Icons.AutoMirrored.Filled.ArrowForwardIos else Icons.Rounded.AddCircle,
                contentDescription = "",
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(35.dp)
                    .clip(CircleShape)
                    .clickable {
                        if (isWriting) {
                            isWriting = false
                        }
                    }
                    .padding(4.dp),
                tint = Color(0xff48bad7)
            )

            if (!isWriting) {


                Icon(
                    Icons.Filled.PhotoCamera,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(35.dp)
                        .clip(CircleShape)
                        .clickable { }
                        .padding(4.dp),
                    tint = Color(0xff48bad7)
                )

                Icon(
                    Icons.Default.Photo,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(35.dp)
                        .clip(CircleShape)
                        .clickable { }
                        .padding(4.dp),
                    tint = Color(0xff48bad7)
                )

                Icon(
                    Icons.Default.Mic,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(35.dp)
                        .clip(CircleShape)
                        .clickable { }
                        .padding(4.dp),
                    tint = Color(0xff48bad7)
                )

            }

        }

        BasicTextField(
            value = mensaje,
            onValueChange = {

                onChangeMenssage(it)
                isWriting = true

            },
            modifier = Modifier
                .constrainAs(idTextInput) {

                    width = Dimension.fillToConstraints

                    if (numberOfLines < 2) {
                        top.linkTo(parent.top)
                    }

                    bottom.linkTo(parent.bottom)
                    start.linkTo(idIcons.end)
                    end.linkTo(idIconSend.start)

                }
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->

                    isWriting = focusState.isFocused

                }
                .defaultMinSize(minWidth = sizeTextMessage)
                .clip(RoundedCornerShape(15.dp)),
            onTextLayout = { layoutResult.value = it },
            maxLines = 6,
            textStyle = TextStyle(color = Color.White),
            cursorBrush = SolidColor(Color.White),

            decorationBox = { innerTextField ->

                Box(
                    modifier = Modifier
                        .background(color = Color.DarkGray)
                        .padding(vertical = 8.dp, horizontal = 16.dp)

                ) {


                    innerTextField()

                    if (mensaje.isBlank()) {

                        Text(
                            text = "Escribe un mensaje...",
                            style = TextStyle.Default.copy(color = Color.Gray),
                            modifier = Modifier.padding(start = 4.dp)
                        )

                    }


                }

            }
        )

        Icon(
            Icons.AutoMirrored.Filled.Send,
            contentDescription = "",
            modifier = Modifier
                .padding(start = 5.dp)
                .constrainAs(idIconSend) {

                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)

                }
                .padding(horizontal = 4.dp)
                .clip(CircleShape)
                .clickable {
                    if (mensaje.isNotBlank()) {
                        onSendMessage()
                    }
                }
                .size(35.dp)
                .padding(4.dp),
            tint = Color(0xff48bad7)
        )


    }

}