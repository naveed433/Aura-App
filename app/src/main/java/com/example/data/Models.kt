package com.example.data

data class User(
    val id: String,
    val name: String,
    val username: String,
    val avatarInitials: String,
    val bio: String = "",
    val postsCount: Int = 210,
    val followersCount: String = "55.3K",
    val followingCount: String = "742",
    val isVerified: Boolean = false,
    val isOnline: Boolean = true,
    val location: String = "Bali, Indonesia"
)

data class StoryItem(
    val id: String,
    val userName: String,
    val userAvatar: String,
    val hasUnseen: Boolean = true,
    val isUserStory: Boolean = false,
    val storyImageColor: Long = 0xFFFF4D5E,
    val caption: String = ""
)

data class FeedPost(
    val id: String,
    val authorName: String,
    val authorHandle: String,
    val authorAvatar: String,
    val timeAgo: String,
    val location: String = "",
    val content: String,
    val mediaBgColor: Long = 0xFFFFD9C9,
    val mediaLabel: String = "",
    val likesCount: Int,
    val commentsCount: Int,
    val sharesCount: Int,
    val isLiked: Boolean = false,
    val isSaved: Boolean = false,
    val comments: List<CommentItem> = emptyList()
)

data class CommentItem(
    val id: String,
    val author: String,
    val avatar: String,
    val text: String,
    val timeAgo: String,
    val likes: Int = 0,
    val isLiked: Boolean = false,
    val replies: List<CommentItem> = emptyList()
)

data class ReelItem(
    val id: String,
    val creatorName: String,
    val creatorHandle: String,
    val caption: String,
    val soundTitle: String,
    val soundArtist: String,
    val likesCount: Int,
    val commentsCount: Int,
    val savesCount: Int,
    val sharesCount: Int,
    val giftsCount: Int,
    val isLiked: Boolean = false,
    val isSaved: Boolean = false,
    val isFollowing: Boolean = false,
    val gradientStart: Long,
    val gradientEnd: Long,
    val videoAspectTag: String = "4K HDR 60fps",
    val activeFilterName: String = "Normal",
    val playbackSpeed: Float = 1.0f
)

enum class MessageStatus {
    SENDING, SENT, DELIVERED, READ
}

enum class MessageType {
    TEXT, VOICE, IMAGE, LOCATION, DOCUMENT, MONEY
}

data class ChatMessage(
    val id: String,
    val text: String,
    val isOutgoing: Boolean,
    val timestamp: String,
    val status: MessageStatus = MessageStatus.READ,
    val type: MessageType = MessageType.TEXT,
    val voiceDuration: String = "0:14",
    val voiceDurationSeconds: Int = 14,
    val isPlayingVoice: Boolean = false,
    val voiceProgress: Float = 0f,
    val playbackSpeed: String = "1x",
    val mediaLabel: String = "",
    val isEncrypted: Boolean = true,
    val reactionEmoji: String? = null,
    val replyToText: String? = null
)

data class Conversation(
    val id: String,
    val name: String,
    val handle: String,
    val avatar: String,
    val lastMessage: String,
    val time: String,
    val unreadCount: Int = 0,
    val isOnline: Boolean = false,
    val isTyping: Boolean = false,
    val isPinned: Boolean = false,
    val isMuted: Boolean = false,
    val isArchived: Boolean = false,
    val isGroup: Boolean = false,
    val membersCount: Int = 1,
    val disappearingMessagesTimer: String? = null,
    val messages: List<ChatMessage> = emptyList()
)

data class ProductItem(
    val id: String,
    val title: String,
    val price: Double,
    val category: String,
    val location: String,
    val sellerName: String,
    val rating: Double = 4.9,
    val reviewsCount: Int = 142,
    val description: String = "",
    val condition: String = "Brand New",
    val cardColor: Long = 0xFFFFE8EA,
    val isSaved: Boolean = false,
    val escrowStatus: String = "Available"
)

data class EscrowOrder(
    val id: String,
    val productId: String,
    val productTitle: String,
    val amount: Double,
    val buyerName: String,
    val sellerName: String,
    val status: String = "Escrow Funded",
    val timestamp: String
)

data class CommunityItem(
    val id: String,
    val name: String,
    val description: String,
    val membersCount: String,
    val postsCount: String,
    val iconEmoji: String,
    val isJoined: Boolean = false
)

data class EventItem(
    val id: String,
    val title: String,
    val day: String,
    val month: String,
    val location: String,
    val attendeesCount: Int,
    val isGoing: Boolean = false
)

data class WalletTransaction(
    val id: String,
    val title: String,
    val description: String,
    val amount: Double,
    val isIncoming: Boolean,
    val timestamp: String
)

data class LinkedDevice(
    val id: String,
    val name: String,
    val location: String,
    val lastActive: String,
    val isCurrent: Boolean = false
)

data class ReportItem(
    val id: String,
    val reportedItem: String,
    val reason: String,
    val reportedBy: String,
    val timeAgo: String,
    val status: String = "Pending",
    val severity: String = "High"
)

data class AdminMetrics(
    val activeUsersCount: Int = 14829,
    val serverHealthPercent: Int = 99,
    val pendingReportsCount: Int = 6,
    val totalTransactionsToday: Double = 24920.00,
    val encryptionSyncNodes: Int = 18,
    val escrowOrdersCount: Int = 12
)

data class CallState(
    val isActive: Boolean = false,
    val isVideo: Boolean = false,
    val isIncoming: Boolean = false,
    val contactName: String = "Elena Rostova",
    val contactAvatar: String = "ER",
    val statusText: String = "Calling...",
    val durationSeconds: Int = 0,
    val isMuted: Boolean = false,
    val isSpeaker: Boolean = false,
    val isCameraOff: Boolean = false
)
