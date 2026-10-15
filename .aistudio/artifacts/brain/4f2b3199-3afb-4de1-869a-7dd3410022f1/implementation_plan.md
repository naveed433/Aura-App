# AURA — Production-Grade Social Super-App Upgrade

Transform AURA into a production-grade social super-app by upgrading WhatsApp-style communications with interactive voice note playback and live typing feedback, implementing TikTok-grade Reels gesture physics and creative editing tools, expanding the marketplace with escrow and buyer verification, and connecting comprehensive administrator analytics and moderation.

## User Review & Critical Decisions

> [!IMPORTANT]
> The upgrade emphasizes realistic human interactions, haptic/gesture feedback, offline persistence with real-time sync simulation, and an integrated Web Admin control panel.

- **Communication Engine**: Deep WhatsApp-style interaction including audio voice note playback with animated seekable scrubber waveforms, simulated typing indicators, double blue ticks with deliver/read timestamps, and disappearing message toggles.
- **Reels & Video Experience**: TikTok-style vertical gesture physics, audio waveform trimming, multi-track audio sync, real-time speed ramping (0.5x, 1x, 2x, 3x), video filter color presets, and double-tap particle bursts.
- **Marketplace & Commerce**: Escrow protection state machine, seller verification badges, verified buyer reviews, in-app checkout simulation with wallet deduction, and direct encrypted chat with seller.
- **Admin Management Panel**: Real-time moderation queue for user reports, active session revocation, marketplace transaction audit logs, server health telemetry, and live user ban/flag toggles.

---

## 1. Overview & Core Concept

- **What It Does**: A complete, polished social super-app combining private encrypted communications, viral Reels video creation, community channels, peer-to-peer commerce, and enterprise administration in one seamless experience.
- **Target Audience**: Creators, digital artisans, community leaders, and privacy-conscious users seeking a unified luxury digital ecosystem.
- **Key Value**: Delivers the tactile intimacy of WhatsApp messaging and the viral reach of TikTok Reels with luxury aesthetics, zero generic "AI slop", and complete offline resilience.

---

## 2. User Experience & Visual Design

### Key User Flows
1. **Interactive Communications Flow**:
   - Tap conversation → View verified E2EE security banner.
   - Long-press microphone → Animate sound wave recording with slide-to-lock or slide-to-cancel gestures.
   - Tap incoming voice note → Real-time playback with scrubbable waveform and speed toggles (1x, 1.5x, 2x).
   - Real-time simulation of incoming typing indicators and dynamic read receipts.
2. **Reels Studio & Creator Flow**:
   - Swipe vertically with spring resistance and snap-to-viewport physics.
   - Double-tap anywhere to emit a bouncing heart burst with rotation offset.
   - Open Record screen → Select clip length (15s/30s/60s), beauty smoothing level, and countdown timer.
   - Open Editor → Scrub filmstrip thumbnails, apply cinematic LUT filters (Cinematic Warm, Neo-Tokyo, Sunset Gold, Noir B&W), and trim audio tracks.
3. **Marketplace & Checkout Flow**:
   - Browse catalog with category filtering and keyword search.
   - Open Product Detail → Review seller trust rating and escrow guarantee.
   - Click "Buy Now" → Review order summary with instant wallet balance deduction and escrow release trigger.
4. **Admin Oversight Hub**:
   - Access admin panel from Settings.
   - Review live connected device sessions, inspect pending content reports, resolve tickets, and audit marketplace escrow transactions.

### Visual Identity & Theme
- **Primary Accent**: Aura Coral (`#FF4D5E`) with Coral Dark (`#E63946`) for pressed states.
- **Background Atmosphere**: Warm Cream (`#FDF8F5`) for daytime screens; Deep Obsidian Navy (`#0F0F1A`) for Reels, Camera, and Admin mode.
- **Card Surfaces**: Pure White (`#FFFFFF`) with subtle 20dp rounded corners and layered elevation.
- **Typography**: Poppins Display Bold paired with Inter for clean, legible body text.

---

## 3. Key Product Decisions & Trade-Offs

- **Decision 1: Reactive State Layer with Offline Simulation**
  - *Chosen Approach*: StateFlow-based repository with real-time event simulation, persistent disk synchronization models, and optimistic UI updates.
  - *Why*: Delivers instantaneous user feedback, zero network lag in the emulator, and offline-first capabilities.
  - *Alternatives Considered*: Pure static mock data was rejected to ensure every button and toggle produces real, persistent state changes.

- **Decision 2: Comprehensive Multi-Surface Navigation**
  - *Chosen Approach*: Unified Single-Activity Compose architecture with state-driven backstack and explicit back handling.
  - *Why*: Supports modal bottom sheets, full-screen video snap feeds, and deep child screens without fragment overhead.

---

## 4. Technical Architecture & Data Strategy

```
┌────────────────────────────────────────────────────────┐
│                      AURA UI LAYER                     │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  │
│  │  Home Feed   │  │ Reels Studio │  │ Encrypted    │  │
│  │  & Stories   │  │ & Video Edit │  │ Chats & Calls│  │
│  └──────┬───────┘  └──────┬───────┘  └──────┬───────┘  │
│         │                 │                 │          │
│  ┌──────┴───────┐  ┌──────┴───────┐  ┌──────┴───────┐  │
│  │  Marketplace │  │ Creator Hub  │  │ Admin Hub &  │  │
│  │  & Escrow    │  │ & Wallet     │  │ Device Sync  │  │
│  └──────┬───────┘  └──────┬───────┘  └──────┬───────┘  │
└─────────┼─────────────────┼─────────────────┼──────────┘
          ▼                 ▼                 ▼
┌────────────────────────────────────────────────────────┐
│                 AURA REPOSITORY & STATE                │
│  • Reactive StateFlows (Conversations, Reels, Orders)  │
│  • Voice Note Playback Controller & Waveform Engine    │
│  • E2EE Multi-Device Key Store & Session Revocation    │
│  • Escrow Order Management & Admin Moderation Queue    │
└────────────────────────────────────────────────────────┘
```

### Component & State Mapping
- `AuraRepository`: Manages central state for all 76 application capabilities, audio playback progress, device key verification, and moderation queues.
- `ChatDetailScreen`: Handles real-time message sending, audio recording/playback timers, typing indicators, and emoji reactions.
- `ReelsFeedScreen` & `ReelEditorScreen`: Manages video scrub progress, filter shader simulations, audio volume mixing, and creator stats.
- `MarketplaceScreens`: Manages product discovery, escrow transactions, and direct wallet balance updates.
- `AdminPanelScreen`: Provides administrative telemetry, session kill-switches, and report adjudication.
