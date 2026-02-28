# Feature 01: 2048 Game Engine Logic

## Overview
The core mathematical and logical rules of the 2048 game. This must be 100% pure Kotlin in `commonMain`.

## User Stories
- **US1.1:** As a player, I want tiles to move in 4 directions so I can play the game.
- **US1.2:** As a player, I want identical tiles to merge so I can increase my score.
- **US1.3:** As a player, I want to see a 'Game Over' or 'Win' message based on the board state.

## Technical Constraints (TDD)
- **Unit Test Coverage:** > 95% for this module.
- **Grid Representation:** 2D Array or flattened List of `Tile` objects.
- **Merge Logic:** Handle "double merges" correctly (e.g., [2, 2, 2, 2] -> [4, 4, 0, 0]).

## Acceptance Criteria
- [ ] Move Right on `[2, 0, 2, 2]` results in `[0, 0, 2, 4]`.
- [ ] New tile (2 or 4) spawns only in empty slots after a valid move.
- [ ] `isGameOver()` returns true if no moves are possible.
