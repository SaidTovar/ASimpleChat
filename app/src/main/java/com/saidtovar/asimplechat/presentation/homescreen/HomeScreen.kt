package com.saidtovar.asimplechat.presentation.homescreen

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.saidtovar.asimplechat.domain.model.RecentChat
import com.saidtovar.asimplechat.presentation.components.ItemFirstLetterProfile

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onChatClick: (Long) -> Unit
) {

    val context = LocalContext.current

    PermissionHandler {
        viewModel.onPermissionGranted()
    }

    val uiState by viewModel.uiState.collectAsState()

    Scaffold (
        topBar = {
            HomeTopBar()
        },
        floatingActionButton = {
            HomeFloatingActionButton {
                if (uiState.ispermissionGranted) {
                    viewModel.onNewChatClick()
                } else {
                    Toast.makeText(context, "Permisos sms requeridos", Toast.LENGTH_SHORT).show()
                }
            }
        },
        floatingActionButtonPosition = FabPosition.EndOverlay
    ) { paddingValues ->

        HomeContent(
            paddingValues = paddingValues,
            chats = uiState.recentChats,
            onChatClick = onChatClick,
        )
    }

    if (uiState.showNewChatDialog) {
        DialogAddChat(
            onSend = { viewModel.onSendMessage() },
            onDismissRequest = { viewModel.onDismissDialog() },
            onAddressChange = { viewModel.onAddressChange(it) },
            address = uiState.address,
            onMessageChange = { viewModel.onMessageChange(it) },
            message = uiState.message
        )
    }
}

@Composable
fun PermissionHandler(
    onGranted: () -> Unit
) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.entries.all { it.value }
        if (granted) {
            onGranted()
        } else {
            Toast.makeText(context, "Permisos requeridos", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        launcher.launch(
            arrayOf(
                Manifest.permission.SEND_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_SMS
            )
        )
    }
}


@Composable
fun HomeTopBar() {
    Column(
        modifier = Modifier.background(Color.Black).padding(16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Chats",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }
}

@Composable
fun HomeContent(
    paddingValues: PaddingValues,
    chats: List<RecentChat>,
    onChatClick: (Long) -> Unit,
) {

    Column(
        modifier = Modifier.background(Color.Black)
            .fillMaxSize()
            .padding(paddingValues)
    ) {

        val lazyListState = rememberLazyListState()

        LaunchedEffect(chats) {

            lazyListState.animateScrollToItem(0)

        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyListState
        ) {
            items(items = chats, key = { it.id }) { recentChat ->
                ItemRecentChat(
                    recentChat = recentChat,
                    onClick = { onChatClick(recentChat.threadId) }
                )
            }
        }
    }
}

@Composable
fun ItemRecentChat(
    recentChat: RecentChat,
    onClick: () -> Unit,
) {
    Row (
        modifier = Modifier
            .clickable { onClick() }
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ){

        ItemFirstLetterProfile(
            name = recentChat.nameChat,
            background = Brush.verticalGradient(
                listOf(
                    Color.LightGray,
                    Color.DarkGray
                )
            )
        )

        Column (
            modifier = Modifier.weight(1f)
        ){

            Text(
                text = recentChat.nameChat,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = if (recentChat.isMe) "Tu: ${recentChat.lastMessage}" else recentChat.lastMessage,
                fontSize = 14.sp,
                color = Color.Gray,
                        maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

        }

        Column (
            horizontalAlignment = Alignment.End
        ){

            Text(
                text = recentChat.lastMessageDate,
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}

@Composable
fun HomeFloatingActionButton(
    onClick: () -> Unit
){
    FloatingActionButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add"
        )
    }
}

@Composable
fun DialogAddChat(
    onSend: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
    onAddressChange: (String) -> Unit = {},
    address: String,
    onMessageChange: (String) -> Unit = {},
    message: String
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier.padding(10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        1.dp,
                        Color.Gray,
                        RoundedCornerShape(10.dp)
                    )
                    .background(Color.White)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Nuevo Chat",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(40.dp))

                HorizontalDivider(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .clip(CircleShape)
                )

                OutlinedTextField(
                    value = address,
                    onValueChange = onAddressChange,
                    label = { Text("Número de teléfono") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = message,
                    onValueChange = onMessageChange,
                    label = { Text("Mensaje") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = onSend,
                    modifier = Modifier.padding(top = 20.dp)
                ) {
                    Text("Enviar")
                }

            }

        }

    }
}