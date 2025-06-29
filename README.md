# Sample Trading Platform — Full Stack (FIX + WebSocket + Microservices)
# A fully containerized mock trading system with:

## Frontend: React (Vite) served via NGINX.

## API Gateway: NGINX reverse proxy.

## Backend: Python FastAPI microservices for Auth, Orders, Market Data, FIX Engine.

## Notification Service: WebSocket server for real-time order updates.

## FIX Engine: QuickFIX + Fiximulator for dummy exchange simulation.

## Database: PostgreSQL for users, orders, and market data.

```mermaid
    graph TD
        A[Client<br/>(Browser / React)] --> B[Frontend<br/>(NGINX + React App)]
        B --> C[API Gateway<br/>(NGINX Proxy)]
        C --> D[Auth Service<br/>(FastAPI + DB)]
        C --> E[Market Data Service<br/>(FastAPI + WS)]
        C --> F[Order Service<br/>(FastAPI REST)]
        F --> G[FIX Engine Service<br/>(QuickFIX + Fiximulator)]
        G --> I[Dummy Exchange Simulator<br/>(Fiximulator)]
        F --> H[Notification Service<br/>(WS)]
        E --> J[Database<br/>(PostgreSQL)]
        F --> J
        D --> J
        H --> B
        E --> B
```
