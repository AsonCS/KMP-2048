### 📄 0004-offline-first-state-sync.md

**Status:** Accepted

**Context:** A 2048 game is often played in "dead zones" (subways, planes). Users expect their progress to be saved even if they lose connection mid-game.

**Decision:** Adopt an **Offline-First** approach. The game state is saved locally (using *SQLDelight* or *KStore*) on every move. A background worker (using *WorkManager* on Android and *Background Tasks* on iOS) will attempt to sync the local state with the NestJS API whenever a connection is available.

**Consequences:** * **Pro:** Seamless user experience regardless of connectivity.

* **Pro:** Reduces API load by debouncing sync calls.
* **Con:** Increased complexity in handling "conflict resolution" if the user plays on two devices simultaneously while offline.
