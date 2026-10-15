package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.example.data.AuraRepository
import com.example.data.AuraScreen
import com.example.ui.components.AuraBottomBar
import com.example.ui.components.CallOverlayScreen
import com.example.ui.components.CreateSheetModal
import com.example.ui.screens.AdminPanelScreen
import com.example.ui.screens.AiAssistantScreen
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.ChatDetailScreen
import com.example.ui.screens.ChatsListScreen
import com.example.ui.screens.CommunitiesScreen
import com.example.ui.screens.CreatorDashboardScreen
import com.example.ui.screens.EventsScreen
import com.example.ui.screens.ExploreScreen
import com.example.ui.screens.HomeFeedScreen
import com.example.ui.screens.InterestSelectionScreen
import com.example.ui.screens.LinkedDevicesScreen
import com.example.ui.screens.MarketplaceScreen
import com.example.ui.screens.PermissionsScreen
import com.example.ui.screens.PhoneVerifyScreen
import com.example.ui.screens.PostDetailScreen
import com.example.ui.screens.ProductDetailScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.ProfileSetupScreen
import com.example.ui.screens.QrCodeScreen
import com.example.ui.screens.ReelEditorScreen
import com.example.ui.screens.ReelPostScreen
import com.example.ui.screens.ReelRecordScreen
import com.example.ui.screens.ReelsFeedScreen
import com.example.ui.screens.SafetyCenterScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.SoundPageScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.WalletScreen
import com.example.ui.screens.WelcomeCarouselScreen
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    private val repository = AuraRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                AuraAppRoot(repository = repository)
            }
        }
    }
}

@Composable
fun AuraAppRoot(repository: AuraRepository) {
    val currentScreen by repository.currentScreen.collectAsState()
    val currentUser by repository.currentUser.collectAsState()
    val stories by repository.stories.collectAsState()
    val feedPosts by repository.feedPosts.collectAsState()
    val reels by repository.reels.collectAsState()
    val conversations by repository.conversations.collectAsState()
    val products by repository.products.collectAsState()
    val escrowOrders by repository.escrowOrders.collectAsState()
    val communities by repository.communities.collectAsState()
    val events by repository.events.collectAsState()
    val walletBalance by repository.walletBalance.collectAsState()
    val transactions by repository.transactions.collectAsState()
    val linkedDevices by repository.linkedDevices.collectAsState()
    val adminMetrics by repository.adminMetrics.collectAsState()
    val reportItems by repository.reportItems.collectAsState()
    val selectedPost by repository.selectedPost.collectAsState()
    val selectedConvo by repository.selectedConversation.collectAsState()
    val selectedProduct by repository.selectedProduct.collectAsState()
    val selectedSound by repository.selectedSound.collectAsState()
    val callState by repository.callState.collectAsState()

    var showCreateModal by remember { mutableStateOf(false) }

    val isMainTab = currentScreen in listOf(
        AuraScreen.MAIN_HOME,
        AuraScreen.MAIN_EXPLORE,
        AuraScreen.MAIN_CHATS,
        AuraScreen.MAIN_PROFILE
    )

    // Handle Android system back gesture cleanly
    BackHandler(enabled = !isMainTab && currentScreen != AuraScreen.SPLASH) {
        repository.goBack()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isMainTab) {
                val totalUnread = conversations.sumOf { it.unreadCount }
                AuraBottomBar(
                    currentScreen = currentScreen,
                    unreadChatsCount = totalUnread,
                    onTabSelected = { tab -> repository.navigateTo(tab) },
                    onFabClick = { showCreateModal = true }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = if (isMainTab) innerPadding.calculateBottomPadding() else Dp(0f))
        ) {
            when (currentScreen) {
                AuraScreen.SPLASH -> {
                    SplashScreen(
                        onTimeout = { repository.navigateTo(AuraScreen.WELCOME_CAROUSEL) }
                    )
                }

                AuraScreen.WELCOME_CAROUSEL -> {
                    WelcomeCarouselScreen(
                        onGetStarted = { repository.navigateTo(AuraScreen.AUTH) },
                        onLoginClick = { repository.navigateTo(AuraScreen.AUTH) },
                        onSkip = { repository.navigateTo(AuraScreen.MAIN_HOME) }
                    )
                }

                AuraScreen.AUTH -> {
                    AuthScreen(
                        onAuthSuccess = { repository.navigateTo(AuraScreen.PHONE_VERIFY) },
                        onBack = { repository.goBack() }
                    )
                }

                AuraScreen.PHONE_VERIFY -> {
                    PhoneVerifyScreen(
                        onVerified = { repository.navigateTo(AuraScreen.PROFILE_SETUP) },
                        onBack = { repository.goBack() }
                    )
                }

                AuraScreen.PROFILE_SETUP -> {
                    ProfileSetupScreen(
                        onContinue = { repository.navigateTo(AuraScreen.INTEREST_SELECT) }
                    )
                }

                AuraScreen.INTEREST_SELECT -> {
                    InterestSelectionScreen(
                        onContinue = { repository.navigateTo(AuraScreen.PERMISSIONS) }
                    )
                }

                AuraScreen.PERMISSIONS -> {
                    PermissionsScreen(
                        onAllowAll = { repository.navigateTo(AuraScreen.MAIN_HOME) },
                        onSkip = { repository.navigateTo(AuraScreen.MAIN_HOME) }
                    )
                }

                AuraScreen.MAIN_HOME -> {
                    HomeFeedScreen(
                        currentUser = currentUser,
                        stories = stories,
                        feedPosts = feedPosts,
                        onPostClick = { post ->
                            repository.selectedPost.value = post
                            repository.navigateTo(AuraScreen.POST_DETAIL)
                        },
                        onLikePost = { postId -> repository.togglePostLike(postId) },
                        onStoryClick = { },
                        onSearchClick = { repository.navigateTo(AuraScreen.MAIN_EXPLORE) },
                        onNotificationsClick = { }
                    )
                }

                AuraScreen.POST_DETAIL -> {
                    selectedPost?.let { post ->
                        PostDetailScreen(
                            post = post,
                            onBack = { repository.goBack() },
                            onLikePost = { repository.togglePostLike(post.id) }
                        )
                    } ?: run {
                        repository.goBack()
                    }
                }

                AuraScreen.MAIN_EXPLORE -> {
                    ExploreScreen(
                        onNavigateFeature = { route ->
                            when (route) {
                                "reels" -> repository.navigateTo(AuraScreen.REELS_FEED)
                                "marketplace" -> repository.navigateTo(AuraScreen.MARKETPLACE)
                                "communities" -> repository.navigateTo(AuraScreen.COMMUNITIES)
                                "events" -> repository.navigateTo(AuraScreen.EVENTS)
                                "ai" -> repository.navigateTo(AuraScreen.AI_ASSISTANT)
                                else -> repository.navigateTo(AuraScreen.REELS_FEED)
                            }
                        }
                    )
                }

                AuraScreen.REELS_FEED -> {
                    ReelsFeedScreen(
                        reels = reels,
                        onLikeReel = { id -> repository.toggleReelLike(id) },
                        onFollowToggle = { id -> repository.toggleReelFollow(id) },
                        onRecordClick = { repository.navigateTo(AuraScreen.REEL_RECORD) },
                        onSoundClick = { sound ->
                            repository.selectedSound.value = sound
                            repository.navigateTo(AuraScreen.SOUND_PAGE)
                        },
                        onBack = { repository.goBack() }
                    )
                }

                AuraScreen.SOUND_PAGE -> {
                    SoundPageScreen(
                        soundName = selectedSound,
                        onUseSound = {
                            repository.navigateTo(AuraScreen.REEL_RECORD)
                        },
                        onBack = { repository.goBack() }
                    )
                }

                AuraScreen.REEL_RECORD -> {
                    ReelRecordScreen(
                        soundName = selectedSound,
                        onNext = { repository.navigateTo(AuraScreen.REEL_EDITOR) },
                        onClose = { repository.goBack() }
                    )
                }

                AuraScreen.REEL_EDITOR -> {
                    ReelEditorScreen(
                        onNext = { repository.navigateTo(AuraScreen.REEL_POST) },
                        onBack = { repository.goBack() }
                    )
                }

                AuraScreen.REEL_POST -> {
                    ReelPostScreen(
                        onPublished = {
                            repository.navigateTo(AuraScreen.MAIN_HOME)
                        },
                        onBack = { repository.goBack() }
                    )
                }

                AuraScreen.MAIN_CHATS -> {
                    ChatsListScreen(
                        conversations = conversations,
                        onConversationClick = { convo ->
                            repository.selectedConversation.value = convo
                            repository.navigateTo(AuraScreen.CHAT_DETAIL)
                        },
                        onNewChatClick = {
                            repository.selectedConversation.value = conversations.first()
                            repository.navigateTo(AuraScreen.CHAT_DETAIL)
                        }
                    )
                }

                AuraScreen.CHAT_DETAIL -> {
                    selectedConvo?.let { convo ->
                        ChatDetailScreen(
                            conversation = convo,
                            onBack = { repository.goBack() },
                            onSendMessage = { text, replyTo -> repository.sendChatMessage(convo.id, text, replyTo = replyTo) },
                            onSendVoiceNote = { secs -> repository.sendVoiceNote(convo.id, secs) },
                            onToggleVoicePlayback = { msgId -> repository.toggleVoiceNotePlayback(msgId) },
                            onToggleVoiceSpeed = { msgId -> repository.toggleVoicePlaybackSpeed(msgId) },
                            onAudioCall = { repository.toggleCall(convo.name, isVideo = false) },
                            onVideoCall = { repository.toggleCall(convo.name, isVideo = true) }
                        )
                    } ?: run {
                        repository.goBack()
                    }
                }

                AuraScreen.MARKETPLACE -> {
                    MarketplaceScreen(
                        products = products,
                        onProductClick = { prod ->
                            repository.selectedProduct.value = prod
                            repository.navigateTo(AuraScreen.PRODUCT_DETAIL)
                        },
                        onBack = { repository.goBack() }
                    )
                }

                AuraScreen.PRODUCT_DETAIL -> {
                    selectedProduct?.let { prod ->
                        ProductDetailScreen(
                            product = prod,
                            walletBalance = walletBalance,
                            onBack = { repository.goBack() },
                            onChatSeller = {
                                val match = conversations.find { it.name.contains(prod.sellerName.split(" ").first()) }
                                repository.selectedConversation.value = match ?: conversations.first()
                                repository.navigateTo(AuraScreen.CHAT_DETAIL)
                            },
                            onConfirmPurchase = { id -> repository.purchaseWithEscrow(id) }
                        )
                    } ?: run {
                        repository.goBack()
                    }
                }

                AuraScreen.COMMUNITIES -> {
                    CommunitiesScreen(
                        communities = communities,
                        onCommunityClick = { },
                        onBack = { repository.goBack() }
                    )
                }

                AuraScreen.COMMUNITY_DETAIL -> {
                    CommunitiesScreen(
                        communities = communities,
                        onCommunityClick = { },
                        onBack = { repository.goBack() }
                    )
                }

                AuraScreen.EVENTS -> {
                    EventsScreen(
                        events = events,
                        onEventClick = { },
                        onBack = { repository.goBack() }
                    )
                }

                AuraScreen.EVENT_DETAIL -> {
                    EventsScreen(
                        events = events,
                        onEventClick = { },
                        onBack = { repository.goBack() }
                    )
                }

                AuraScreen.MAIN_PROFILE -> {
                    ProfileScreen(
                        user = currentUser,
                        onSettingsClick = { repository.navigateTo(AuraScreen.SETTINGS) },
                        onQrClick = { repository.navigateTo(AuraScreen.QR_CODE) },
                        onWalletClick = { repository.navigateTo(AuraScreen.WALLET) },
                        onCreatorDashboardClick = { repository.navigateTo(AuraScreen.CREATOR_DASHBOARD) }
                    )
                }

                AuraScreen.WALLET -> {
                    WalletScreen(
                        balance = walletBalance,
                        transactions = transactions,
                        onBack = { repository.goBack() }
                    )
                }

                AuraScreen.CREATOR_DASHBOARD -> {
                    CreatorDashboardScreen(
                        onBack = { repository.goBack() }
                    )
                }

                AuraScreen.QR_CODE -> {
                    QrCodeScreen(
                        user = currentUser,
                        onBack = { repository.goBack() }
                    )
                }

                AuraScreen.SETTINGS -> {
                    SettingsScreen(
                        onBack = { repository.goBack() },
                        onSafetyCenterClick = { repository.navigateTo(AuraScreen.SAFETY_CENTER) },
                        onLinkedDevicesClick = { repository.navigateTo(AuraScreen.LINKED_DEVICES) },
                        onAdminPanelClick = { repository.navigateTo(AuraScreen.ADMIN_PANEL) }
                    )
                }

                AuraScreen.SAFETY_CENTER -> {
                    SafetyCenterScreen(
                        onBack = { repository.goBack() }
                    )
                }

                AuraScreen.LINKED_DEVICES -> {
                    LinkedDevicesScreen(
                        devices = linkedDevices,
                        onRevokeDevice = { id -> repository.revokeDevice(id) },
                        onBack = { repository.goBack() }
                    )
                }

                AuraScreen.STORAGE_DATA -> {
                    SettingsScreen(
                        onBack = { repository.goBack() },
                        onSafetyCenterClick = { repository.navigateTo(AuraScreen.SAFETY_CENTER) },
                        onLinkedDevicesClick = { repository.navigateTo(AuraScreen.LINKED_DEVICES) },
                        onAdminPanelClick = { repository.navigateTo(AuraScreen.ADMIN_PANEL) }
                    )
                }

                AuraScreen.ADMIN_PANEL -> {
                    AdminPanelScreen(
                        metrics = adminMetrics,
                        reports = reportItems,
                        escrowOrders = escrowOrders,
                        onResolveReport = { id -> repository.resolveReport(id) },
                        onBanUser = { id -> repository.banUserFromReport(id) },
                        onReleaseEscrow = { id -> repository.releaseEscrow(id) },
                        onBack = { repository.goBack() }
                    )
                }

                AuraScreen.AI_ASSISTANT -> {
                    AiAssistantScreen(
                        onBack = { repository.goBack() }
                    )
                }
            }

            // Create Sheet Modal from Center FAB (+)
            if (showCreateModal) {
                CreateSheetModal(
                    onDismiss = { showCreateModal = false },
                    onOptionSelected = { action ->
                        when (action) {
                            "create_reel" -> repository.navigateTo(AuraScreen.REEL_RECORD)
                            "create_post" -> repository.navigateTo(AuraScreen.REEL_POST)
                            "create_ai" -> repository.navigateTo(AuraScreen.AI_ASSISTANT)
                            "create_product" -> repository.navigateTo(AuraScreen.MARKETPLACE)
                            "create_group" -> repository.navigateTo(AuraScreen.MAIN_CHATS)
                            "create_event" -> repository.navigateTo(AuraScreen.EVENTS)
                            else -> repository.navigateTo(AuraScreen.REEL_RECORD)
                        }
                    }
                )
            }

            // Audio & Video Call Overlay if call active
            AnimatedVisibility(
                visible = callState.isActive,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                CallOverlayScreen(
                    callState = callState,
                    onEndCall = { repository.endCall() }
                )
            }
        }
    }
}
