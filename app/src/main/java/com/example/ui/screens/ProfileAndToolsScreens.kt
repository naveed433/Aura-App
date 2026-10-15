package com.example.ui.screens

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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.User
import com.example.data.WalletTransaction
import com.example.ui.theme.AuraBg
import com.example.ui.theme.AuraCoral
import com.example.ui.theme.AuraCoralDark
import com.example.ui.theme.AuraCoralSoft
import com.example.ui.theme.AuraGold
import com.example.ui.theme.AuraMint
import com.example.ui.theme.AuraMintDark
import com.example.ui.theme.AuraPeach
import com.example.ui.theme.AuraSuccess
import com.example.ui.theme.AuraSurface
import com.example.ui.theme.AuraSurface2
import com.example.ui.theme.AuraText
import com.example.ui.theme.AuraText2
import com.example.ui.theme.AuraText3

// SCREEN 20 — PROFILE
@Composable
fun ProfileScreen(
    user: User,
    onSettingsClick: () -> Unit,
    onQrClick: () -> Unit,
    onWalletClick: () -> Unit,
    onCreatorDashboardClick: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Photos", "Videos", "Tagged", "Saved")

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .testTag("profile_screen"),
        color = AuraBg
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 90.dp)
        ) {
            // Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Profile", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = AuraText)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        IconButton(onClick = onWalletClick) {
                            Icon(Icons.Default.Payment, contentDescription = "Wallet", tint = AuraCoral)
                        }
                        IconButton(onClick = onQrClick) {
                            Icon(Icons.Default.QrCode, contentDescription = "QR Code", tint = AuraText)
                        }
                        IconButton(onClick = onSettingsClick) {
                            Icon(Icons.Default.Settings, contentDescription = "Settings", tint = AuraText)
                        }
                    }
                }
            }

            // User Profile Info
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Avatar with edit badge
                    Box(contentAlignment = Alignment.BottomEnd) {
                        Box(
                            modifier = Modifier
                                .size(96.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(listOf(AuraCoral, AuraGold))
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(user.avatarInitials, color = Color.White, fontSize = 34.sp, fontWeight = FontWeight.Bold)
                        }
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(AuraCoral)
                                .border(2.dp, AuraBg, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.CameraAlt, contentDescription = "Edit photo", tint = Color.White, modifier = Modifier.size(14.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Text(user.name, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = AuraText)
                    Text("@${user.username}", fontSize = 14.sp, color = AuraText2)

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = user.bio,
                        fontSize = 13.5.sp,
                        color = AuraText2,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Stats Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        ProfileStatItem(user.postsCount.toString(), "Posts")
                        ProfileStatItem(user.followersCount, "Followers")
                        ProfileStatItem(user.followingCount, "Following")
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Action buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = onCreatorDashboardClick,
                            modifier = Modifier.weight(1f).height(44.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = AuraCoral),
                            shape = RoundedCornerShape(22.dp)
                        ) {
                            Icon(Icons.Default.Insights, contentDescription = "Dashboard", tint = Color.White, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Creator Hub", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        }

                        Button(
                            onClick = { },
                            modifier = Modifier.weight(1f).height(44.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = AuraSurface),
                            shape = RoundedCornerShape(22.dp)
                        ) {
                            Icon(Icons.Default.Share, contentDescription = "Share", tint = AuraCoral, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Share", color = AuraCoral, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Tabs
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        tabs.forEachIndexed { index, tabName ->
                            val isSelected = selectedTab == index
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .clickable { selectedTab = index }
                                    .padding(vertical = 8.dp)
                            ) {
                                Text(
                                    text = tabName,
                                    fontSize = 13.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) AuraCoral else AuraText3
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                if (isSelected) {
                                    Box(
                                        modifier = Modifier
                                            .width(28.dp)
                                            .height(3.dp)
                                            .clip(RoundedCornerShape(1.5.dp))
                                            .background(AuraCoral)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Photo Grid items
            item {
                val gridColors = listOf(0xFFFFD9C9, 0xFFD4F1E8, 0xFFFFE8EA, 0xFFE8DEF8, 0xFFFFF5F0, 0xFFE0E7FF)
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .padding(top = 8.dp)
                ) {
                    items(gridColors) { colorHex ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(118.dp)
                                .background(Color(colorHex)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("✨", fontSize = 24.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileStatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = AuraText)
        Text(label.uppercase(), fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = AuraText3)
    }
}

// SCREEN 25 — WALLET
@Composable
fun WalletScreen(
    balance: Double,
    transactions: List<WalletTransaction>,
    onBack: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .testTag("wallet_screen"),
        color = AuraBg
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = AuraCoral)
                }
                Text("Wallet", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = AuraText)
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                // Balance Card
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(6.dp, RoundedCornerShape(24.dp), spotColor = AuraCoral),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.linearGradient(listOf(AuraCoral, AuraCoralDark))
                                )
                                .padding(24.dp)
                        ) {
                            Column {
                                Text("AURA TOTAL BALANCE", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White.copy(alpha = 0.8f), letterSpacing = 1.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("$${String.format("%.2f", balance)}", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                Spacer(modifier = Modifier.height(20.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Button(
                                        onClick = { },
                                        modifier = Modifier.weight(1f).height(44.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.25f)),
                                        shape = RoundedCornerShape(14.dp)
                                    ) {
                                        Icon(Icons.Default.ArrowUpward, contentDescription = "Send", tint = Color.White)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Send", color = Color.White, fontWeight = FontWeight.SemiBold)
                                    }
                                    Button(
                                        onClick = { },
                                        modifier = Modifier.weight(1f).height(44.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.25f)),
                                        shape = RoundedCornerShape(14.dp)
                                    ) {
                                        Icon(Icons.Default.ArrowDownward, contentDescription = "Request", tint = Color.White)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Request", color = Color.White, fontWeight = FontWeight.SemiBold)
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Recent Transactions", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = AuraText)
                    Spacer(modifier = Modifier.height(10.dp))
                }

                items(transactions) { tx ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = AuraSurface)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(if (tx.isIncoming) AuraMint else AuraCoralSoft),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (tx.isIncoming) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward,
                                    contentDescription = null,
                                    tint = if (tx.isIncoming) AuraMintDark else AuraCoral
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(tx.title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = AuraText)
                                Text(tx.timestamp, fontSize = 11.sp, color = AuraText3)
                            }
                            Text(
                                text = "${if (tx.isIncoming) "+" else "-"}$${String.format("%.2f", tx.amount)}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = if (tx.isIncoming) AuraSuccess else AuraText
                            )
                        }
                    }
                }
            }
        }
    }
}

// SCREEN 29 — CREATOR DASHBOARD
@Composable
fun CreatorDashboardScreen(
    onBack: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .testTag("creator_dashboard_screen"),
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
                Text("Creator Studio & Monetization", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = AuraText)
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 2x2 Metric Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                DashboardMetricCard("Total Earnings", "$2,840.00", "+24% this week", AuraSuccess, Modifier.weight(1f))
                DashboardMetricCard("Video Views", "892K", "+18% growth", AuraCoral, Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                DashboardMetricCard("New Followers", "+12.4K", "142 today", AuraMintDark, Modifier.weight(1f))
                DashboardMetricCard("Reel Gifts", "$380.00", "42 gifts sent", AuraGold, Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Monetization Tools", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = AuraText)
            Spacer(modifier = Modifier.height(12.dp))

            listOf(
                "Direct Payouts" to "Instant bank transfer via Stripe & Web3 wallet",
                "Brand Partnerships" to "Connect with verified luxury lifestyle sponsors",
                "Exclusive Audio Rights" to "Monetize your original sound remix royalties"
            ).forEach { (title, subtitle) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = AuraSurface)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(title, fontWeight = FontWeight.SemiBold, color = AuraText, fontSize = 14.sp)
                            Text(subtitle, color = AuraText2, fontSize = 12.sp)
                        }
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = AuraText3)
                    }
                }
            }
        }
    }
}

@Composable
private fun DashboardMetricCard(
    title: String,
    value: String,
    delta: String,
    deltaColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(100.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = AuraSurface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title.uppercase(), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = AuraText3)
            Text(value, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = AuraText)
            Text(delta, fontSize = 11.sp, color = deltaColor, fontWeight = FontWeight.SemiBold)
        }
    }
}

// SCREEN 30 — QR CODE
@Composable
fun QrCodeScreen(
    user: User,
    onBack: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .testTag("qr_code_screen"),
        color = AuraBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = AuraCoral)
                }
                Text("My QR Code", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = AuraText)
            }

            Card(
                modifier = Modifier
                    .size(280.dp)
                    .shadow(12.dp, RoundedCornerShape(28.dp), spotColor = AuraCoral),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = AuraSurface)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(170.dp)
                            .background(Color(0xFF1A1A2E), RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🔳", fontSize = 100.sp)
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Text("@${user.username}", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = AuraText)
                    Text("Scan to connect on AURA", fontSize = 12.sp, color = AuraText2)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier.weight(1f).height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AuraCoral),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text("Save QR", color = Color.White, fontWeight = FontWeight.Bold)
                }
                Button(
                    onClick = { },
                    modifier = Modifier.weight(1f).height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AuraSurface),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text("Share", color = AuraCoral, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
