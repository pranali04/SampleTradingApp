import time
from sqlalchemy import create_engine, text
from sqlalchemy.exc import OperationalError
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker

DATABASE_URL = "postgresql://postgres:postgres@db:5432/trading"

def get_engine():
    backoff = 0.5
    while True:
        try:
            engine = create_engine(DATABASE_URL, pool_pre_ping=True)
            # Try a quick test connect
            with engine.connect() as conn:
                conn.execute(text("SELECT 1"))
            return engine
        except OperationalError:
            print(f"Database unavailable, retrying in {backoff}s...")
            time.sleep(backoff)
            backoff = min(backoff * 2, 5)  # avoid infinite backoff growth

engine = get_engine()
# engine = create_engine(DATABASE_URL)
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)

Base = declarative_base()
