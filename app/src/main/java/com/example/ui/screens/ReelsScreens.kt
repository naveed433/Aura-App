package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Crop
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material.icons.filled.FlipCameraAndroid
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ReelItem
import com.example.ui.theme.AuraBg
import com.example.ui.theme.AuraCoral
import com.example.ui.theme.AuraCoralDark
import com.example.ui.theme.AuraGold
import com.example.ui.theme.AuraMint
import com.example.ui.theme.AuraSurface
import com.example.ui.theme.AuraText
import com.example.ui.theme.AuraText2
import com.example.ui.theme.AuraText3
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// SCREEN 11 — REELS FEED (TikTok gesture physics, heart burst, sound page link)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReelsFeedScreen(
    reels: List<ReelItem>,
    onLikeReel: (String) -> Unit,
    onFollowToggle: (String) -> Unit,
    onRecordClick: () -> Unit,
    onSoundClick: (String) -> Unit,
    onBack: () -> Unit
) {
    var currentIndex by remember { mutableIntStateOf(0) }
    val currentReel = reels.getOrNull(currentIndex) ?: reels.first()
    var showCommentsSheet by remember { mutableStateOf(false) }
    var showBigHeart by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // Spinning Vinyl disc animation
    val infiniteTransition = rememberInfiniteTransition(label = "disc_spin")
    val discRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "disc_spin_angle"
    )

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .testTag("reels_feed_screen"),
        color = Color.Black
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(currentReel.gradientStart),
                            Color(currentReel.gradientEnd).copy(alpha = 0.85f),
                            Color.Black
                        )
                    )
                )
                // TikTok vertical swipe physics simulation
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        if (delta < -30) {
                            // Swipe up -> Next reel
                            currentIndex = (currentIndex + 1) % reels.size
                        } else if (delta > 30) {
                            // Swipe down -> Prev reel
                            currentIndex = if (currentIndex - 1 < 0) reels.size - 1 else currentIndex - 1
                        }
                    }
                )
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            if (!currentReel.isLiked) onLikeReel(currentReel.id)
                            showBigHeart = true
                            coroutineScope.launch {
                                delay(800)
                                showBigHeart = false
                            }
                        }
                    )
                }
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Text("Reels", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                IconButton(onClick = onRecordClick) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.25f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.CameraAlt, contentDescription = "Record", tint = Color.White, modifier = Modifier.size(20.dp))
                    }
                }
            }

            // Big popping double-tap heart with particle burst
            AnimatedVisibility(
                visible = showBigHeart,
                enter = scaleIn(tween(200)) + fadeIn(),
                exit = scaleOut(tween(300)) + fadeOut(),
                modifier = Modifier.align(Alignment.Center)
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Liked",
                    tint = AuraCoral,
                    modifier = Modifier.size(110.dp)
                )
            }

            // Bottom-Left Creator info & caption
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth(0.78f)
                    .padding(start = 16.dp, bottom = 32.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                            .background(AuraCoral),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = currentReel.creatorName.split(" ").mapNotNull { it.firstOrNull()?.toString() }.joinToString(""),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(currentReel.creatorHandle, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.width(10.dp))

                    // Follow pill
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (currentReel.isFollowing) AuraCoral else Color.Transparent)
                            .border(1.5.dp, if (currentReel.isFollowing) AuraCoral else Color.White, RoundedCornerShape(14.dp))
                            .clickable { onFollowToggle(currentReel.id) }
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = if (currentReel.isFollowing) "Following" else "Follow",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = currentReel.caption,
                    color = Color.White,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Music track row with spinning vinyl -> taps open Sound Page
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onSoundClick(currentReel.soundTitle) }
                ) {
                    Icon(
                        imageVector = Icons.Default.MusicNote,
                        contentDescription = "Sound",
                        tint = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${currentReel.soundTitle} • ${currentReel.soundArtist}",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Right Action Rail (Heart, Comment, Save, Share, Gift, Music Disc)
            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Heart
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.4f))
                            .clickable { onLikeReel(currentReel.id) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Like",
                            tint = if (currentReel.isLiked) AuraCoral else Color.White,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                    Text("${currentReel.likesCount / 1000}K", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                // Comment
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.4f))
                            .clickable { showCommentsSheet = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.ChatBubble, contentDescription = "Comments", tint = Color.White, modifier = Modifier.size(24.dp))
                    }
                    Text("${currentReel.commentsCount}", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                // Save
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.4f))
                            .clickable { },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Bookmark, contentDescription = "Save", tint = Color.White, modifier = Modifier.size(24.dp))
                    }
                    Text("${currentReel.savesCount / 1000}K", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                // Share
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.4f))
                            .clickable { },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Share, contentDescription = "Share", tint = Color.White, modifier = Modifier.size(24.dp))
                    }
                    Text("${currentReel.sharesCount / 1000}K", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                // Gift
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.4f))
                            .clickable { },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.CardGiftcard, contentDescription = "Gift", tint = AuraGold, modifier = Modifier.size(24.dp))
                    }
                    Text("Gift", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                // Spinning Vinyl Disc
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .rotate(discRotation)
                        .clip(CircleShape)
                        .background(Color(0xFF1F1F1F))
                        .border(1.5.dp, Color.White.copy(alpha = 0.6f), CircleShape)
                        .clickable { onSoundClick(currentReel.soundTitle) },
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .clip(CircleShape)
                            .background(AuraCoral)
                    )
                }
            }
        }

        // Comments Bottom Sheet
        if (showCommentsSheet) {
            ModalBottomSheet(
                onDismissRequest = { showCommentsSheet = false },
                containerColor = AuraSurface,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "${currentReel.commentsCount} Comments",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = AuraText
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    listOf(
                        "Kenji" to "The camera grading on this is out of this world! 🔥",
                        "Sarah" to "Where can I purchase this exact piece?",
                        "Marco" to "Incredible lighting and flow, pure art ✨"
                    ).forEach { (author, text) ->
                        Row(modifier = Modifier.padding(vertical = 8.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(AuraCoral),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(author.take(1), color = Color.White, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(author, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = AuraText)
                                Text(text, fontSize = 13.sp, color = AuraText2)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }
    }
}

// SCREEN 12 — REEL RECORD
@Composable
fun ReelRecordScreen(
    soundName: String = "Golden Hours (Acoustic)",
    onNext: () -> Unit,
    onClose: () -> Unit
) {
    var selectedTime by remember { mutableStateOf("15s") }
    var isRecording by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .testTag("reel_record_screen"),
        color = Color(0xFF12121E)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Camera viewfinder simulation
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(listOf(Color(0xFF2A2A40), Color(0xFF0F0F1A)))
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Videocam, contentDescription = "Camera View", tint = Color.White.copy(alpha = 0.5f), modifier = Modifier.size(54.dp))
                    Text("4K 60fps Viewfinder Active", color = Color.White.copy(alpha = 0.6f), fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.MusicNote, contentDescription = null, tint = AuraGold, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(soundName, color = AuraGold, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }

            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onClose) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Icon(Icons.Default.FlipCameraAndroid, contentDescription = "Flip", tint = Color.White)
                    Icon(Icons.Default.Speed, contentDescription = "Speed", tint = Color.White)
                    Icon(Icons.Default.Timer, contentDescription = "Timer", tint = Color.White)
                    Icon(Icons.Default.AutoAwesome, contentDescription = "Beauty", tint = AuraGold)
                }
            }

            // Bottom controls
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
                    .padding(bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Time options pill
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.Black.copy(alpha = 0.5f))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    listOf("15s", "30s", "60s", "Hands-free").forEach { time ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (selectedTime == time) AuraCoral else Color.Transparent)
                                .clickable { selectedTime = time }
                                .padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Text(time, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                // Big Record Button
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .border(4.dp, Color.White, CircleShape)
                        .padding(6.dp)
                        .clip(CircleShape)
                        .background(AuraCoral)
                        .clickable {
                            isRecording = !isRecording
                            if (!isRecording) onNext()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(if (isRecording) 24.dp else 48.dp)
                            .clip(if (isRecording) RoundedCornerShape(4.dp) else CircleShape)
                            .background(Color.White)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = if (isRecording) "Recording... Tap to stop" else "Tap to record or hold",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp
                )
            }
        }
    }
}

// SCREEN 13 — REEL EDITOR (LUT Filters & Speed Curves)
@Composable
fun ReelEditorScreen(
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    var selectedFilter by remember { mutableStateOf("Warm LUT") }
    var selectedSpeed by remember { mutableStateOf("1.0x") }

    val filters = listOf(
        "Normal" to Color(0xFF1A1A2E),
        "Warm LUT" to Color(0xFF8B4513),
        "Neo-Tokyo" to Color(0xFF4B0082),
        "Sunset Gold" to Color(0xFFB8860B),
        "Noir B&W" to Color(0xFF262626)
    )

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .testTag("reel_editor_screen"),
        color = Color(0xFF0D0D18)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Text("Edit Reel Studio", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Button(
                    onClick = onNext,
                    colors = ButtonDefaults.buttonColors(containerColor = AuraCoral),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Text("Next", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }

            // Video preview loop with active filter
            val activeFilterColor = filters.find { it.first == selectedFilter }?.second ?: Color(0xFF1A1A2E)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.verticalGradient(listOf(AuraCoralDark, activeFilterColor))
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🎬", fontSize = 48.sp)
                    Text("AURA Cinematic Preview ($selectedFilter)", color = Color.White, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Speed: $selectedSpeed • 60fps HDR", color = AuraGold, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Filmstrip timeline simulation
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(44.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White.copy(alpha = 0.1f))
                    .border(2.dp, AuraGold, RoundedCornerShape(8.dp)),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(6) {
                    Box(
                        modifier = Modifier
                            .width(42.dp)
                            .height(34.dp)
                            .background(AuraCoral.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Filter Presets Selector
            Text("Color Grading Filters", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 20.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filters) { (filterName, color) ->
                    val isSel = selectedFilter == filterName
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (isSel) AuraCoral else Color.White.copy(alpha = 0.15f))
                            .clickable { selectedFilter = filterName }
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(filterName, color = Color.White, fontSize = 12.sp, fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal)
                    }
                }
            }

            // Speed Ramping Selector
            Text("Speed Ramping", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp))
            Row(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("0.5x", "1.0x", "1.5x", "2.0x", "3.0x").forEach { spd ->
                    val isSel = selectedSpeed == spd
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSel) AuraGold else Color.White.copy(alpha = 0.15f))
                            .clickable { selectedSpeed = spd }
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(spd, color = if (isSel) Color.Black else Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

// SCREEN 14 — REEL POST
@Composable
fun ReelPostScreen(
    onPublished: () -> Unit,
    onBack: () -> Unit
) {
    var caption by remember { mutableStateOf("Ceramic pottery drop ✨ Hand-glazed with volcanic clay #aura #art #bali") }
    var allowDuet by remember { mutableStateOf(true) }
    var shareToFeed by remember { mutableStateOf(true) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag("reel_post_screen"),
        color = Color(0xFFFDF8F5)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = AuraCoral)
                    }
                    Text("Publish Reel", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = AuraText)
                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = caption,
                    onValueChange = { caption = it },
                    label = { Text("Write a caption and add #hashtags") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AuraCoral,
                        unfocusedContainerColor = AuraSurface
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = AuraSurface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Allow Duet & Remix", fontWeight = FontWeight.Medium, color = AuraText)
                            Switch(
                                checked = allowDuet,
                                onCheckedChange = { allowDuet = it },
                                colors = SwitchDefaults.colors(checkedTrackColor = AuraCoral)
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Share to Main Feed", fontWeight = FontWeight.Medium, color = AuraText)
                            Switch(
                                checked = shareToFeed,
                                onCheckedChange = { shareToFeed = it },
                                colors = SwitchDefaults.colors(checkedTrackColor = AuraCoral)
                            )
                        }
                    }
                }
            }

            Button(
                onClick = onPublished,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("publish_reel_button"),
                colors = ButtonDefaults.buttonColors(containerColor = AuraCoral),
                shape = RoundedCornerShape(26.dp)
            ) {
                Text("Share Reel", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

// SCREEN 16 / 49 — SOUND PAGE
@Composable
fun SoundPageScreen(
    soundName: String,
    onUseSound: () -> Unit,
    onBack: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .testTag("sound_page_screen"),
        color = AuraBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = AuraCoral)
                }
                Text("Sound", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = AuraText)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Spinning Vinyl illustration and track metadata
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF1E1E2E)),
                    contentAlignment = Alignment.Center
                ) {
                    Box(modifier = Modifier.size(24.dp).clip(CircleShape).background(AuraCoral))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(soundName, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = AuraText)
                    Text("Joji • Aura Sounds Official", fontSize = 13.sp, color = AuraText2)
                    Text("34K reels • 2.1M plays", fontSize = 11.sp, color = AuraText3)
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = onUseSound,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AuraCoral),
                shape = RoundedCornerShape(24.dp)
            ) {
                Icon(Icons.Default.MusicNote, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Use This Sound in Reel", color = Color.White, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text("Reels using this sound", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = AuraText)
            Spacer(modifier = Modifier.height(8.dp))

            val demoThumbnails = listOf(0xFFFFD9C9, 0xFFD4F1E8, 0xFFFFE8EA, 0xFFE8DEF8, 0xFFFFF5F0, 0xFFE0E7FF)
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(demoThumbnails) { hex ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(hex)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("▶️", fontSize = 24.sp)
                    }
                }
            }
        }
    }
}
