# API Specification (Ktor <-> NestJS)

## Endpoints
### `POST /sync`
- **Purpose:** Save current board state.
- **Body:** `{ userId: string, score: number, grid: number[][], timestamp: long }`

### `GET /leaderboard`
- **Purpose:** Retrieve top 100 global scores.
- **Response:** `Array<{ username: string, highScore: number }>`
