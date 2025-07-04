from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

from . import models, database
from .routes import router as market_router
from .websocket import router as ws_router

models.Base.metadata.create_all(bind=database.engine)

app = FastAPI(title="Market Data Service")

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # Or your frontend domain
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

app.include_router(market_router, prefix="/api/marketdata")
app.include_router(ws_router)
