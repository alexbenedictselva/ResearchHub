from fastapi import APIRouter

from app.models.request_models import NoveltyRequest

from app.models.response_models import NoveltyResponse

from app.services.novelty_service import NoveltyService


router = APIRouter(
    prefix="/ai",
    tags=["Novelty Checker"]
)


@router.post(
    "/novelty-check",
    response_model=NoveltyResponse
)
def novelty(request: NoveltyRequest):

    result = NoveltyService.check_novelty(

        request.userAbstract,

        request.papers

    )

    return result