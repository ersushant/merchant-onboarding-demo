# PartnerLink Merchant Onboarding Demo

Small full-stack demo for a merchant application flow. It demonstrates a React 18 + TypeScript user journey backed by Spring Boot, JPA, H2, validation, idempotency and JWT-protected management APIs.

## What it demonstrates

- A React 18 TypeScript application form with client validation, inline errors, API error display, loading state and success confirmation.
- `POST /api/merchants` accepts a required `Idempotency-Key`. Repeating a key with the same payload returns the original merchant; reusing it with a different payload returns `409 Conflict`.
- Write operations are transactional: merchant creation (including its idempotency record), profile updates, status changes and deletion.
- Explicit status lifecycle: `APPLICATION_SUBMITTED → UNDER_REVIEW → APPROVED → SHIPPED`. An application may instead move from `UNDER_REVIEW → REJECTED`; `SHIPPED` and `REJECTED` are final.
- Registration/login endpoints issue JWTs. The public application endpoint is intentionally open for the demo; merchant management endpoints require `Authorization: Bearer <token>`.

## Run locally

Open two terminals.

```bash
cd backend
mvn spring-boot:run
```

```bash
cd frontend
npm install
npm run dev
```

Open `http://localhost:5173`. The frontend calls `http://localhost:8080` by default. Set `VITE_API_URL` if the API runs elsewhere.

## API quick reference

| Method | Endpoint | Access |
| --- | --- | --- |
| POST | `/api/merchants` | Public; requires `Idempotency-Key` |
| PATCH | `/api/merchants/{id}/status` | JWT |
| GET/PUT/DELETE | `/api/merchants/{id}` | JWT |
| POST | `/api/auth/register` | Public |
| POST | `/api/auth/login` | Public |

Swagger UI is available at `http://localhost:8080/swagger-ui/index.html`.

## Verify

```bash
cd backend && mvn test
cd frontend && npm run build
```

The backend tests cover idempotent replay/conflict, the initial status, valid/invalid transitions and not-found handling.
