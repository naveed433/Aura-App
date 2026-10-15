package com.example.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class AuraScreen {
    SPLASH,
    WELCOME_CAROUSEL,
    AUTH,
    PHONE_VERIFY,
    PROFILE_SETUP,
    INTEREST_SELECT,
    PERMISSIONS,
    MAIN_HOME,
    MAIN_EXPLORE,
    MAIN_CHATS,
    MAIN_PROFILE,
    REELS_FEED,
    REEL_RECORD,
    REEL_EDITOR,
    REEL_POST,
    SOUND_PAGE,
    CHAT_DETAIL,
    POST_DETAIL,
    MARKETPLACE,
    PRODUCT_DETAIL,
    COMMUNITIES,
    COMMUNITY_DETAIL,
    EVENTS,
    EVENT_DETAIL,
    WALLET,
    CREATOR_DASHBOARD,
    SAFETY_CENTER,
    SETTINGS,
    LINKED_DEVICES,
    STORAGE_DATA,
    ADMIN_PANEL,
    AI_ASSISTANT,
    QR_CODE
}

class AuraRepository {

    private val scope = CoroutineScope(Dispatchers.Main)

    // Navigation and screen management
    private val _currentScreen = MutableStateFlow(AuraScreen.SPLASH)
    val currentScreen: StateFlow<AuraScreen> = _currentScreen.asStateFlow()

    private val screenStack = mutableListOf<AuraScreen>()

    // Current user
    val currentUser = MutableStateFlow(
        User(
            id = "user_me",
            name = "Nova Sterling",
            username = "novasterling",
            avatarInitials = "NS",
            bio = "Visual artist & founder of Aura Labs ✨ Building the future of connected experiences.",
            postsCount = 210,
            followersCount = "55.3K",
            followingCount = "742",
            isVerified = true,
            isOnline = true,
            location = "Bali, Indonesia"
        )
    )

    // Stories
    val stories = MutableStateFlow(
        listOf(
            StoryItem("s_0", "Your Story", "NS", hasUnseen = false, isUserStory = true, storyImageColor = 0xFFFF4D5E, caption = "Sunrise over Ubud 🌅"),
            StoryItem("s_1", "Elena", "ER", hasUnseen = true, storyImageColor = 0xFFFF758C, caption = "Studio sessions all day 🎧"),
            StoryItem("s_2", "Marcus", "MV", hasUnseen = true, storyImageColor = 0xFF7928CA, caption = "Architectural sketches for Villa Sora 🏛️"),
            StoryItem("s_3", "Aria", "AK", hasUnseen = true, storyImageColor = 0xFF0070F3, caption = "Tasting menu in Kyoto 🍣"),
            StoryItem("s_4", "Liam", "LC", hasUnseen = false, storyImageColor = 0xFF10B981, caption = "Track day at Sepang 🏎️"),
            StoryItem("s_5", "Chloe", "CD", hasUnseen = true, storyImageColor = 0xFFF5A623, caption = "Ceramics workshop drop tomorrow ✨")
        )
    )

    // Home Feed posts
    val feedPosts = MutableStateFlow(
        listOf(
            FeedPost(
                id = "post_1",
                authorName = "Elena Rostova",
                authorHandle = "@elenarostova",
                authorAvatar = "ER",
                timeAgo = "2h ago",
                location = "Bali, Indonesia",
                content = "Golden hour reflections at Canggu cliffside. Feeling deeply grateful for the community we're cultivating through Aura. Drop a 🧡 if you're taking time for yourself this evening.",
                mediaBgColor = 0xFFFFD9C9,
                mediaLabel = "Cliffside sunset view with warm horizon glow",
                likesCount = 1243,
                commentsCount = 87,
                sharesCount = 14,
                isLiked = true,
                comments = listOf(
                    CommentItem("c1", "Marcus Vance", "MV", "Incredible light! What camera setup did you use?", "1h ago", 12),
                    CommentItem("c2", "Aria Kuroki", "AK", "Pure magic ✨ Need to visit Bali soon!", "45m ago", 8)
                )
            ),
            FeedPost(
                id = "post_2",
                authorName = "Marcus Vance",
                authorHandle = "@marcusv",
                authorAvatar = "MV",
                timeAgo = "4h ago",
                location = "Tokyo, Japan",
                content = "Completed the final render pass on the minimalist timber pavilion. Combining sustainable bamboo laminates with kinetic solar louvers.",
                mediaBgColor = 0xFFD4F1E8,
                mediaLabel = "Modern architectural render in natural cedar wood",
                likesCount = 842,
                commentsCount = 42,
                sharesCount = 28,
                isLiked = false,
                comments = listOf(
                    CommentItem("c3", "Liam Chen", "LC", "The joinery details are phenomenal.", "2h ago", 5)
                )
            ),
            FeedPost(
                id = "post_3",
                authorName = "Aura Official",
                authorHandle = "@aura",
                authorAvatar = "AU",
                timeAgo = "6h ago",
                location = "Global",
                content = "Aura 2.0 is live! Featuring quantum-resistant end-to-end encrypted messaging, cinematic Reels creation, and unified verified marketplace transactions. Where connections truly glow.",
                mediaBgColor = 0xFFFFE8EA,
                mediaLabel = "Aura Glowing Hologram Visual",
                likesCount = 4920,
                commentsCount = 312,
                sharesCount = 820,
                isLiked = true
            )
        )
    )

    // Reels
    val reels = MutableStateFlow(
        listOf(
            ReelItem(
                id = "reel_1",
                creatorName = "Chloe Dupont",
                creatorHandle = "@chloedupont",
                caption = "Handcrafting ceramic espresso cups with raw terracotta glaze #ceramics #pottery #slowliving #art",
                soundTitle = "Golden Hours (Acoustic)",
                soundArtist = "Joji • Aura Sounds",
                likesCount = 84200,
                commentsCount = 1420,
                savesCount = 9400,
                sharesCount = 3100,
                giftsCount = 450,
                isLiked = true,
                isFollowing = false,
                gradientStart = 0xFF1A1A2E,
                gradientEnd = 0xFFFF4D5E
            ),
            ReelItem(
                id = "reel_2",
                creatorName = "Liam Chen",
                creatorHandle = "@liamchen",
                caption = "Night drive through Shinjuku neon rain in the restored 1993 GT-R 🌧️ #tokyo #jdm #cinematic #nightvibes",
                soundTitle = "Midnight Drive Lo-Fi",
                soundArtist = "Tokyo Beats • Original",
                likesCount = 132400,
                commentsCount = 2890,
                savesCount = 18200,
                sharesCount = 7600,
                giftsCount = 820,
                isLiked = false,
                isFollowing = true,
                gradientStart = 0xFF0F172A,
                gradientEnd = 0xFF6366F1
            ),
            ReelItem(
                id = "reel_3",
                creatorName = "Aria Kuroki",
                creatorHandle = "@ariakuroki",
                caption = "Secret cafe tucked away in Kyoto's Gion district. The matcha crepe is life-changing! 🍵✨ #japan #travel #foodie",
                soundTitle = "Japanese Garden Wind",
                soundArtist = "Zen Melodies",
                likesCount = 47800,
                commentsCount = 814,
                savesCount = 6320,
                sharesCount = 1420,
                giftsCount = 210,
                isLiked = true,
                isFollowing = true,
                gradientStart = 0xFF064E3B,
                gradientEnd = 0xFF10B981
            )
        )
    )

    // Selected items for detail screens
    val selectedPost = MutableStateFlow<FeedPost?>(null)
    val selectedConversation = MutableStateFlow<Conversation?>(null)
    val selectedProduct = MutableStateFlow<ProductItem?>(null)
    val selectedCommunity = MutableStateFlow<CommunityItem?>(null)
    val selectedEvent = MutableStateFlow<EventItem?>(null)
    val selectedSound = MutableStateFlow("Golden Hours (Acoustic)")

    // Call state
    val callState = MutableStateFlow(CallState())

    // Chat conversations (WhatsApp style)
    val conversations = MutableStateFlow(
        listOf(
            Conversation(
                id = "chat_1",
                name = "Elena Rostova",
                handle = "@elenarostova",
                avatar = "ER",
                lastMessage = "Can't wait to see the final deck! Sending voice note soon 🎙️",
                time = "10:42 AM",
                unreadCount = 2,
                isOnline = true,
                isTyping = false,
                isPinned = true,
                disappearingMessagesTimer = "24 hours",
                messages = listOf(
                    ChatMessage("m1", "Hey Nova! Did you check the venue options for the Aura Summit?", false, "10:30 AM", MessageStatus.READ),
                    ChatMessage("m2", "Yes, the bamboo pavilion in Ubud looks breathtaking. I'll reserve the dates today.", true, "10:35 AM", MessageStatus.READ),
                    ChatMessage("m3", "Voice message (0:14)", false, "10:40 AM", MessageStatus.READ, MessageType.VOICE, voiceDuration = "0:14", voiceDurationSeconds = 14),
                    ChatMessage("m4", "Can't wait to see the final deck! Sending voice note soon 🎙️", false, "10:42 AM", MessageStatus.READ)
                )
            ),
            Conversation(
                id = "chat_2",
                name = "Aura Designers Circle",
                handle = "@auradesigners",
                avatar = "AD",
                lastMessage = "Marcus: Shared the new Figma token specifications.",
                time = "Yesterday",
                unreadCount = 0,
                isOnline = false,
                isPinned = true,
                isGroup = true,
                membersCount = 18,
                messages = listOf(
                    ChatMessage("m5", "Welcome to Aura Designers Circle! End-to-end encrypted.", false, "Yesterday"),
                    ChatMessage("m6", "Shared the new Figma token specifications.", false, "Yesterday")
                )
            ),
            Conversation(
                id = "chat_3",
                name = "Marcus Vance",
                handle = "@marcusv",
                avatar = "MV",
                lastMessage = "Sent 2 architectural renders in full resolution",
                time = "Yesterday",
                unreadCount = 0,
                isOnline = true,
                messages = listOf(
                    ChatMessage("m7", "Hey, do you need the CAD files as well?", false, "Yesterday"),
                    ChatMessage("m8", "Just the high-res PNGs for now, thank you!", true, "Yesterday", MessageStatus.READ)
                )
            ),
            Conversation(
                id = "chat_4",
                name = "Chloe Dupont",
                handle = "@chloedupont",
                avatar = "CD",
                lastMessage = "Your custom ceramic set has been packed!",
                time = "Oct 12",
                unreadCount = 0,
                isOnline = false,
                messages = listOf(
                    ChatMessage("m9", "Your custom ceramic set has been packed!", false, "Oct 12")
                )
            )
        )
    )

    // Marketplace Products
    val products = MutableStateFlow(
        listOf(
            ProductItem(
                id = "p1",
                title = "Handcrafted Ceramic Set (6 Pcs)",
                price = 145.00,
                category = "Home",
                location = "Ubud, Bali",
                sellerName = "Chloe Dupont",
                rating = 4.9,
                description = "Custom glazed stoneware made from organic volcanic clay. Dishwasher and microwave safe. Protected with Aura Escrow.",
                cardColor = 0xFFFFE8EA,
                escrowStatus = "Escrow Eligible"
            ),
            ProductItem(
                id = "p2",
                title = "Vintage Leica M6 Rangefinder",
                price = 2850.00,
                category = "Electronics",
                location = "Tokyo, Japan",
                sellerName = "Kenji Sato",
                rating = 5.0,
                description = "Mint condition Leica M6 classic with original leather case and 35mm Summicron f/2 lens. Serviced in 2026.",
                cardColor = 0xFFD4F1E8,
                escrowStatus = "Escrow Eligible"
            ),
            ProductItem(
                id = "p3",
                title = "Architectural Bamboo Stool",
                price = 220.00,
                category = "Furniture",
                location = "Canggu, Bali",
                sellerName = "Marcus Vance Design",
                rating = 4.8,
                description = "Ergonomic curved laminated bamboo bench. Natural beeswax finish.",
                cardColor = 0xFFFFD9C9,
                escrowStatus = "Escrow Eligible"
            ),
            ProductItem(
                id = "p4",
                title = "Raw Silk Hand-Dyed Kimono",
                price = 380.00,
                category = "Fashion",
                location = "Kyoto, Japan",
                sellerName = "Aria Textiles",
                rating = 4.9,
                description = "Natural indigo and madder dye on mulberry silk. Unisex relaxed drape.",
                cardColor = 0xFFE8DEF8,
                escrowStatus = "Escrow Eligible"
            )
        )
    )

    // Escrow Orders
    val escrowOrders = MutableStateFlow(
        listOf(
            EscrowOrder("ord_1", "p1", "Handcrafted Ceramic Set", 145.00, "Nova Sterling", "Chloe Dupont", "In Escrow (Transit)", "Today, 11:00 AM"),
            EscrowOrder("ord_2", "p3", "Architectural Bamboo Stool", 220.00, "Kenji Sato", "Marcus Vance", "Delivered & Released", "Yesterday")
        )
    )

    // Communities
    val communities = MutableStateFlow(
        listOf(
            CommunityItem("c1", "Sustainable Architecture", "Innovating circular materials and biophilic urban design.", "12.4K members", "890 posts", "🏛️", true),
            CommunityItem("c2", "Kinetic Photography", "Analog film enthusiasts, medium format, and street portraiture.", "34.8K members", "4.2K posts", "📷", true),
            CommunityItem("c3", "Ceramic Arts Collective", "Hand-building, wheel throwing, glaze chemistry & kiln building.", "8.9K members", "620 posts", "🏺", false),
            CommunityItem("c4", "Web3 & Zero-Knowledge Devs", "Building private, encrypted decentralized protocols.", "19.1K members", "1.8K posts", "🔐", true)
        )
    )

    // Events
    val events = MutableStateFlow(
        listOf(
            EventItem("e1", "Aura Creator Summit 2026", "24", "OCT", "Bamboo Sanctuary, Ubud", 420, true),
            EventItem("e2", "Tokyo Night Vision Photo Walk", "02", "NOV", "Shibuya Sky & Omotesando", 85, false),
            EventItem("e3", "Craft & Ceramic Pop-up Bazaar", "15", "NOV", "Echo Beach, Canggu", 190, true)
        )
    )

    // Wallet
    val walletBalance = MutableStateFlow(1240.50)
    val transactions = MutableStateFlow(
        listOf(
            WalletTransaction("tx1", "Received from Elena R.", "Summit design contribution", 250.00, true, "Today, 10:45 AM"),
            WalletTransaction("tx2", "Marketplace: Ceramic Set", "Purchase from Chloe Dupont", 145.00, false, "Yesterday, 4:12 PM"),
            WalletTransaction("tx3", "Creator Gift Payout", "Weekly Reels creator bonus", 380.00, true, "Oct 12, 9:00 AM"),
            WalletTransaction("tx4", "Aura Pro Subscription", "Annual cloud & E2EE backup", 39.99, false, "Oct 01, 12:00 AM")
        )
    )

    // Linked Devices for E2EE Sync
    val linkedDevices = MutableStateFlow(
        listOf(
            LinkedDevice("d1", "MacBook Pro 16\" (M3 Max)", "Bali, Indonesia", "Active Now", isCurrent = false),
            LinkedDevice("d2", "Pixel 9 Pro (This Device)", "Bali, Indonesia", "Active Now", isCurrent = true),
            LinkedDevice("d3", "iPad Pro 13\" Liquid Retina", "Tokyo, Japan", "Yesterday at 18:24", isCurrent = false)
        )
    )

    // Admin & Oversight Panel metrics & reports
    val adminMetrics = MutableStateFlow(AdminMetrics())
    val reportItems = MutableStateFlow(
        listOf(
            ReportItem("r1", "Product: Counterfeit Lens", "Misleading branding description", "@kenji_user", "15m ago", "Pending", "High"),
            ReportItem("r2", "Comment on Reel #2", "Hate speech / unsolicited spam", "@troll_detector", "1h ago", "Pending", "Medium"),
            ReportItem("r3", "Account @spammer_bot", "Automated direct message solicitation", "System Auto-Flag", "3h ago", "Resolved", "Critical")
        )
    )

    // Navigation methods
    fun navigateTo(screen: AuraScreen) {
        if (_currentScreen.value != screen) {
            screenStack.add(_currentScreen.value)
            _currentScreen.value = screen
        }
    }

    fun goBack(): Boolean {
        if (screenStack.isNotEmpty()) {
            _currentScreen.value = screenStack.removeAt(screenStack.size - 1)
            return true
        }
        return false
    }

    // Likes & interaction actions
    fun togglePostLike(postId: String) {
        feedPosts.update { list ->
            list.map { post ->
                if (post.id == postId) {
                    val newLiked = !post.isLiked
                    post.copy(
                        isLiked = newLiked,
                        likesCount = if (newLiked) post.likesCount + 1 else post.likesCount - 1
                    )
                } else post
            }
        }
    }

    fun toggleReelLike(reelId: String) {
        reels.update { list ->
            list.map { reel ->
                if (reel.id == reelId) {
                    val newLiked = !reel.isLiked
                    reel.copy(
                        isLiked = newLiked,
                        likesCount = if (newLiked) reel.likesCount + 1 else reel.likesCount - 1
                    )
                } else reel
            }
        }
    }

    fun toggleReelFollow(reelId: String) {
        reels.update { list ->
            list.map { reel ->
                if (reel.id == reelId) {
                    reel.copy(isFollowing = !reel.isFollowing)
                } else reel
            }
        }
    }

    // Voice note playback controller
    fun toggleVoiceNotePlayback(messageId: String) {
        conversations.update { convos ->
            convos.map { convo ->
                convo.copy(
                    messages = convo.messages.map { msg ->
                        if (msg.id == messageId) {
                            val newPlaying = !msg.isPlayingVoice
                            msg.copy(isPlayingVoice = newPlaying, voiceProgress = if (newPlaying) 0.45f else msg.voiceProgress)
                        } else {
                            msg.copy(isPlayingVoice = false)
                        }
                    }
                )
            }
        }
        selectedConversation.update { curr ->
            curr?.let { c ->
                c.copy(
                    messages = c.messages.map { msg ->
                        if (msg.id == messageId) {
                            val newPlaying = !msg.isPlayingVoice
                            msg.copy(isPlayingVoice = newPlaying, voiceProgress = if (newPlaying) 0.45f else msg.voiceProgress)
                        } else {
                            msg.copy(isPlayingVoice = false)
                        }
                    }
                )
            }
        }
    }

    fun toggleVoicePlaybackSpeed(messageId: String) {
        val speeds = listOf("1x", "1.5x", "2x")
        conversations.update { convos ->
            convos.map { convo ->
                convo.copy(
                    messages = convo.messages.map { msg ->
                        if (msg.id == messageId) {
                            val nextIdx = (speeds.indexOf(msg.playbackSpeed) + 1) % speeds.size
                            msg.copy(playbackSpeed = speeds[nextIdx])
                        } else msg
                    }
                )
            }
        }
        selectedConversation.update { curr ->
            curr?.let { c ->
                c.copy(
                    messages = c.messages.map { msg ->
                        if (msg.id == messageId) {
                            val nextIdx = (speeds.indexOf(msg.playbackSpeed) + 1) % speeds.size
                            msg.copy(playbackSpeed = speeds[nextIdx])
                        } else msg
                    }
                )
            }
        }
    }

    fun sendChatMessage(conversationId: String, text: String, type: MessageType = MessageType.TEXT, replyTo: String? = null) {
        val newMsg = ChatMessage(
            id = "msg_${System.currentTimeMillis()}",
            text = text,
            isOutgoing = true,
            timestamp = "Just now",
            status = MessageStatus.SENT,
            type = type,
            isEncrypted = true,
            replyToText = replyTo
        )
        conversations.update { convos ->
            convos.map { convo ->
                if (convo.id == conversationId) {
                    convo.copy(
                        lastMessage = text,
                        time = "Just now",
                        messages = convo.messages + newMsg
                    )
                } else convo
            }
        }
        // Update selected conversation if open
        if (selectedConversation.value?.id == conversationId) {
            selectedConversation.update { curr ->
                curr?.copy(
                    lastMessage = text,
                    time = "Just now",
                    messages = curr.messages + newMsg
                )
            }
        }

        // Simulate delivery tick transition and automated reply typing
        scope.launch {
            delay(1200)
            updateMessageStatus(conversationId, newMsg.id, MessageStatus.DELIVERED)
            delay(1500)
            updateMessageStatus(conversationId, newMsg.id, MessageStatus.READ)
            // Trigger simulated typing
            setTyping(conversationId, true)
            delay(2400)
            setTyping(conversationId, false)
            val replyText = "Received securely! Looking forward to reviewing."
            val replyMsg = ChatMessage(
                id = "msg_rep_${System.currentTimeMillis()}",
                text = replyText,
                isOutgoing = false,
                timestamp = "Just now",
                status = MessageStatus.READ,
                type = MessageType.TEXT,
                isEncrypted = true
            )
            conversations.update { convos ->
                convos.map { convo ->
                    if (convo.id == conversationId) {
                        convo.copy(
                            lastMessage = replyText,
                            time = "Just now",
                            messages = convo.messages + replyMsg
                        )
                    } else convo
                }
            }
            if (selectedConversation.value?.id == conversationId) {
                selectedConversation.update { curr ->
                    curr?.copy(
                        lastMessage = replyText,
                        time = "Just now",
                        messages = curr.messages + replyMsg
                    )
                }
            }
        }
    }

    private fun updateMessageStatus(conversationId: String, messageId: String, status: MessageStatus) {
        conversations.update { convos ->
            convos.map { c ->
                if (c.id == conversationId) {
                    c.copy(messages = c.messages.map { if (it.id == messageId) it.copy(status = status) else it })
                } else c
            }
        }
        if (selectedConversation.value?.id == conversationId) {
            selectedConversation.update { curr ->
                curr?.copy(messages = curr.messages.map { if (it.id == messageId) it.copy(status = status) else it })
            }
        }
    }

    private fun setTyping(conversationId: String, isTyping: Boolean) {
        conversations.update { convos ->
            convos.map { if (it.id == conversationId) it.copy(isTyping = isTyping) else it }
        }
        if (selectedConversation.value?.id == conversationId) {
            selectedConversation.update { it?.copy(isTyping = isTyping) }
        }
    }

    fun sendVoiceNote(conversationId: String, durationSeconds: Int = 8) {
        val durationFormatted = "0:${if (durationSeconds < 10) "0$durationSeconds" else "$durationSeconds"}"
        val newVoice = ChatMessage(
            id = "msg_${System.currentTimeMillis()}",
            text = "Voice message ($durationFormatted)",
            isOutgoing = true,
            timestamp = "Just now",
            status = MessageStatus.DELIVERED,
            type = MessageType.VOICE,
            voiceDuration = durationFormatted,
            voiceDurationSeconds = durationSeconds,
            isEncrypted = true
        )
        conversations.update { convos ->
            convos.map { convo ->
                if (convo.id == conversationId) {
                    convo.copy(
                        lastMessage = "🎤 Voice note ($durationFormatted)",
                        time = "Just now",
                        messages = convo.messages + newVoice
                    )
                } else convo
            }
        }
        if (selectedConversation.value?.id == conversationId) {
            selectedConversation.update { curr ->
                curr?.copy(
                    lastMessage = "🎤 Voice note ($durationFormatted)",
                    time = "Just now",
                    messages = curr.messages + newVoice
                )
            }
        }
    }

    fun purchaseWithEscrow(productId: String): Boolean {
        val product = products.value.find { it.id == productId } ?: return false
        if (walletBalance.value < product.price) return false

        walletBalance.update { it - product.price }
        val newTx = WalletTransaction(
            id = "tx_${System.currentTimeMillis()}",
            title = "Aura Escrow: ${product.title}",
            description = "Held in zero-knowledge buyer escrow",
            amount = product.price,
            isIncoming = false,
            timestamp = "Just now"
        )
        transactions.update { listOf(newTx) + it }

        val newOrder = EscrowOrder(
            id = "ord_${System.currentTimeMillis()}",
            productId = product.id,
            productTitle = product.title,
            amount = product.price,
            buyerName = currentUser.value.name,
            sellerName = product.sellerName,
            status = "Escrow Funded (Verifying)",
            timestamp = "Just now"
        )
        escrowOrders.update { listOf(newOrder) + it }
        adminMetrics.update { it.copy(escrowOrdersCount = it.escrowOrdersCount + 1) }
        return true
    }

    fun releaseEscrow(orderId: String) {
        escrowOrders.update { list ->
            list.map { if (it.id == orderId) it.copy(status = "Escrow Released to Seller") else it }
        }
    }

    fun toggleCall(contactName: String, isVideo: Boolean = false) {
        callState.update { curr ->
            if (curr.isActive) {
                curr.copy(isActive = false)
            } else {
                CallState(
                    isActive = true,
                    isVideo = isVideo,
                    isIncoming = false,
                    contactName = contactName,
                    contactAvatar = contactName.split(" ").mapNotNull { it.firstOrNull()?.toString() }.joinToString(""),
                    statusText = "Connected • 00:04",
                    durationSeconds = 4
                )
            }
        }
    }

    fun endCall() {
        callState.update { it.copy(isActive = false) }
    }

    fun resolveReport(reportId: String) {
        reportItems.update { list ->
            list.map { if (it.id == reportId) it.copy(status = "Resolved") else it }
        }
        adminMetrics.update { it.copy(pendingReportsCount = (it.pendingReportsCount - 1).coerceAtLeast(0)) }
    }

    fun banUserFromReport(reportId: String) {
        reportItems.update { list ->
            list.map { if (it.id == reportId) it.copy(status = "User Banned & Content Removed") else it }
        }
        adminMetrics.update { it.copy(pendingReportsCount = (it.pendingReportsCount - 1).coerceAtLeast(0)) }
    }

    fun revokeDevice(deviceId: String) {
        linkedDevices.update { list -> list.filterNot { it.id == deviceId } }
    }
}
