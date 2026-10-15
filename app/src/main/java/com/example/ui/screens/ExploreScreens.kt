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
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CommunityItem
import com.example.data.EventItem
import com.example.ui.theme.AuraBg
import com.example.ui.theme.AuraBlue
import com.example.ui.theme.AuraCoral
import com.example.ui.theme.AuraCoralSoft
import com.example.ui.theme.AuraGold
import com.example.ui.theme.AuraLavender
import com.example.ui.theme.AuraMint
import com.example.ui.theme.AuraMintDark
import com.example.ui.theme.AuraSurface
import com.example.ui.theme.AuraText
import com.example.ui.theme.AuraText2
import com.example.ui.theme.AuraText3

data class ExploreHubCategory(
    val title: String,
    val icon: ImageVector,
    val color: Color,
    val routeKey: String
)

// SCREEN 21 — EXPLORE
@Composable
fun ExploreScreen(
    onNavigateFeature: (String) -> Unit
) {
    val categories = listOf(
        ExploreHubCategory("Reels", Icons.Default.Movie, AuraCoral, "reels"),
        ExploreHubCategory("Marketplace", Icons.Default.ShoppingBag, AuraGold, "marketplace"),
        ExploreHubCategory("Communities", Icons.Default.Groups, AuraLavender, "communities"),
        ExploreHubCategory("Events", Icons.Default.Event, AuraMintDark, "events"),
        ExploreHubCategory("Live Stream", Icons.Default.LiveTv, AuraCoral, "live"),
        ExploreHubCategory("AI Assistant", Icons.Default.Widgets, AuraBlue, "ai")
    )

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .testTag("explore_screen"),
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
                    Text("Explore", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = AuraText)
                    Text(".", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = AuraCoral)
                }
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(AuraSurface),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Search, contentDescription = "Search", tint = AuraText)
                }
            }

            // 2-Column Hub Category Cards
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth().height(260.dp)
            ) {
                items(categories) { cat ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(82.dp)
                            .shadow(2.dp, RoundedCornerShape(20.dp))
                            .clickable { onNavigateFeature(cat.routeKey) },
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = AuraSurface)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(cat.color.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(cat.icon, contentDescription = cat.title, tint = cat.color, modifier = Modifier.size(22.dp))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(cat.title, fontSize = 13.5.sp, fontWeight = FontWeight.SemiBold, color = AuraText)
                        }
                    }
                }
            }

            // Trending Section Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Trending on Aura", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = AuraText)
                Text("See all", fontSize = 13.sp, color = AuraCoral, fontWeight = FontWeight.SemiBold)
            }

            // 3-Column Trending Visual Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(1f)
            ) {
                val demoColors = listOf(0xFFFFD9C9, 0xFFD4F1E8, 0xFFFFE8EA, 0xFFE8DEF8, 0xFFFFF5F0, 0xFFE0E7FF)
                items(demoColors) { colorLong ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(colorLong))
                            .clickable { onNavigateFeature("reels") },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("✨", fontSize = 24.sp)
                    }
                }
            }
        }
    }
}

// SCREEN 39 — COMMUNITIES
@Composable
fun CommunitiesScreen(
    communities: List<CommunityItem>,
    onCommunityClick: (CommunityItem) -> Unit,
    onBack: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .testTag("communities_screen"),
        color = AuraBg
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = AuraCoral)
                }
                Text("Communities", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = AuraText)
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(communities) { comm ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(2.dp, RoundedCornerShape(18.dp))
                            .clickable { onCommunityClick(comm) },
                        shape = RoundedCornerShape(18.dp),
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
                                    .size(54.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(AuraLavender),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(comm.iconEmoji, fontSize = 28.sp)
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(comm.name, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = AuraText)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(comm.description, fontSize = 12.sp, color = AuraText2, maxLines = 2)
                                Spacer(modifier = Modifier.height(6.dp))
                                Text("👥 ${comm.membersCount} • 📝 ${comm.postsCount}", fontSize = 11.sp, color = AuraText3)
                            }
                        }
                    }
                }
            }
        }
    }
}

// SCREEN 41 — EVENTS
@Composable
fun EventsScreen(
    events: List<EventItem>,
    onEventClick: (EventItem) -> Unit,
    onBack: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .testTag("events_screen"),
        color = AuraBg
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = AuraCoral)
                }
                Text("Events", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = AuraText)
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(events) { ev ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(2.dp, RoundedCornerShape(20.dp))
                            .clickable { onEventClick(ev) },
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = AuraSurface)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Date badge
                            Box(
                                modifier = Modifier
                                    .size(58.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(AuraCoralSoft),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(ev.month, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = AuraCoral)
                                    Text(ev.day, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = AuraText)
                                }
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(ev.title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = AuraText)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(ev.location, fontSize = 12.sp, color = AuraText2)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("🎟️ ${ev.attendeesCount} Attending", fontSize = 11.sp, color = AuraMintDark, fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                }
            }
        }
    }
}
