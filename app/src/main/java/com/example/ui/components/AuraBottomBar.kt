package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AuraScreen
import com.example.ui.theme.AuraCoral
import com.example.ui.theme.AuraCoralDark
import com.example.ui.theme.AuraSurface
import com.example.ui.theme.AuraText2
import com.example.ui.theme.AuraText3

@Composable
fun AuraBottomBar(
    currentScreen: AuraScreen,
    unreadChatsCount: Int,
    onTabSelected: (AuraScreen) -> Unit,
    onFabClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(16.dp, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
        color = AuraSurface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.navigationBars)
                .height(68.dp)
                .padding(horizontal = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Home
                AuraNavItem(
                    label = "Home",
                    iconFilled = Icons.Filled.Home,
                    iconOutlined = Icons.Outlined.Home,
                    isSelected = currentScreen == AuraScreen.MAIN_HOME,
                    onClick = { onTabSelected(AuraScreen.MAIN_HOME) },
                    testTag = "nav_home"
                )

                // Explore
                AuraNavItem(
                    label = "Explore",
                    iconFilled = Icons.Filled.Explore,
                    iconOutlined = Icons.Outlined.Explore,
                    isSelected = currentScreen == AuraScreen.MAIN_EXPLORE,
                    onClick = { onTabSelected(AuraScreen.MAIN_EXPLORE) },
                    testTag = "nav_explore"
                )

                // Spacer for floating FAB center
                Box(modifier = Modifier.size(54.dp))

                // Chats (WhatsApp style)
                AuraNavItem(
                    label = "Chats",
                    iconFilled = Icons.Filled.ChatBubble,
                    iconOutlined = Icons.Outlined.ChatBubbleOutline,
                    isSelected = currentScreen == AuraScreen.MAIN_CHATS,
                    badgeCount = unreadChatsCount,
                    onClick = { onTabSelected(AuraScreen.MAIN_CHATS) },
                    testTag = "nav_chats"
                )

                // Profile
                AuraNavItem(
                    label = "Profile",
                    iconFilled = Icons.Filled.Person,
                    iconOutlined = Icons.Outlined.Person,
                    isSelected = currentScreen == AuraScreen.MAIN_PROFILE,
                    onClick = { onTabSelected(AuraScreen.MAIN_PROFILE) },
                    testTag = "nav_profile"
                )
            }

            // Floating Center Coral Gradient FAB (+)
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = (-14).dp)
                    .size(56.dp)
                    .shadow(12.dp, CircleShape, spotColor = AuraCoral)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            listOf(AuraCoral, AuraCoralDark)
                        )
                    )
                    .clickable { onFabClick() }
                    .testTag("nav_fab_create"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}

@Composable
private fun AuraNavItem(
    label: String,
    iconFilled: ImageVector,
    iconOutlined: ImageVector,
    isSelected: Boolean,
    badgeCount: Int = 0,
    onClick: () -> Unit,
    testTag: String
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.08f else 1.0f,
        animationSpec = spring(dampingRatio = 0.6f),
        label = "nav_scale"
    )

    val interactionSource = remember { MutableInteractionSource() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() }
            .scale(scale)
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .testTag(testTag)
    ) {
        BadgedBox(
            badge = {
                if (badgeCount > 0) {
                    Badge(
                        containerColor = AuraCoral,
                        contentColor = Color.White
                    ) {
                        Text(
                            text = badgeCount.toString(),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        ) {
            Icon(
                imageVector = if (isSelected) iconFilled else iconOutlined,
                contentDescription = label,
                tint = if (isSelected) AuraCoral else AuraText3,
                modifier = Modifier.size(24.dp)
            )
        }
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
            color = if (isSelected) AuraCoral else AuraText2,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}
