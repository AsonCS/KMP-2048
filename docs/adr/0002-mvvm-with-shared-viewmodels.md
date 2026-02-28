### 📄 0002-mvvm-with-shared-viewmodels.md

**Status:** Accepted

**Context:** We are using **Compose Multiplatform** for the UI. To keep the UI "dumb" and testable, we need a clear pattern for state management.

**Decision:** Implement the **MVVM (Model-View-ViewModel)** pattern. The ViewModels will reside in the `shared` module using `CommonViewModel` (or a KMP-compatible library like *MOKO Resources* or *KMP-NativeCoroutines*).

**Consequences:** * **Pro:** UI components only observe state; they don't calculate it.

* **Pro:** ViewModels can be unit-tested using standard Kotlin tests without an emulator/simulator.
* **Con:** Navigation handling between shared code and platform-specific entry points can be complex.
