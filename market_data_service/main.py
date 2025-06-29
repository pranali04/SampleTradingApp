from fastapi import FastAPI, WebSocket
import csv
import asyncio
import random

app = FastAPI()

@app.websocket("/ws/marketdata")
async def marketdata_ws(ws: WebSocket):
    await ws.accept()
    while True:
        price = round(185.50 * random.uniform(0.99, 1.01), 2)
        await ws.send_json({
            "symbol": "AAPL",
            "price": price
        })
        await asyncio.sleep(1)
