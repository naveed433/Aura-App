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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AuraBg
import com.example.ui.theme.AuraCoral
import com.example.ui.theme.AuraCoralDark
import com.example.ui.theme.AuraCoralSoft
import com.example.ui.theme.AuraGold
import com.example.ui.theme.AuraMint
import com.example.ui.theme.AuraMintDark
import com.example.ui.theme.AuraSurface
import com.example.ui.theme.AuraText
import com.example.ui.theme.AuraText2
import com.example.ui.theme.AuraText3
import kotlinx.coroutines.delay

// SCREEN 1 — SPLASH
@Composable
fun SplashScreen(
    onTimeout: () -> Unit
) {
    val transition = rememberInfiniteTransition(label = "splash")
    val pulseScale by transition.animateFloat(
        initialValue = 0.98f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    LaunchedEffect(Unit) {
        delay(1800)
        onTimeout()
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onTimeout() }
            .testTag("splash_screen"),
        color = AuraBg
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Center 96px rounded square gradient coral->gold with chat bubble
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .scale(pulseScale)
                        .shadow(20.dp, RoundedCornerShape(30.dp), spotColor = AuraCoral)
                        .clip(RoundedCornerShape(30.dp))
                        .background(
                            Brush.linearGradient(listOf(AuraCoral, AuraGold))
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "💬",
                        fontSize = 44.sp
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "AURA",
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Bold,
                        color = AuraText,
                        letterSpacing = (-0.02).sp
                    )
                    Text(
                        text = ".",
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Bold,
                        color = AuraCoral
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "WHERE CONNECTIONS GLOW",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AuraText2,
                    letterSpacing = 2.sp
                )
            }
        }
    }
}

// SCREEN 2 — WELCOME CAROUSEL
@Composable
fun WelcomeCarouselScreen(
    onGetStarted: () -> Unit,
    onLoginClick: () -> Unit,
    onSkip: () -> Unit
) {
    var currentSlide by remember { mutableIntStateOf(0) }

    val slides = listOf(
        Triple("Stay connected anywhere in the world.", "Real-time sync, quantum encryption, and seamless voice & video calls.", "🌍"),
        Triple("Quantum-Grade Privacy & Security.", "End-to-end encrypted chats, view-once media, and zero-knowledge data backup.", "🔒"),
        Triple("Create, share, and go viral on Reels.", "Cinematic 4K creator tools, instant music sync, and direct marketplace monetization.", "🎬")
    )

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag("welcome_carousel_screen"),
        color = AuraBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Skip button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Skip",
                    color = AuraText2,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .clickable { onSkip() }
                        .padding(8.dp)
                )
            }

            // Slide Content
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        .background(AuraCoralSoft),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = slides[currentSlide].third,
                        fontSize = 64.sp
                    )
                }

                Spacer(modifier = Modifier.height(36.dp))

                Text(
                    text = slides[currentSlide].first,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = AuraText,
                    textAlign = TextAlign.Center,
                    lineHeight = 32.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = slides[currentSlide].second,
                    fontSize = 14.sp,
                    color = AuraText2,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Dots indicator
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    slides.indices.forEach { index ->
                        Box(
                            modifier = Modifier
                                .size(if (currentSlide == index) 24.dp else 8.dp, 8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (currentSlide == index) AuraCoral else AuraText3.copy(alpha = 0.4f))
                                .clickable { currentSlide = index }
                        )
                    }
                }
            }

            // Bottom Buttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onGetStarted,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("welcome_get_started"),
                    colors = ButtonDefaults.buttonColors(containerColor = AuraCoral),
                    shape = RoundedCornerShape(26.dp)
                ) {
                    Text("Get Started", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                }

                Button(
                    onClick = onLoginClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("welcome_login"),
                    colors = ButtonDefaults.buttonColors(containerColor = AuraMint),
                    shape = RoundedCornerShape(26.dp)
                ) {
                    Text("I already have an account", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = AuraMintDark)
                }
            }
        }
    }
}

// SCREEN 3 — LOGIN / SIGNUP
@Composable
fun AuthScreen(
    onAuthSuccess: () -> Unit,
    onBack: () -> Unit
) {
    var isSignUp by remember { mutableStateOf(true) }
    var emailOrPhone by remember { mutableStateOf("nova@auralabs.io") }
    var password by remember { mutableStateOf("SuperSecret99!") }
    var showPassword by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag("auth_screen"),
        color = AuraBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = AuraCoral)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Let's Connect Together",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = AuraText
            )
            Text(
                text = "Where your digital presence glows.",
                fontSize = 14.sp,
                color = AuraText2
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Toggle Tab: Sign Up vs Login
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "Sign up",
                    fontSize = 18.sp,
                    fontWeight = if (isSignUp) FontWeight.Bold else FontWeight.Medium,
                    color = if (isSignUp) AuraCoral else AuraText3,
                    modifier = Modifier
                        .clickable { isSignUp = true }
                        .padding(end = 24.dp, bottom = 4.dp)
                )
                Text(
                    text = "Login",
                    fontSize = 18.sp,
                    fontWeight = if (!isSignUp) FontWeight.Bold else FontWeight.Medium,
                    color = if (!isSignUp) AuraCoral else AuraText3,
                    modifier = Modifier
                        .clickable { isSignUp = false }
                        .padding(bottom = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Input Fields
            OutlinedTextField(
                value = emailOrPhone,
                onValueChange = { emailOrPhone = it },
                label = { Text("Email or phone") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_email_phone"),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AuraCoral,
                    unfocusedContainerColor = AuraSurface,
                    focusedContainerColor = AuraSurface
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            if (showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = "Toggle password visibility",
                            tint = AuraText3
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_password"),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AuraCoral,
                    unfocusedContainerColor = AuraSurface,
                    focusedContainerColor = AuraSurface
                )
            )

            // Password strength bar indicator
            if (isSignUp) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(modifier = Modifier.weight(1f).height(4.dp).background(AuraCoral, RoundedCornerShape(2.dp)))
                    Box(modifier = Modifier.weight(1f).height(4.dp).background(AuraGold, RoundedCornerShape(2.dp)))
                    Box(modifier = Modifier.weight(1f).height(4.dp).background(AuraMintDark, RoundedCornerShape(2.dp)))
                }
                Text("Password strength: Excellent 🛡️", fontSize = 11.sp, color = AuraMintDark, modifier = Modifier.padding(top = 4.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onAuthSuccess,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("auth_submit_button"),
                colors = ButtonDefaults.buttonColors(containerColor = AuraCoral),
                shape = RoundedCornerShape(26.dp)
            ) {
                Text(
                    text = if (isSignUp) "Create Account" else "Login",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Social Login Row
            Text(
                text = "— or continue with —",
                fontSize = 13.sp,
                color = AuraText3,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                listOf("Google" to "G", "Apple" to "", "Passkey" to "🔑").forEach { (name, symbol) ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .size(56.dp)
                            .shadow(4.dp, CircleShape)
                            .clip(CircleShape)
                            .background(AuraSurface)
                            .clickable { onAuthSuccess() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = symbol, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = AuraText)
                    }
                }
            }
        }
    }
}

// SCREEN 4 — PHONE VERIFICATION
@Composable
fun PhoneVerifyScreen(
    onVerified: () -> Unit,
    onBack: () -> Unit
) {
    var digits by remember { mutableStateOf(listOf("8", "4", "2", "9", "1", "0")) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag("phone_verify_screen"),
        color = AuraBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = AuraCoral)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Enter your code", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = AuraText)
            Text("We sent a 6-digit verification code to +1 (555) 019-2834", fontSize = 13.sp, color = AuraText2)

            Spacer(modifier = Modifier.height(32.dp))

            // 6 PIN boxes
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                digits.forEach { digit ->
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(2.dp, RoundedCornerShape(14.dp))
                            .clip(RoundedCornerShape(14.dp))
                            .background(AuraSurface)
                            .border(1.5.dp, AuraCoral, RoundedCornerShape(14.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = digit, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = AuraText)
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Resend code in 0:24",
                fontSize = 13.sp,
                color = AuraText2,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(36.dp))

            Button(
                onClick = onVerified,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("verify_code_button"),
                colors = ButtonDefaults.buttonColors(containerColor = AuraCoral),
                shape = RoundedCornerShape(26.dp)
            ) {
                Text("Verify Code", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
            }
        }
    }
}

// SCREEN 5 — PROFILE SETUP
@Composable
fun ProfileSetupScreen(
    onContinue: () -> Unit
) {
    var fullName by remember { mutableStateOf("Nova Sterling") }
    var username by remember { mutableStateOf("novasterling") }
    var bio by remember { mutableStateOf("Visual artist & founder of Aura Labs ✨") }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag("profile_setup_screen"),
        color = AuraBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text("Set up your profile", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = AuraText)
            Text("Let the Aura community know who you are.", fontSize = 13.sp, color = AuraText2)

            Spacer(modifier = Modifier.height(32.dp))

            // Circular avatar uploader with camera icon
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally)
                    .border(2.dp, AuraCoral, CircleShape)
                    .clip(CircleShape)
                    .background(AuraCoralSoft)
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.CameraAlt, contentDescription = "Upload Avatar", tint = AuraCoral)
                    Text("Photo", fontSize = 11.sp, color = AuraCoral, fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AuraCoral,
                    unfocusedContainerColor = AuraSurface,
                    focusedContainerColor = AuraSurface
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("@username") },
                trailingIcon = {
                    Icon(Icons.Default.CheckCircle, contentDescription = "Available", tint = AuraMintDark)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AuraCoral,
                    unfocusedContainerColor = AuraSurface,
                    focusedContainerColor = AuraSurface
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                value = bio,
                onValueChange = { bio = it },
                label = { Text("Bio") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AuraCoral,
                    unfocusedContainerColor = AuraSurface,
                    focusedContainerColor = AuraSurface
                )
            )

            Spacer(modifier = Modifier.height(36.dp))

            Button(
                onClick = onContinue,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("profile_continue_button"),
                colors = ButtonDefaults.buttonColors(containerColor = AuraCoral),
                shape = RoundedCornerShape(26.dp)
            ) {
                Text("Continue", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
            }
        }
    }
}

// SCREEN 6 — INTEREST SELECTION
@Composable
fun InterestSelectionScreen(
    onContinue: () -> Unit
) {
    val topics = listOf(
        "Tech" to "💻", "Design" to "🎨", "Music" to "🎵",
        "Travel" to "✈️", "Food" to "🍜", "Art" to "🖼️",
        "Fashion" to "👗", "Gaming" to "🎮", "Business" to "📈",
        "Fitness" to "⚡", "Photo" to "📷", "Movies" to "🍿",
        "Books" to "📚", "Science" to "🔬", "Nature" to "🌿"
    )

    val selected = remember { mutableStateListOf("Tech", "Design", "Music", "Photo", "Travel") }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag("interest_selection_screen"),
        color = AuraBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text("What are you into?", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = AuraText)
            Text("Pick at least 5 topics to personalize your feed.", fontSize = 13.sp, color = AuraText2)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "${selected.size}/15 selected",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = AuraCoral
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(topics) { (topic, emoji) ->
                    val isPicked = selected.contains(topic)
                    Box(
                        modifier = Modifier
                            .height(84.dp)
                            .shadow(if (isPicked) 4.dp else 1.dp, RoundedCornerShape(18.dp))
                            .clip(RoundedCornerShape(18.dp))
                            .background(if (isPicked) AuraCoralSoft else AuraSurface)
                            .border(if (isPicked) 2.dp else 1.dp, if (isPicked) AuraCoral else AuraSurface, RoundedCornerShape(18.dp))
                            .clickable {
                                if (isPicked) selected.remove(topic) else selected.add(topic)
                            }
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = emoji, fontSize = 28.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = topic,
                                fontSize = 12.sp,
                                fontWeight = if (isPicked) FontWeight.Bold else FontWeight.Medium,
                                color = if (isPicked) AuraCoralDark else AuraText
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onContinue,
                enabled = selected.size >= 3,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("interest_continue_button"),
                colors = ButtonDefaults.buttonColors(containerColor = AuraCoral),
                shape = RoundedCornerShape(26.dp)
            ) {
                Text("Continue (${selected.size} picked)", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
            }
        }
    }
}

// SCREEN 8 — PERMISSIONS
@Composable
fun PermissionsScreen(
    onAllowAll: () -> Unit,
    onSkip: () -> Unit
) {
    var camera by remember { mutableStateOf(true) }
    var mic by remember { mutableStateOf(true) }
    var notifs by remember { mutableStateOf(true) }
    var contacts by remember { mutableStateOf(true) }
    var location by remember { mutableStateOf(true) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag("permissions_screen"),
        color = AuraBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Enable permissions", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = AuraText)
                Text("AURA operates best with real-time communications and multimedia enabled.", fontSize = 13.sp, color = AuraText2)

                Spacer(modifier = Modifier.height(24.dp))

                PermissionRow("Camera", "Take photos, reels, and video calls", camera) { camera = it }
                PermissionRow("Microphone", "Voice notes, calls, and reels audio", mic) { mic = it }
                PermissionRow("Notifications", "Instant encrypted message notifications", notifs) { notifs = it }
                PermissionRow("Contacts", "Connect with verified friends on Aura", contacts) { contacts = it }
                PermissionRow("Location", "Share nearby events and local marketplace", location) { location = it }
            }

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = onAllowAll,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("permissions_allow_all"),
                    colors = ButtonDefaults.buttonColors(containerColor = AuraCoral),
                    shape = RoundedCornerShape(26.dp)
                ) {
                    Text("Allow All & Enter Aura", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                }

                TextButton(
                    onClick = onSkip,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Skip for now", color = AuraText2)
                }
            }
        }
    }
}

@Composable
private fun PermissionRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        colors = CardDefaults.cardColors(containerColor = AuraSurface),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.SemiBold, color = AuraText, fontSize = 15.sp)
                Text(subtitle, color = AuraText2, fontSize = 12.sp)
            }
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = AuraCoral)
            )
        }
    }
}
