# Business Rules & Game Logic

## 2048 Core Logic
1. **Grid:** 4x4 matrix.
2. **Movement:** Tiles move in the chosen direction (Up, Down, Left, Right) until stopped by another tile or the edge.
3. **Merging:** When two tiles with the same number touch, they merge into one with the sum of their values.
4. **Spawning:** After every move, a new tile (2 or 4) appears in an empty spot.
5. **Win Condition:** Reaching the 2048 tile. User can opt to continue.
6. **Lose Condition:** No empty spots and no adjacent tiles with the same value.

## Scoring & Persistence
- **Current Score:** Sum of all merges in the current session.
- **Global Leaderboard:** User records are sent to the NestJS backend.
- **Cross-Platform Sync:** Game state (grid matrix + score) must be persisted to the Document DB via API to allow "pick up where you left off" across devices.
