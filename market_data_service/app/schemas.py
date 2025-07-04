from pydantic import BaseModel

class MarketDataOut(BaseModel):
    symbol: str
    name: str
    last_price: float
    bid: float
    ask: float

    class Config:
        orm_mode = True
