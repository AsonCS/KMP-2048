# 🧩 KMP 2048: Cross-Platform Spec-Driven Puzzle

A high-performance, **2048** clone built with **Kotlin Multiplatform (KMP)** and **Compose Multiplatform**. This project demonstrates a strict **Spec-Driven Development (SDD)** approach, featuring a shared game engine, cloud synchronization via **NestJS**, and an offline-first architecture.

---

## 📖 Documentation Map (Spec-Driven)

This repository is built "Spec-First." Before writing code, refer to these documents to understand the requirements and constraints:

### 🏛️ Foundation

* **[Architecture & Design](./docs/architecture.md)**: Clean Architecture, DDD, and Modularization strategy.
* **[Technology Stack](./docs/technology.md)**: Detailed versions of Kotlin, Ktor, NestJS, and Database choices.
* **[Architectural Decisions (ADRs)](./docs/adr/)**: The "Why" behind our technical choices.

### 🎮 Game Specs

* **[Business Rules](./docs/business_rules.md)**: Scoring logic and 2048 grid mechanics.
* **[Feature Specifications](./docs/features/)**: Breakdown of individual modules (Game Engine, Sync, Auth, etc.).
* **[API Contracts](./docs/api_spec.md)**: Communication protocols between KMP and NestJS.

### 🧪 Quality Assurance

* **[User Stories & TDD](./docs/user_stories.md)**: Acceptance criteria to drive our test-first development.

---

## 🏗️ Project Structure

```text
.
├── androidApp/               # Android-specific entry point & resources
├── iosApp/                   # Xcode Project & Swift entry point
├── shared/                   # 🚀 80% of Codebase (The "Brain")
│   ├── src/commonMain/       # Domain (DDD), Data (Ktor), & UI (Compose)
│   ├── src/androidMain/      # Android-specific UI Previews
│   └── src/iosMain/          # iOS-specific interop
├── backend-nestjs/           # Node.js API (Scoreboard & Sync)
├── docs/                     # Specifications & ADRs (Read these first!)
└── .github/workflows/        # Automated TDD & Coverage enforcement

```

---

## 🚀 Getting Started

### Prerequisites

* **Android Studio Ladybug+** or **IntelliJ IDEA**.
* **Xcode 15+** (for iOS builds).
* **Node.js v20+** (for Backend).
* **Kotlin Multiplatform Wizard** setup.

### Development Flow

1. **Read the Specs:** Navigate to `docs/features/` to understand the current task.
2. **TDD First:** Write a failing Unit Test in `shared/src/commonTest/`.
3. **Implement:** Code until the test passes.
4. **UI:** Create Compose components with `@Preview` and instrumented tests.
5. **Sync:** Ensure the NestJS backend is running to test cross-device persistence.

---

## 🛡️ Quality Mandate

To maintain the integrity of the project, the following rules are enforced via **GitHub Actions**:

* **Unit Test Coverage:** Must exceed **90%** for all shared logic.
* **Compose Previews:** Required for every UI component.
* **Instrumented Tests:** Every User Story must have a verified UI test on real or emulated devices.
* **Static Analysis:** Strict Kotlin Linting and DDD boundary checks.

---

## 🛠️ Tech Highlights

* **Frontend:** Kotlin Multiplatform, Compose Multiplatform, Koin (DI), Ktor (Network).
* **Backend:** NestJS, MongoDB/Firestore (Generic Repository), JWT Auth.
* **State:** MVVM with Shared ViewModels + Flow/Coroutines.
