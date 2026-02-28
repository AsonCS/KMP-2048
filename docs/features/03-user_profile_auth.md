# Feature 03: User Profile & Authentication

## Overview
Identifies the player to track high scores and enable cross-device synchronization.

## User Stories
- **US3.1:** As a player, I want to create a nickname so I appear on the leaderboard.
- **US3.2:** As a player, I want to link my account (Google/Apple) to keep my progress safe.

## Technical Constraints
- **Backend:** NestJS Auth module using JWT.
- **Frontend:** Shared `AuthRepository` interface; platform-specific social login implementation.

## Acceptance Criteria
- [ ] Unique nickname validation.
- [ ] JWT token stored securely (EncryptedSharedPreferences / Keychain).
