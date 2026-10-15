package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AuraBg
import com.example.ui.theme.AuraBlue
import com.example.ui.theme.AuraCoral
import com.example.ui.theme.AuraCoralSoft
import com.example.ui.theme.AuraGold
import com.example.ui.theme.AuraMint
import com.example.ui.theme.AuraMintDark
import com.example.ui.theme.AuraSurface
import com.example.ui.theme.AuraSurface2
import com.example.ui.theme.AuraText
import com.example.ui.theme.AuraText2
import com.example.ui.theme.AuraText3
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class AiMessage(
    val id: String,
    val text: String,
    val isUser: Boolean,
    val thinkingTrace: String? = null
)

// SCREEN 26 — AI ASSISTANT (with Gemini Thinking Mode simulation)
@Composable
fun AiAssistantScreen(
    onBack: () -> Unit
) {
    val messages = remember {
        mutableStateListOf(
            AiMessage(
                id = "ai_1",
                text = "Hello Nova! I'm Aura AI, powered by gemini-4-argon with Deep Thinking mode enabled. How can I assist you with your creative work, marketplace transactions, or community growth today?",
                isUser = false
            )
        )
    }

    var inputText by remember { mutableStateOf("") }
    var isThinking by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val quickTools = listOf(
        "🧠 Deep Thinking" to "High Thinking Level Active",
        "✨ Summarize" to "Summarize recent chats and community posts",
        "🌍 Translate" to "Translate messages to Japanese & Indonesian",
        "🎨 Reel Script" to "Draft viral 30s luxury lifestyle script"
    )

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .testTag("ai_assistant_screen"),
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
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(listOf(AuraCoral, AuraGold))
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Psychology, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text("Aura Intelligence Studio", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = AuraText)
                    Text("gemini-4-argon • High Thinking Mode", fontSize = 11.sp, color = AuraMintDark, fontWeight = FontWeight.Medium)
                }
            }

            // Quick Tool Chips
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(quickTools) { (label, prompt) ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(AuraSurface)
                            .clickable {
                                messages.add(AiMessage("u_${System.currentTimeMillis()}", prompt, true))
                                isThinking = true
                                coroutineScope.launch {
                                    delay(1200)
                                    isThinking = false
                                    messages.add(
                                        AiMessage(
                                            "ai_${System.currentTimeMillis()}",
                                            "Here is your optimized result generated using High Thinking Mode reasoning:\n\n1. Concept hooks viewers in the first 1.2 seconds.\n2. Audio sync matches beat drops for maximum retention.\n3. Community CTA encourages direct private messaging.",
                                            false,
                                            thinkingTrace = "Thought process: Evaluated optimal retention curves for luxury lifestyle Reels, analyzed audience engagement metrics in Bali and Tokyo demographics."
                                        )
                                    )
                                }
                            }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(label, fontSize = 12.sp, color = AuraText, fontWeight = FontWeight.Medium)
                    }
                }
            }

            // Messages List
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(messages) { msg ->
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = if (msg.isUser) Alignment.End else Alignment.Start
                    ) {
                        if (msg.thinkingTrace != null) {
                            Box(
                                modifier = Modifier
                                    .padding(bottom = 4.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(AuraMint.copy(alpha = 0.4f))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text("🧠 Thought process: ${msg.thinkingTrace}", fontSize = 11.sp, color = AuraMintDark)
                            }
                        }

                        Card(
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (msg.isUser) AuraCoral else AuraSurface
                            ),
                            modifier = Modifier.fillMaxWidth(0.85f)
                        ) {
                            Text(
                                text = msg.text,
                                fontSize = 14.sp,
                                color = if (msg.isUser) Color.White else AuraText,
                                lineHeight = 20.sp,
                                modifier = Modifier.padding(14.dp)
                            )
                        }
                    }
                }

                if (isThinking) {
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = AuraCoral, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Aura AI is thinking deeply with gemini-4-argon...", fontSize = 12.sp, color = AuraCoral, fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }

            // Composer
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AuraSurface)
                    .navigationBarsPadding()
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = { Text("Ask Aura AI anything...", fontSize = 13.sp) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(22.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AuraCoral,
                        unfocusedContainerColor = AuraSurface2
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(AuraCoral)
                        .clickable {
                            if (inputText.isNotBlank()) {
                                val query = inputText
                                inputText = ""
                                messages.add(AiMessage("u_${System.currentTimeMillis()}", query, true))
                                isThinking = true
                                coroutineScope.launch {
                                    delay(1000)
                                    isThinking = false
                                    messages.add(
                                        AiMessage(
                                            "ai_${System.currentTimeMillis()}",
                                            "I've analyzed your query with high thinking depth. Aura is designed to keep your communications zero-knowledge private while providing maximum viral reach on public Reels and guaranteed escrow protection in the Marketplace.",
                                            false
                                        )
                                    )
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = Color.White, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}
