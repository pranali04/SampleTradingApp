from sqlalchemy import Column, Integer, String, Float
from .database import Base

class MarketData(Base):
    __tablename__ = "market_data"

    id = Column(Integer, primary_key=True, index=True)
    symbol = Column(String, unique=True, index=True, nullable=False)
    name = Column(String, nullable=False)
    last_price = Column(Float, nullable=False)
    bid = Column(Float, nullable=False)
    ask = Column(Float, nullable=False)
