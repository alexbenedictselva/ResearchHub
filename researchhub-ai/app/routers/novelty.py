from fastapi import APIRouter

from app.models.request_models import NoveltyRequest
from app.models.response_models import NoveltyAnalysisResponse
from app.services.novelty_orchestrator import NoveltyOrchestrator


router = APIRouter(
    prefix="/ai",
    tags=["Novelty Checker"]
)


@router.post("/novelty-check", response_model=NoveltyAnalysisResponse)
def novelty_check(request: NoveltyRequest) -> NoveltyAnalysisResponse:
    """
    Receives the user abstract and retrieved papers from the Spring Boot backend,
    runs the full novelty analysis pipeline via the orchestrator, and returns
    a structured innovation report.
    """
    return NoveltyOrchestrator.analyze(request)
