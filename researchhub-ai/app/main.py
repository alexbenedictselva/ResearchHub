from fastapi import FastAPI

from app.utils.model_loader import load_models

from app.routers.summarize import router as summarize_router
from app.routers.novelty import router as novelty_router
from app.routers.keywords import router as keywords_router

app = FastAPI(
    title="ResearchHub AI Service",
    version="1.0"
)


@app.on_event("startup")
def startup():

    load_models()


app.include_router(summarize_router)
app.include_router(novelty_router)
app.include_router(keywords_router)


@app.get("/")
def home():

    return {
        "message": "ResearchHub AI Running"
    }