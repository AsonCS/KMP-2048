### 📄 0003-repository-pattern-with-ktor-and-generic-document-db.md

**Status:** Accepted

**Context:** The app needs to fetch leaderboards and sync game state to a backend. We want the flexibility to switch between MongoDB and Firebase without rewriting the mobile client.

**Decision:** Use the **Repository Pattern** in the Data Layer. The Domain layer will interact with an `IGameRepository` interface. We will use **Ktor** for HTTP requests and **kotlinx.serialization** for JSON. The backend (NestJS) will abstract the specific Document DB.

**Consequences:** * **Pro:** The UI and Domain layers don't know if the data is coming from a local cache or a remote server.

* **Pro:** Swapping the backend database (e.g., Mongo to Firestore) only requires changes in the NestJS implementation, not the KMP code.
