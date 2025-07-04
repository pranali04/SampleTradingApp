from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

from . import models, database
from .routes import router as auth_router

models.Base.metadata.create_all(bind=database.engine)

app = FastAPI(title="Auth Service")

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # Or your frontend URL(s)
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

app.include_router(auth_router, prefix="/auth")
