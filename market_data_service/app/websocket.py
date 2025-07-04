from fastapi import APIRouter, WebSocket, WebSocketDisconnect
from sqlalchemy.orm import Session
from . import database, models
import asyncio

router = APIRouter()

@router.websocket("/ws/marketdata")
async def market_data_ws(websocket: WebSocket):
    await websocket.accept()
    db = database.SessionLocal()
    try:
        while True:
            # Mock price update
            data = db.query(models.MarketData).all()
            result = [
                {
                    "symbol": row.symbol,
                    "name": row.name,
                    "last_price": row.last_price,
                    "bid": row.bid,
                    "ask": row.ask
                }
                for row in data
            ]
            await websocket.send_json(result)
            await asyncio.sleep(2)  # Update every 2 seconds
    except WebSocketDisconnect:
        print("WebSocket disconnected")
    finally:
        db.close()
