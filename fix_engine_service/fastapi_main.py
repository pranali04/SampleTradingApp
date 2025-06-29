from fastapi import FastAPI
from message_handler import Application

app = FastAPI()
application = Application()

@app.post("/send_order")
def send_order(order: dict):
    application.sendNewOrderSingle(order)
    return {"status": "sent"}
