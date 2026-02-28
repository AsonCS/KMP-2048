# User Stories & Use Cases (Spec-Driven)

## US1: Tile Movement
**As a player, I want to swipe the grid so that tiles move and merge.**
- **Scenario:** Merging two '2' tiles.
- **Acceptance Criteria:** - [ ] Swipe Right on a row [2, 0, 2, 0] results in [0, 0, 0, 4].
  - [ ] Score increases by 4.
  - [ ] A new tile spawns in a random empty cell.

## US2: Persistence
**As a player, I want my game to save automatically so I can switch devices.**
- **Scenario:** Closing app and opening on a different platform.
- **Acceptance Criteria:**
  - [ ] Every move triggers a background sync (debounced).
  - [ ] On login, the app fetches the latest `GameState` from the backend.

## US3: Previews
- **Requirement:** Every Compose component (Tile, Grid, ScoreBoard) must have a `@Preview` and a corresponding screenshot/instrumented test.
