from fastapi import FastAPI
from app.routers.summarize import router
from app.utils.model_loader import load_model

app = FastAPI(
    title="ResearchHub AI Service",
    version="1.0"
)

@app.on_event("startup")
def startup_event():
    load_model()

app.include_router(router)

@app.get("/")
def home():
    return {"message": "ResearchHub AI Service Running"}