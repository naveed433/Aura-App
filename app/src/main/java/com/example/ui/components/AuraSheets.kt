package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assistant
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Poll
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AuraBlue
import com.example.ui.theme.AuraCoral
import com.example.ui.theme.AuraCoralSoft
import com.example.ui.theme.AuraGold
import com.example.ui.theme.AuraLavender
import com.example.ui.theme.AuraMint
import com.example.ui.theme.AuraPeach
import com.example.ui.theme.AuraSurface
import com.example.ui.theme.AuraText
import com.example.ui.theme.AuraText2

data class CreateOption(
    val title: String,
    val icon: ImageVector,
    val color: Color,
    val actionTag: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateSheetModal(
    onDismiss: () -> Unit,
    onOptionSelected: (String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val options = listOf(
        CreateOption("Story", Icons.Default.PhotoCamera, AuraCoralSoft, "create_story"),
        CreateOption("Reel", Icons.Default.Movie, AuraGold, "create_reel"),
        CreateOption("Post", Icons.Default.PostAdd, AuraPeach, "create_post"),
        CreateOption("Live", Icons.Default.Videocam, AuraCoral, "create_live"),
        CreateOption("Poll", Icons.Default.Poll, AuraBlue, "create_poll"),
        CreateOption("Event", Icons.Default.Event, AuraLavender, "create_event"),
        CreateOption("Group", Icons.Default.Group, AuraMint, "create_group"),
        CreateOption("AI Studio", Icons.Default.Assistant, AuraCoral, "create_ai"),
        CreateOption("Product", Icons.Default.ShoppingBag, AuraGold, "create_product")
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = AuraSurface,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .testTag("create_sheet_content")
        ) {
            Text(
                text = "Create",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = AuraText,
                modifier = Modifier.padding(bottom = 18.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
            ) {
                items(options) { item ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                onOptionSelected(item.actionTag)
                                onDismiss()
                            }
                            .padding(8.dp)
                            .testTag(item.actionTag)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .shadow(4.dp, RoundedCornerShape(18.dp))
                                .background(item.color.copy(alpha = 0.25f), RoundedCornerShape(18.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = if (item.color == AuraCoralSoft) AuraCoral else item.color,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = item.title,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = AuraText,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// WhatsApp-like Attachment Radial & Grid menu
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttachmentSheetModal(
    onDismiss: () -> Unit,
    onItemSelected: (String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val attachments = listOf(
        CreateOption("Document", Icons.Default.Description, AuraBlue, "attach_doc"),
        CreateOption("Camera", Icons.Default.PhotoCamera, AuraCoral, "attach_camera"),
        CreateOption("Gallery", Icons.Default.Image, AuraLavender, "attach_gallery"),
        CreateOption("Audio", Icons.Default.MusicNote, AuraGold, "attach_audio"),
        CreateOption("Location", Icons.Default.LocationOn, AuraMint, "attach_location"),
        CreateOption("Contact", Icons.Default.Person, AuraPeach, "attach_contact"),
        CreateOption("Poll", Icons.Default.Poll, AuraBlue, "attach_poll"),
        CreateOption("Gift", Icons.Default.CardGiftcard, AuraCoral, "attach_gift"),
        CreateOption("Pay", Icons.Default.Payments, AuraMint, "attach_pay")
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = AuraSurface,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Text(
                text = "Share Content",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = AuraText,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            ) {
                items(attachments) { item ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                onItemSelected(item.title)
                                onDismiss()
                            }
                            .padding(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(item.color.copy(alpha = 0.2f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = item.color,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.title,
                            fontSize = 12.sp,
                            color = AuraText,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// Quick emoji reaction bar
@Composable
fun MessageReactionBar(
    onSelectEmoji: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val quickEmojis = listOf("❤️", "😂", "😮", "😢", "👍", "🙏", "🔥")

    Surface(
        shape = RoundedCornerShape(24.dp),
        color = AuraSurface,
        shadowElevation = 8.dp,
        modifier = modifier.padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            quickEmojis.forEach { emoji ->
                Text(
                    text = emoji,
                    fontSize = 22.sp,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { onSelectEmoji(emoji) }
                        .padding(4.dp)
                )
            }
        }
    }
}
