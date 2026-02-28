# Architecture Specification

## Overview
The project follows **Clean Architecture** and **Domain-Driven Design (DDD)** principles to ensure high testability and platform independence.

## Layers
1. **Domain Layer (Shared):** Contains Entities (Board, Tile), Value Objects, and Use Cases. No dependencies on UI or Frameworks.
2. **Data Layer (Shared):** Implementation of Repositories using Ktor for networking and local persistence.
3. **Presentation Layer (Shared/Compose):** MVVM pattern. ViewModels reside in the shared module to handle state logic.
4. **Framework Layer:** Android and iOS specific entry points.

## Modularization Strategy
- `:shared:core`: Base classes, logging, and utilities.
- `:shared:game`: Domain and Data logic for the 2048 engine.
- `:shared:ui`: Compose Multiplatform components and ViewModels.
- `:androidApp`: Android-specific configuration.
- `:iosApp`: Xcode project and Swift wrappers.

## Testing Standards
- **TDD:** All Business Logic must have failing tests written before implementation.
- **Coverage:** Minimum **90% Unit Test coverage** for shared modules.
- **Instrumented Tests:** UI components must be tested using `createComposeRule` (Android) and XCTest (iOS).
