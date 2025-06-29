from fastapi import FastAPI, WebSocket
from typing import List

app = FastAPI()
connections: List[WebSocket] = []

@app.websocket("/ws/notifications")
async def websocket_endpoint(ws: WebSocket):
    await ws.accept()
    connections.append(ws)
    try:
        while True:
            await ws.receive_text()  # Keep alive
    except:
        connections.remove(ws)

@app.post("/notify")
async def notify(payload: dict):
    to_remove = []
    for ws in connections:
        try:
            await ws.send_json(payload)
        except:
            to_remove.append(ws)
    for ws in to_remove:
        connections.remove(ws)
    return {"status": "sent"}
