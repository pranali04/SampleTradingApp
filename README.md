# Sample Trading Platform — Full Stack (FIX + WebSocket + Microservices)

A fully containerized mock trading system with:

**Frontend:** React (Vite) served via NGINX.

**API Gateway:** NGINX reverse proxy.

**Backend:** Python FastAPI microservices for Auth, Orders, Market Data, FIX Engine.

**Notification Service:** WebSocket server for real-time order updates.

**FIX Engine:** QuickFIX + Fiximulator for dummy exchange simulation.

**Database:** PostgreSQL for users, orders, and market data.

Client (React)
  ↓
Frontend (NGINX + React)
  ↓
API Gateway (NGINX)
  ├─→ Auth Service (FastAPI) → PostgreSQL
  ├─→ Market Data Service (WebSockets) → PostgreSQL
  └─→ Order Service (REST)
       ├─→ FIX Engine → Dummy Exchange (Fiximulator)
       ├─→ Notifications (WebSockets → Frontend)
       └─→ PostgreSQL


       
| Service           | Tech Stack                       | Purpose                                                                   |
| ----------------- | -------------------------------- | ------------------------------------------------------------------------- |
| **Frontend**      | React + Vite + NGINX             | UI for login/register, market data, order entry, order status.            |
| **API Gateway**   | NGINX                            | Routes `/auth/`, `/orders/`, `/marketdata/`, `/notifications/` to backend |
| **Auth Service**  | FastAPI + JWT + PostgreSQL       | User registration, login, secure JWT generation.                          |
| **Market Data**   | FastAPI + WebSocket + PostgreSQL | Provides live price feeds & market search.                                |
| **Order Service** | FastAPI + PostgreSQL + REST      | Handles new/amend/cancel orders, persists orders, talks to FIX Engine.    |
| **FIX Engine**    | QuickFIX/py + Fiximulator        | Simulates order lifecycle: Ack → Partial Fill → Fill → Cancel/Amend.      |
| **Notification**  | FastAPI + WebSocket              | Pushes real-time order status to frontend.                                |
| **Database**      | PostgreSQL                       | Single DB for users, orders, and market data.                             |

**Clone this repo**
git clone https://your-repo-url.git
cd mock-trading-platform

**.env (recommended)**
Create .env or Docker secrets for:
DATABASE_URL=postgresql+psycopg2://user:pass@db:5432/trading_db
JWT_SECRET=YOUR_SECURE_SECRET

**Build and run with Docker Compose**
docker-compose up --build

This will:
Build all services
Run them in separate containers
Connect them on trading_net network

**Access the UI**
Visit: http://localhost:3000
Register a new user
Log in → get JWT
Subscribe to market data
Place/amend/cancel orders
View live order status updates

**Security Best Practices**
Use strong JWT secret keys and rotate them periodically.

Use HTTPS for the Gateway in production.

Run NGINX reverse proxy for TLS termination.

Secure DB with user/password & Docker secrets.

**Useful Commands**
Action	Command
Build & run all services	 docker-compose up --build
Stop all services	         docker-compose down
View logs	                 docker-compose logs -f
Exec into container	         docker exec -it <container_name> /bin/sh