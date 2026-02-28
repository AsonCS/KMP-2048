# Feature 04: Global Leaderboard

## Overview
The social layer where players compare their top scores.

## User Stories
- **US4.1:** As a player, I want to see the Top 100 global players.
- **US4.2:** As a player, I want to share my high score with friends.

## Technical Constraints
- **Fetch Strategy:** Pagination (limit 50 per page).
- **Data Model:** `LeaderboardEntry(rank: Int, name: String, score: Long)`.

## Acceptance Criteria
- [ ] List displays correctly on both 120Hz (Android) and ProMotion (iOS) displays.
- [ ] Refresh mechanism (Pull-to-refresh) implemented in Compose.
