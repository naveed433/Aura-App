package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Tablet
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AdminMetrics
import com.example.data.EscrowOrder
import com.example.data.LinkedDevice
import com.example.data.ReportItem
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

// SCREEN 27 — SETTINGS
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onSafetyCenterClick: () -> Unit,
    onLinkedDevicesClick: () -> Unit,
    onAdminPanelClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .testTag("settings_screen"),
        color = AuraBg
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = AuraCoral)
                    }
                    Text("Settings", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = AuraText)
                }
            }

            // Aura Pro Premium Banner
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Brush.linearGradient(listOf(AuraCoral, AuraGold)))
                            .padding(20.dp)
                    ) {
                        Column {
                            Text("👑 AURA PRO PRIVACY & CLOUD", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White.copy(alpha = 0.85f), letterSpacing = 1.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Unlimited 4K Reels & E2EE Sync", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Spacer(modifier = Modifier.height(12.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.White)
                                    .padding(horizontal = 14.dp, vertical = 6.dp)
                            ) {
                                Text("Upgrade for $4.99/mo", color = AuraCoral, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // Admin Hub Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onAdminPanelClick() },
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = AuraSurface)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(AuraCoralSoft),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = AuraCoral)
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Admin Oversight Control Hub", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = AuraText)
                            Text("Monitor live users, reports & marketplace escrow", fontSize = 12.sp, color = AuraText2)
                        }
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = AuraText3)
                    }
                }
            }

            // Security & Devices
            item {
                Text("Security & Privacy", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = AuraText2)
                Spacer(modifier = Modifier.height(6.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = AuraSurface)
                ) {
                    Column {
                        SettingsRow("Linked Devices (Multi-Device E2EE)", Icons.Default.Devices, onLinkedDevicesClick)
                        SettingsRow("Safety Center & SOS", Icons.Default.Shield, onSafetyCenterClick)
                        SettingsRow("End-to-End Encryption Keys", Icons.Default.Lock, {})
                    }
                }
            }

            // Storage & Preferences
            item {
                Text("Preferences & Storage", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = AuraText2)
                Spacer(modifier = Modifier.height(6.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = AuraSurface)
                ) {
                    Column {
                        SettingsRow("Storage & Offline Cache (1.2 GB)", Icons.Default.Storage, {})
                        SettingsRow("Two-Factor Authentication", Icons.Default.Security, {})
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsRow(title: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = AuraCoral, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(title, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = AuraText)
        }
        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = AuraText3, modifier = Modifier.size(16.dp))
    }
}

// SCREEN 28 — SAFETY CENTER (Emergency SOS)
@Composable
fun SafetyCenterScreen(
    onBack: () -> Unit
) {
    var sosSent by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .testTag("safety_center_screen"),
        color = AuraBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
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
                    Text("Safety Center & SOS", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = AuraText)
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Emergency SOS Banner
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Brush.linearGradient(listOf(AuraCoralDark, AuraGold)))
                            .padding(20.dp)
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Warning, contentDescription = "SOS", tint = Color.White)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Emergency SOS Broadcast", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 17.sp)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Alert all 3 trusted contacts with your live encrypted GPS location.", color = Color.White.copy(alpha = 0.9f), fontSize = 13.sp)
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { sosSent = true },
                                colors = ButtonDefaults.buttonColors(containerColor = if (sosSent) AuraMintDark else Color.White),
                                shape = RoundedCornerShape(20.dp)
                            ) {
                                Text(
                                    text = if (sosSent) "✓ SOS Broadcast Active" else "Trigger Instant SOS",
                                    color = if (sosSent) Color.White else AuraCoralDark,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text("Trusted Contacts (3 Configured)", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = AuraText)
                Spacer(modifier = Modifier.height(8.dp))

                listOf("Elena Rostova (Partner)", "Marcus Vance (Colleague)", "Bali Tourist Police (+62 361 754590)").forEach { contact ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = AuraSurface)
                    ) {
                        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = AuraMintDark)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(contact, fontSize = 13.5.sp, fontWeight = FontWeight.Medium, color = AuraText)
                        }
                    }
                }
            }
        }
    }
}

// SCREEN 58 — LINKED DEVICES (Multi-device E2EE)
@Composable
fun LinkedDevicesScreen(
    devices: List<LinkedDevice>,
    onRevokeDevice: (String) -> Unit,
    onBack: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .testTag("linked_devices_screen"),
        color = AuraBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = AuraCoral)
                }
                Text("Linked Devices", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = AuraText)
            }

            Spacer(modifier = Modifier.height(14.dp))
            Text(
                "Use Aura across web, desktop, and tablet with synchronized end-to-end encryption keys.",
                fontSize = 13.sp,
                color = AuraText2
            )

            Spacer(modifier = Modifier.height(18.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(devices) { dev ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = AuraSurface)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(if (dev.isCurrent) AuraMint else AuraCoralSoft),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (dev.name.contains("MacBook")) Icons.Default.Computer else if (dev.name.contains("iPad")) Icons.Default.Tablet else Icons.Default.PhoneAndroid,
                                    contentDescription = null,
                                    tint = if (dev.isCurrent) AuraMintDark else AuraCoral
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(dev.name, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = AuraText)
                                Text("${dev.location} • ${dev.lastActive}", fontSize = 12.sp, color = AuraText2)
                            }
                            if (!dev.isCurrent) {
                                Text(
                                    text = "Log out",
                                    color = AuraCoralDark,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .clickable { onRevokeDevice(dev.id) }
                                        .padding(4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// ADMIN WEBSITE & MANAGEMENT PANEL (Requested by user)
@Composable
fun AdminPanelScreen(
    metrics: AdminMetrics,
    reports: List<ReportItem>,
    escrowOrders: List<EscrowOrder>,
    onResolveReport: (String) -> Unit,
    onBanUser: (String) -> Unit,
    onReleaseEscrow: (String) -> Unit,
    onBack: () -> Unit
) {
    var selectedTab by remember { mutableStateOf("Overview") }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .testTag("admin_panel_screen"),
        color = Color(0xFF0F0F1A)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = AuraCoral)
                }
                Column {
                    Text("AURA Command & Oversight Hub", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text("Enterprise Admin Console", fontSize = 11.sp, color = Color.White.copy(alpha = 0.6f))
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Navigation Tabs (Overview, Reports, Escrow Orders)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Overview", "Reports Queue", "Escrow Audit").forEach { tab ->
                    val isSel = selectedTab == tab
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSel) AuraCoral else Color(0xFF1E1E2E))
                            .clickable { selectedTab = tab }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(tab, color = Color.White, fontSize = 12.sp, fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium)
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            when (selectedTab) {
                "Overview" -> {
                    // Live Metrics 2x2 Grid
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        AdminMetricTile("Active Online Users", "${metrics.activeUsersCount}", "🟢 Real-time", AuraSuccess, Modifier.weight(1f))
                        AdminMetricTile("Server Health", "${metrics.serverHealthPercent}%", "⚡ All nodes up", AuraMint, Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        AdminMetricTile("Pending Reports", "${metrics.pendingReportsCount}", "⚠️ Moderation queue", AuraCoral, Modifier.weight(1f))
                        AdminMetricTile("Marketplace Today", "$${String.format("%.0f", metrics.totalTransactionsToday)}", "🛡️ Escrow active", AuraGold, Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Text("System Status Summary", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E))
                    ) {
                        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text("• Quantum-resistant E2EE Handshake Nodes: ${metrics.encryptionSyncNodes} online", color = AuraMint, fontSize = 12.sp)
                            Text("• Escrow Protected Transactions: ${metrics.escrowOrdersCount} active", color = AuraGold, fontSize = 12.sp)
                            Text("• Multi-Device Synced Sessions: 42,914 across Web, Android, iOS", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                        }
                    }
                }

                "Reports Queue" -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        items(reports) { rep ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E))
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(rep.reportedItem, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp)
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(if (rep.status == "Resolved") AuraSuccess else AuraCoral)
                                                .padding(horizontal = 8.dp, vertical = 2.dp)
                                        ) {
                                            Text(rep.status, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(rep.reason, color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("By ${rep.reportedBy} • ${rep.timeAgo}", fontSize = 11.sp, color = Color.White.copy(alpha = 0.5f))
                                        if (rep.status == "Pending") {
                                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                                Button(
                                                    onClick = { onBanUser(rep.id) },
                                                    colors = ButtonDefaults.buttonColors(containerColor = AuraCoralDark),
                                                    shape = RoundedCornerShape(10.dp)
                                                ) {
                                                    Text("Ban User", fontSize = 11.sp)
                                                }
                                                Button(
                                                    onClick = { onResolveReport(rep.id) },
                                                    colors = ButtonDefaults.buttonColors(containerColor = AuraSuccess),
                                                    shape = RoundedCornerShape(10.dp)
                                                ) {
                                                    Text("Resolve", fontSize = 11.sp)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                "Escrow Audit" -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        items(escrowOrders) { order ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E))
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(order.productTitle, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp)
                                        Text("$${String.format("%.2f", order.amount)}", fontWeight = FontWeight.Bold, color = AuraGold, fontSize = 15.sp)
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("Buyer: ${order.buyerName} ➔ Seller: ${order.sellerName}", fontSize = 12.sp, color = Color.White.copy(alpha = 0.8f))
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("Status: ${order.status}", fontSize = 11.sp, color = AuraMint)
                                        if (!order.status.contains("Released")) {
                                            Button(
                                                onClick = { onReleaseEscrow(order.id) },
                                                colors = ButtonDefaults.buttonColors(containerColor = AuraSuccess),
                                                shape = RoundedCornerShape(10.dp)
                                            ) {
                                                Text("Release Escrow", fontSize = 11.sp)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AdminMetricTile(
    title: String,
    value: String,
    status: String,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(86.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, fontSize = 11.sp, color = Color.White.copy(alpha = 0.6f))
            Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text(status, fontSize = 10.sp, color = accentColor, fontWeight = FontWeight.SemiBold)
        }
    }
}
