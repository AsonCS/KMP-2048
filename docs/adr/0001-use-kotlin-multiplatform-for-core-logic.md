### 📄 0001-use-kotlin-multiplatform-for-core-logic.md

**Status:** Accepted

**Context:** The game needs to run on both iOS and Android with identical win/loss logic, grid movement, and scoring. Writing this twice increases the risk of "logic drift" where one platform behaves differently than the other.

**Decision:** We will use **Kotlin Multiplatform (KMP)** to house all business logic, game engine mechanics, and data models in a `:shared` module.

**Consequences:** * **Pro:** 100% logic parity between platforms.

* **Pro:** Unit tests only need to be written once in the shared module.
* **Con:** Requires developers to be familiar with the KMP memory model and Gradle configuration.
