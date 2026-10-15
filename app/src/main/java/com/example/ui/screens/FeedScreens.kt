package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CommentItem
import com.example.data.FeedPost
import com.example.data.StoryItem
import com.example.data.User
import com.example.ui.theme.AuraBg
import com.example.ui.theme.AuraCoral
import com.example.ui.theme.AuraCoralDark
import com.example.ui.theme.AuraCoralSoft
import com.example.ui.theme.AuraGold
import com.example.ui.theme.AuraLavender
import com.example.ui.theme.AuraMint
import com.example.ui.theme.AuraMintDark
import com.example.ui.theme.AuraPeach
import com.example.ui.theme.AuraSurface
import com.example.ui.theme.AuraSurface2
import com.example.ui.theme.AuraText
import com.example.ui.theme.AuraText2
import com.example.ui.theme.AuraText3
import kotlinx.coroutines.launch

// SCREEN 9 — HOME FEED
@Composable
fun HomeFeedScreen(
    currentUser: User,
    stories: List<StoryItem>,
    feedPosts: List<FeedPost>,
    onPostClick: (FeedPost) -> Unit,
    onLikePost: (String) -> Unit,
    onStoryClick: (StoryItem) -> Unit,
    onSearchClick: () -> Unit,
    onNotificationsClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .testTag("home_feed_screen"),
        color = AuraBg
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 90.dp)
        ) {
            // Top Bar
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Avatar pill
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(listOf(AuraCoral, AuraGold))
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = currentUser.avatarInitials,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }

                    // AURA. wordmark
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "AURA",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = AuraText,
                            letterSpacing = (-0.02).sp
                        )
                        Text(
                            text = ".",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = AuraCoral
                        )
                    }

                    // Search and notification buttons
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .shadow(2.dp, CircleShape)
                                .clip(CircleShape)
                                .background(AuraSurface)
                                .clickable { onSearchClick() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Search, contentDescription = "Search", tint = AuraText, modifier = Modifier.size(20.dp))
                        }
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .shadow(2.dp, CircleShape)
                                .clip(CircleShape)
                                .background(AuraSurface)
                                .clickable { onNotificationsClick() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = AuraText, modifier = Modifier.size(20.dp))
                            // Unread indicator dot
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(AuraCoral)
                            )
                        }
                    }
                }
            }

            // Stories Strip
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(stories) { story ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clickable { onStoryClick(story) }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                // Gradient ring for unseen stories
                                Box(
                                    modifier = Modifier
                                        .size(68.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (story.hasUnseen) {
                                                Brush.sweepGradient(listOf(AuraCoral, AuraGold, AuraCoralDark, AuraCoral))
                                            } else {
                                                Brush.linearGradient(listOf(AuraText3.copy(alpha = 0.3f), AuraText3.copy(alpha = 0.3f)))
                                            }
                                        )
                                        .padding(2.5.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(CircleShape)
                                            .background(AuraSurface)
                                            .padding(2.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .clip(CircleShape)
                                                .background(Color(story.storyImageColor)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = story.userAvatar,
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp
                                            )
                                        }
                                    }
                                }

                                if (story.isUserStory) {
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.BottomEnd)
                                            .size(20.dp)
                                            .clip(CircleShape)
                                            .background(AuraCoral)
                                            .border(1.5.dp, AuraSurface, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(Icons.Default.Add, contentDescription = "Add Story", tint = Color.White, modifier = Modifier.size(14.dp))
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = story.userName,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = AuraText2,
                                maxLines = 1
                            )
                        }
                    }
                }
            }

            // Greeting Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                        .shadow(4.dp, RoundedCornerShape(24.dp), spotColor = AuraCoral),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.linearGradient(listOf(AuraCoralSoft, AuraPeach))
                            )
                            .padding(20.dp)
                    ) {
                        Column {
                            Text(
                                text = "Good Day, ${currentUser.name.split(" ").first()}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = AuraText
                            )
                            Text(
                                text = "Where connections glow ✨ Quantum encrypted.",
                                fontSize = 13.sp,
                                color = AuraText2
                            )
                            Spacer(modifier = Modifier.height(14.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color.White)
                                    .clickable { }
                                    .padding(horizontal = 14.dp, vertical = 8.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("Invite Friends", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = AuraCoral)
                                }
                            }
                        }
                    }
                }
            }

            // Quick Actions Row
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionCard("Nearby", Icons.Default.NearMe, AuraCoralSoft, AuraCoral, Modifier.weight(1f))
                    QuickActionCard("Trending", Icons.Default.TrendingUp, AuraMint, AuraMintDark, Modifier.weight(1f))
                    QuickActionCard("For You", Icons.Default.AutoAwesome, AuraLavender, AuraCoralDark, Modifier.weight(1f))
                }
            }

            // Feed Posts
            items(feedPosts) { post ->
                PostCard(
                    post = post,
                    onPostClick = { onPostClick(post) },
                    onLikeClick = { onLikePost(post.id) }
                )
            }
        }
    }
}

@Composable
private fun QuickActionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    bgColor: Color,
    iconColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(72.dp)
            .shadow(2.dp, RoundedCornerShape(18.dp))
            .clickable { },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = AuraSurface)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(bgColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = title, tint = iconColor, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(title, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = AuraText)
        }
    }
}

@Composable
fun PostCard(
    post: FeedPost,
    onPostClick: () -> Unit,
    onLikeClick: () -> Unit
) {
    var showBigHeart by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .shadow(4.dp, RoundedCornerShape(22.dp)),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = AuraSurface)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Author row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(listOf(AuraCoral, AuraGold))
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(post.authorAvatar, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(post.authorName, fontWeight = FontWeight.SemiBold, color = AuraText, fontSize = 14.sp)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(post.timeAgo, color = AuraText3, fontSize = 12.sp)
                            if (post.location.isNotEmpty()) {
                                Text(" • ", color = AuraText3, fontSize = 12.sp)
                                Text(post.location, color = AuraCoral, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                }
                Icon(Icons.Default.MoreVert, contentDescription = "Options", tint = AuraText3)
            }

            // Post Content text
            Text(
                text = post.content,
                fontSize = 14.sp,
                lineHeight = 21.sp,
                color = AuraText,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clickable { onPostClick() }
            )

            // Post Media / Canvas with double-tap heart pop
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(Color(post.mediaBgColor))
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onDoubleTap = {
                                if (!post.isLiked) onLikeClick()
                                showBigHeart = true
                                coroutineScope.launch {
                                    kotlinx.coroutines.delay(800)
                                    showBigHeart = false
                                }
                            },
                            onTap = { onPostClick() }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("✨", fontSize = 36.sp)
                    Text(post.mediaLabel, color = AuraText.copy(alpha = 0.7f), fontSize = 12.sp, fontWeight = FontWeight.Medium)
                }

                // Double-tap popping heart
                if (showBigHeart) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Liked",
                        tint = AuraCoral,
                        modifier = Modifier.size(90.dp)
                    )
                }
            }

            // Action row (Like, Comment, Share, Save)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    // Like
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onLikeClick() }
                    ) {
                        Icon(
                            imageVector = if (post.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Like",
                            tint = if (post.isLiked) AuraCoral else AuraText2,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(post.likesCount.toString(), fontSize = 13.sp, color = AuraText2, fontWeight = FontWeight.Medium)
                    }

                    // Comment
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onPostClick() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ChatBubbleOutline,
                            contentDescription = "Comment",
                            tint = AuraText2,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(post.commentsCount.toString(), fontSize = 13.sp, color = AuraText2, fontWeight = FontWeight.Medium)
                    }

                    // Share
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { }
                    ) {
                        Icon(Icons.Default.Share, contentDescription = "Share", tint = AuraText2, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(post.sharesCount.toString(), fontSize = 13.sp, color = AuraText2, fontWeight = FontWeight.Medium)
                    }
                }

                // Bookmark Save
                IconButton(onClick = { }) {
                    Icon(Icons.Default.BookmarkBorder, contentDescription = "Save", tint = AuraText2)
                }
            }
        }
    }
}

// SCREEN 10 — POST DETAIL & COMMENTS
@Composable
fun PostDetailScreen(
    post: FeedPost,
    onBack: () -> Unit,
    onLikePost: () -> Unit
) {
    var commentText by remember { mutableStateOf("") }
    var commentsList by remember { mutableStateOf(post.comments) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag("post_detail_screen"),
        color = AuraBg
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = AuraCoral)
                }
                Text("Post", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = AuraText)
            }

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp)
            ) {
                // Post card in detail
                item {
                    PostCard(
                        post = post,
                        onPostClick = {},
                        onLikeClick = onLikePost
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "${commentsList.size} Comments",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = AuraText,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
                    )
                }

                // Comments
                items(commentsList) { comment ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(AuraCoralSoft),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(comment.avatar, color = AuraCoralDark, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(comment.author, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = AuraText)
                            Text(comment.text, fontSize = 13.5.sp, color = AuraText, lineHeight = 19.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Row {
                                Text(comment.timeAgo, fontSize = 11.sp, color = AuraText3)
                                Spacer(modifier = Modifier.width(12.dp))
                                Text("Reply", fontSize = 11.sp, color = AuraCoral, fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                }
            }

            // Bottom Comment Input
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AuraSurface)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = commentText,
                    onValueChange = { commentText = it },
                    placeholder = { Text("Add a thoughtful comment…", fontSize = 13.sp) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AuraCoral,
                        unfocusedContainerColor = AuraSurface2
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = {
                        if (commentText.isNotBlank()) {
                            commentsList = commentsList + CommentItem(
                                id = "c_${System.currentTimeMillis()}",
                                author = "Nova Sterling",
                                avatar = "NS",
                                text = commentText,
                                timeAgo = "Just now"
                            )
                            commentText = ""
                        }
                    }
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = AuraCoral)
                }
            }
        }
    }
}
