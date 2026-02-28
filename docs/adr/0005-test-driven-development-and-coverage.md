### 📄 0005-test-driven-development-and-coverage.md

**Status:** Accepted

**Context:** The user requires high reliability (90%+ coverage) and specific UI testing for Compose components.

**Decision:** Enforce **TDD** for all shared logic. Every new Use Case (e.g., `MoveTilesUseCase`) must have a unit test suite before the implementation is written. For the UI, every Compose component must have a `@Preview` for visual regression and an instrumented test for interaction verification.

**Consequences:** * **Pro:** High confidence in code refactoring.

* **Con:** Initial development speed might be slower due to the overhead of writing tests first.
