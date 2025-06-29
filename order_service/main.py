from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from sqlalchemy import create_engine, Column, Integer, String, Float, JSON
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker
import requests

DATABASE_URL = "postgresql+psycopg2://user:pass@db:5432/trading_db"

engine = create_engine(DATABASE_URL)
SessionLocal = sessionmaker(bind=engine)
Base = declarative_base()

NOTIFY_URL = "http://notification_service:8004/notify"

FIX_ENGINE_URL = "http://fix_engine_service:9000/send_order"

app = FastAPI()

class OrderModel(Base):
    __tablename__ = "orders"
    id = Column(Integer, primary_key=True, index=True)
    symbol = Column(String)
    side = Column(String)
    quantity = Column(Integer)
    price = Column(Float)
    order_type = Column(String)
    client_id = Column(String)
    time_in_force = Column(String)
    venue = Column(String)
    custom_tags = Column(JSON)
    status = Column(String)
    filled_qty = Column(Integer, default=0)

Base.metadata.create_all(bind=engine)

class Order(BaseModel):
    symbol: str
    side: str
    quantity: int
    price: float
    order_type: str
    client_id: str
    time_in_force: str
    venue: str
    custom_tags: dict | None = None


@app.post("/order")
def place_order(order: Order):
    db = SessionLocal()
    db_order = OrderModel(
        symbol=order.symbol,
        side=order.side,
        quantity=order.quantity,
        price=order.price,
        order_type=order.order_type,
        client_id=order.client_id,
        time_in_force=order.time_in_force,
        venue=order.venue,
        custom_tags=order.custom_tags,
        status="New",
        filled_qty=0
    )
    db.add(db_order)
    db.commit()
    db.refresh(db_order)

    payload = db_order.__dict__
    requests.post(FIX_ENGINE_URL, json=payload)

    requests.post(NOTIFY_URL, json=db_order.__dict__)
    return db_order

@app.patch("/order/{order_id}/amend")
def amend_order(order_id: int, changes: dict):
    db = SessionLocal()
    order = db.query(OrderModel).filter(OrderModel.id == order_id).first()
    if not order:
        raise HTTPException(status_code=404, detail="Order not found")
    if "quantity" in changes:
        order.quantity = changes["quantity"]
    if "price" in changes:
        order.price = changes["price"]
    order.status = "Amended"
    db.commit()
    requests.post(NOTIFY_URL, json=order.__dict__)
    return order

@app.post("/order/{order_id}/cancel")
def cancel_order(order_id: int):
    db = SessionLocal()
    order = db.query(OrderModel).filter(OrderModel.id == order_id).first()
    if not order:
        raise HTTPException(status_code=404, detail="Order not found")
    order.status = "Canceled"
    db.commit()
    requests.post(NOTIFY_URL, json=order.__dict__)
    return order
