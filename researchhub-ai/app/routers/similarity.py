from fastapi import APIRouter

from app.models.request_models import SimilarityRequest
from app.models.response_models import SimilarityResponse

from app.services.similarity_service import SimilarityService

router = APIRouter(
    prefix="/ai",
    tags=["AI"]
)

@router.post(
    "/similarity",
    response_model=SimilarityResponse
)
def compare(request: SimilarityRequest):

    similarity = SimilarityService.compare(
        request.text1,
        request.text2
    )

    return SimilarityResponse(
        similarity=similarity
    )