# Feature 02: State Persistence & Sync

## Overview
Handles saving the current board state locally for "resume" capability and syncing it to the NestJS backend for cross-platform play.

## User Stories
- **US2.1:** As a player, I want to close the app and resume exactly where I left off.
- **US2.2:** As a player, I want my game to sync to the cloud so I can switch from Android to iOS.

## Technical Constraints
- **Local Storage:** Use a KMP-compatible key-value store or SQLDelight.
- **Remote Sync:** Use `Ktor` to `POST /game/sync` with a debounced approach (e.g., sync every 5 moves).
- **Conflict Resolution:** Last-write-wins based on a timestamp.

## Acceptance Criteria
- [ ] App loads the last saved grid on startup.
- [ ] Offline moves are cached and synced once the internet is restored.
