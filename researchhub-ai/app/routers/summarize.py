from fastapi import APIRouter

from app.models.request_models import SummaryRequest
from app.models.response_models import SummaryResponse
from app.services.summarizer_service import SummarizerService

router = APIRouter(
    prefix="/ai",
    tags=["AI"]
)

@router.post("/summarize", response_model=SummaryResponse)
def summarize(request: SummaryRequest):

    summary = SummarizerService.summarize(
        request.abstract
    )

    return SummaryResponse(
        summary=summary
    )