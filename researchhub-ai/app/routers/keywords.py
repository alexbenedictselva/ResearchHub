from fastapi import APIRouter

from app.models.request_models import KeywordExtractionRequest
from app.models.response_models import KeywordExtractionResponse
from app.services.keyword_service import KeywordService


router = APIRouter(
    prefix="/ai",
    tags=["Keywords"]
)


@router.post("/extract-keywords", response_model=KeywordExtractionResponse)
def extract_keywords(request: KeywordExtractionRequest) -> KeywordExtractionResponse:
    """
    Accepts the user's abstract from the request body and returns the most
    important keywords extracted from it.
    """
    keywords = KeywordService.extract_keywords_from_text(request.userAbstract)
    return KeywordExtractionResponse(keywords=keywords)
