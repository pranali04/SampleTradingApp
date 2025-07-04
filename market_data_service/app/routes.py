from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from typing import List

from . import models, schemas, database

router = APIRouter()

def get_db():
    db = database.SessionLocal()
    try:
        yield db
    finally:
        db.close()

@router.get("/", response_model=List[schemas.MarketDataOut])
def get_all_market_data(db: Session = Depends(get_db)):
    return db.query(models.MarketData).all()

@router.get("/{symbol}", response_model=schemas.MarketDataOut)
def get_market_data(symbol: str, db: Session = Depends(get_db)):
    market_data = db.query(models.MarketData).filter(models.MarketData.symbol == symbol).first()
    if not market_data:
        raise HTTPException(status_code=404, detail="Symbol not found")
    return market_data
