package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Reply
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SentimentSatisfiedAlt
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ChatMessage
import com.example.data.Conversation
import com.example.data.MessageStatus
import com.example.data.MessageType
import com.example.ui.components.AttachmentSheetModal
import com.example.ui.components.MessageReactionBar
import com.example.ui.theme.AuraBg
import com.example.ui.theme.AuraBlue
import com.example.ui.theme.AuraCoral
import com.example.ui.theme.AuraCoralDark
import com.example.ui.theme.AuraCoralSoft
import com.example.ui.theme.AuraGold
import com.example.ui.theme.AuraMint
import com.example.ui.theme.AuraMintDark
import com.example.ui.theme.AuraSuccess
import com.example.ui.theme.AuraSurface
import com.example.ui.theme.AuraSurface2
import com.example.ui.theme.AuraText
import com.example.ui.theme.AuraText2
import com.example.ui.theme.AuraText3
import kotlinx.coroutines.delay

// SCREEN 17 — CHATS LIST (WhatsApp style)
@Composable
fun ChatsListScreen(
    conversations: List<Conversation>,
    onConversationClick: (Conversation) -> Unit,
    onNewChatClick: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }

    val filteredList = conversations.filter {
        when (selectedFilter) {
            "Unread" -> it.unreadCount > 0
            "Groups" -> it.isGroup
            else -> true
        } && (it.name.contains(searchQuery, ignoreCase = true) || it.lastMessage.contains(searchQuery, ignoreCase = true))
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .testTag("chats_list_screen"),
        color = AuraBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text("Chats", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = AuraText)
                    Text(".", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = AuraCoral)
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(AuraCoral)
                            .clickable { onNewChatClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "New Chat", tint = Color.White, modifier = Modifier.size(22.dp))
                    }
                }
            }

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search conversations, messages...", fontSize = 13.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = AuraText3) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AuraCoral,
                    unfocusedContainerColor = AuraSurface,
                    focusedContainerColor = AuraSurface
                )
            )

            // Filter pills (All, Unread, Groups, Archived)
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("All", "Unread", "Groups", "Archived").forEach { filter ->
                    item {
                        val isSelected = selectedFilter == filter
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSelected) AuraCoral else AuraSurface)
                                .clickable { selectedFilter = filter }
                                .padding(horizontal = 16.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = filter,
                                color = if (isSelected) Color.White else AuraText2,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // Chats List
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(bottom = 90.dp)
            ) {
                items(filteredList) { convo ->
                    ConversationRow(
                        conversation = convo,
                        onClick = { onConversationClick(convo) }
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(start = 80.dp, end = 20.dp),
                        thickness = 0.5.dp,
                        color = AuraText3.copy(alpha = 0.15f)
                    )
                }
            }
        }
    }
}

@Composable
fun ConversationRow(
    conversation: Conversation,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar with online green dot
        Box {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(listOf(AuraCoral, AuraGold))
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = conversation.avatar,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
            }
            if (conversation.isOnline) {
                Box(
                    modifier = Modifier
                        .size(13.dp)
                        .clip(CircleShape)
                        .background(AuraSuccess)
                        .border(2.dp, AuraBg, CircleShape)
                        .align(Alignment.BottomEnd)
                )
            }
        }

        Spacer(modifier = Modifier.width(14.dp))

        // Name and last message preview
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = conversation.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = AuraText
                )
                Text(
                    text = conversation.time,
                    fontSize = 11.sp,
                    color = if (conversation.unreadCount > 0) AuraCoral else AuraText3,
                    fontWeight = if (conversation.unreadCount > 0) FontWeight.Bold else FontWeight.Normal
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (conversation.isTyping) "typing..." else conversation.lastMessage,
                    fontSize = 13.sp,
                    color = if (conversation.isTyping) AuraCoral else if (conversation.unreadCount > 0) AuraText else AuraText2,
                    fontWeight = if (conversation.isTyping || conversation.unreadCount > 0) FontWeight.Medium else FontWeight.Normal,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    if (conversation.disappearingMessagesTimer != null) {
                        Icon(Icons.Default.HourglassTop, contentDescription = "Disappearing", tint = AuraText3, modifier = Modifier.size(14.dp))
                    }
                    if (conversation.isPinned) {
                        Icon(Icons.Default.PushPin, contentDescription = "Pinned", tint = AuraCoral, modifier = Modifier.size(14.dp))
                    }
                    if (conversation.unreadCount > 0) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(AuraCoral)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = conversation.unreadCount.toString(),
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

// SCREEN 18 — CHAT DETAIL (WhatsApp style bubbles, voice note player, E2EE banner)
@Composable
fun ChatDetailScreen(
    conversation: Conversation,
    onBack: () -> Unit,
    onSendMessage: (String, String?) -> Unit,
    onSendVoiceNote: (Int) -> Unit,
    onToggleVoicePlayback: (String) -> Unit,
    onToggleVoiceSpeed: (String) -> Unit,
    onAudioCall: () -> Unit,
    onVideoCall: () -> Unit
) {
    var messageInput by remember { mutableStateOf("") }
    var replyToQuote by remember { mutableStateOf<String?>(null) }
    var showAttachmentSheet by remember { mutableStateOf(false) }
    var selectedMessageForReaction by remember { mutableStateOf<String?>(null) }
    var isRecordingAudio by remember { mutableStateOf(false) }
    var recordDurationSeconds by remember { mutableIntStateOf(0) }

    // Audio recording timer simulation
    LaunchedEffect(isRecordingAudio) {
        if (isRecordingAudio) {
            recordDurationSeconds = 0
            while (isRecordingAudio) {
                delay(1000)
                recordDurationSeconds++
            }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag("chat_detail_screen"),
        color = AuraBg
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header with Call & Video Call
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AuraSurface)
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = AuraCoral)
                }

                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(listOf(AuraCoral, AuraGold))
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(conversation.avatar, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(conversation.name, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = AuraText)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Lock, contentDescription = "E2EE", tint = AuraMintDark, modifier = Modifier.size(11.dp))
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = if (conversation.isTyping) "typing..." else if (conversation.isOnline) "Online • Encrypted" else "End-to-end encrypted",
                            fontSize = 11.sp,
                            color = if (conversation.isTyping) AuraCoral else AuraMintDark,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                IconButton(onClick = onAudioCall) {
                    Icon(Icons.Default.Call, contentDescription = "Voice Call", tint = AuraCoral)
                }
                IconButton(onClick = onVideoCall) {
                    Icon(Icons.Default.Videocam, contentDescription = "Video Call", tint = AuraCoral)
                }
                IconButton(onClick = { }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More", tint = AuraText3)
                }
            }

            // Messages Stream
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                reverseLayout = false
            ) {
                // E2EE Notice Pill
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(AuraSurface)
                                .border(0.5.dp, AuraMintDark.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                                .padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "🔒 Messages & calls are end-to-end encrypted. No one outside of this chat, not even Aura, can read them.",
                                fontSize = 11.sp,
                                color = AuraText2,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }

                items(conversation.messages) { msg ->
                    ChatBubbleItem(
                        message = msg,
                        onLongPress = { selectedMessageForReaction = msg.id },
                        onToggleVoice = { onToggleVoicePlayback(msg.id) },
                        onToggleSpeed = { onToggleVoiceSpeed(msg.id) },
                        onReplyClick = { replyToQuote = msg.text }
                    )
                }

                // Live Typing indicator simulation
                if (conversation.isTyping) {
                    item {
                        TypingIndicatorBubble()
                    }
                }
            }

            // Quick emoji reaction bar if a message is pressed
            AnimatedVisibility(visible = selectedMessageForReaction != null) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    MessageReactionBar(
                        onSelectEmoji = {
                            selectedMessageForReaction = null
                        }
                    )
                }
            }

            // Reply quote preview if replying to a message
            if (replyToQuote != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AuraSurface2)
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Reply, contentDescription = "Reply", tint = AuraCoral, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Replying: $replyToQuote", fontSize = 12.sp, color = AuraText, maxLines = 1)
                    }
                    IconButton(onClick = { replyToQuote = null }, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Cancel", tint = AuraText3, modifier = Modifier.size(16.dp))
                    }
                }
            }

            // Bottom Composer Bar or Audio Recording Bar
            if (isRecordingAudio) {
                // Recording mode
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AuraSurface)
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(AuraCoralDark)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "0:${if (recordDurationSeconds < 10) "0$recordDurationSeconds" else "$recordDurationSeconds"}",
                            fontWeight = FontWeight.Bold,
                            color = AuraCoralDark,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("Slide left to cancel", fontSize = 12.sp, color = AuraText3)
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Cancel",
                            color = AuraCoral,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .clickable { isRecordingAudio = false }
                                .padding(8.dp)
                        )
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(AuraCoral)
                                .clickable {
                                    isRecordingAudio = false
                                    onSendVoiceNote(recordDurationSeconds.coerceAtLeast(1))
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send Voice", tint = Color.White, modifier = Modifier.size(20.dp))
                        }
                    }
                }
            } else {
                // Standard Text composer bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AuraSurface)
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Emoji Icon
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.SentimentSatisfiedAlt, contentDescription = "Emoji", tint = AuraText3)
                    }

                    // Attachments button
                    IconButton(onClick = { showAttachmentSheet = true }) {
                        Icon(Icons.Default.AttachFile, contentDescription = "Attach", tint = AuraText3)
                    }

                    // Text Field
                    OutlinedTextField(
                        value = messageInput,
                        onValueChange = { messageInput = it },
                        placeholder = { Text("Message...", fontSize = 14.sp) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(22.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AuraCoral,
                            unfocusedContainerColor = AuraSurface2
                        )
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    if (messageInput.isNotBlank()) {
                        // Send button
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(AuraCoral)
                                .clickable {
                                    onSendMessage(messageInput, replyToQuote)
                                    messageInput = ""
                                    replyToQuote = null
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = Color.White, modifier = Modifier.size(18.dp))
                        }
                    } else {
                        // Mic button -> toggles voice recording mode
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(AuraCoral)
                                .clickable {
                                    isRecordingAudio = true
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Mic, contentDescription = "Voice Note", tint = Color.White, modifier = Modifier.size(20.dp))
                        }
                    }
                }
            }
        }

        // Attachments Modal
        if (showAttachmentSheet) {
            AttachmentSheetModal(
                onDismiss = { showAttachmentSheet = false },
                onItemSelected = { item ->
                    onSendMessage("Shared $item", null)
                }
            )
        }
    }
}

@Composable
fun ChatBubbleItem(
    message: ChatMessage,
    onLongPress: () -> Unit,
    onToggleVoice: () -> Unit,
    onToggleSpeed: () -> Unit,
    onReplyClick: () -> Unit
) {
    val isOut = message.isOutgoing

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (isOut) Arrangement.End else Arrangement.Start
    ) {
        Card(
            modifier = Modifier
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = { onLongPress() },
                        onDoubleTap = { onReplyClick() }
                    )
                }
                .shadow(if (isOut) 2.dp else 1.dp, RoundedCornerShape(
                    topStart = 18.dp,
                    topEnd = 18.dp,
                    bottomStart = if (isOut) 18.dp else 4.dp,
                    bottomEnd = if (isOut) 4.dp else 18.dp
                )),
            shape = RoundedCornerShape(
                topStart = 18.dp,
                topEnd = 18.dp,
                bottomStart = if (isOut) 18.dp else 4.dp,
                bottomEnd = if (isOut) 4.dp else 18.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = if (isOut) AuraCoral else AuraSurface
            )
        ) {
            Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 9.dp)) {
                // Reply quote header if replying
                if (message.replyToText != null) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 6.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (isOut) Color.White.copy(alpha = 0.2f) else AuraSurface2)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "↪ ${message.replyToText}",
                            fontSize = 11.sp,
                            color = if (isOut) Color.White else AuraText2,
                            maxLines = 1
                        )
                    }
                }

                if (message.type == MessageType.VOICE) {
                    // Interactive Voice note player with seeker waveform
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Play / Pause toggle button
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(if (isOut) Color.White.copy(alpha = 0.25f) else AuraCoralSoft)
                                .clickable { onToggleVoice() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (message.isPlayingVoice) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (message.isPlayingVoice) "Pause" else "Play",
                                tint = if (isOut) Color.White else AuraCoral,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        // Dynamic Waveform bars
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(2.5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { onToggleVoice() }
                        ) {
                            val heights = listOf(8, 16, 24, 12, 18, 28, 14, 20, 10, 22, 16, 26, 14, 8)
                            heights.forEachIndexed { index, height ->
                                val active = (index.toFloat() / heights.size) <= message.voiceProgress
                                Box(
                                    modifier = Modifier
                                        .width(2.5.dp)
                                        .height(height.dp)
                                        .background(
                                            if (isOut) {
                                                if (active) Color.White else Color.White.copy(alpha = 0.4f)
                                            } else {
                                                if (active) AuraCoral else AuraText3.copy(alpha = 0.4f)
                                            },
                                            RoundedCornerShape(1.dp)
                                        )
                                )
                            }
                        }

                        // Playback Speed pill button (1x -> 1.5x -> 2x)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isOut) Color.White.copy(alpha = 0.2f) else AuraSurface2)
                                .clickable { onToggleSpeed() }
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = message.playbackSpeed,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isOut) Color.White else AuraCoralDark
                            )
                        }

                        Text(
                            text = message.voiceDuration,
                            fontSize = 11.sp,
                            color = if (isOut) Color.White.copy(alpha = 0.9f) else AuraText2
                        )
                    }
                } else {
                    Text(
                        text = message.text,
                        fontSize = 14.sp,
                        color = if (isOut) Color.White else AuraText,
                        lineHeight = 20.sp
                    )
                }

                Spacer(modifier = Modifier.height(3.dp))

                Row(
                    modifier = Modifier.align(Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = message.timestamp,
                        fontSize = 10.sp,
                        color = if (isOut) Color.White.copy(alpha = 0.75f) else AuraText3
                    )
                    if (isOut) {
                        Spacer(modifier = Modifier.width(4.dp))
                        when (message.status) {
                            MessageStatus.SENT -> {
                                Icon(Icons.Default.Check, contentDescription = "Sent", tint = Color.White.copy(alpha = 0.7f), modifier = Modifier.size(13.dp))
                            }
                            MessageStatus.DELIVERED -> {
                                Icon(Icons.Default.DoneAll, contentDescription = "Delivered", tint = Color.White.copy(alpha = 0.7f), modifier = Modifier.size(13.dp))
                            }
                            MessageStatus.READ -> {
                                Icon(Icons.Default.DoneAll, contentDescription = "Read", tint = Color.White, modifier = Modifier.size(13.dp))
                            }
                            MessageStatus.SENDING -> {
                                Box(modifier = Modifier.size(10.dp).border(1.dp, Color.White, CircleShape))
                            }
                        }
                    }
                }
            }
        }
    }
}

// Bouncing Typing Indicator Bubble
@Composable
fun TypingIndicatorBubble() {
    val transition = rememberInfiniteTransition(label = "typing_bounce")
    val dot1Offset by transition.animateFloat(
        initialValue = 0f, targetValue = -6f,
        animationSpec = infiniteRepeatable(tween(400, delayMillis = 0, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "dot1"
    )
    val dot2Offset by transition.animateFloat(
        initialValue = 0f, targetValue = -6f,
        animationSpec = infiniteRepeatable(tween(400, delayMillis = 150, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "dot2"
    )
    val dot3Offset by transition.animateFloat(
        initialValue = 0f, targetValue = -6f,
        animationSpec = infiniteRepeatable(tween(400, delayMillis = 300, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "dot3"
    )

    Card(
        modifier = Modifier.padding(vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AuraSurface)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Elena is typing", fontSize = 12.sp, color = AuraText2)
            Spacer(modifier = Modifier.width(4.dp))
            Box(modifier = Modifier.size(5.dp).clip(CircleShape).background(AuraCoral))
            Box(modifier = Modifier.size(5.dp).clip(CircleShape).background(AuraCoral))
            Box(modifier = Modifier.size(5.dp).clip(CircleShape).background(AuraCoral))
        }
    }
}
