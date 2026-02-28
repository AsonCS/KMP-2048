# Feature 05: Compose Multiplatform UI System

## Overview
Definition of the Atomic Design components for the game.

## Components List
1. **TileView:** Dynamic background color based on value (2=Grey, 2048=Gold).
2. **GameGrid:** The 4x4 container with smooth move animations.
3. **ScoreCard:** Animated counter for current and high scores.
4. **ControlPanel:** Reset and Undo buttons.

## Mandatory Tests (Instrumented)
- **Previews:** Every component must have a `@Preview`.
- **Animations:** Verify that tile translations happen within 200ms.
- **Accessibility:** Ensure content descriptions for screen readers ("Tile 2048 at Row 1 Column 2").

## Acceptance Criteria
- [ ] Zero platform-specific code in the UI components (100% Shared).
- [ ] Responsive layout for Foldables and Tablets.
