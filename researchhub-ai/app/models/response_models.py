from pydantic import BaseModel
from typing import List


class PaperSimilarity(BaseModel):

    paperId: str

    title: str

    similarity: float


class NoveltyResponse(BaseModel):

    noveltyScore: float

    highestSimilarity: float

    averageSimilarity: float

    topMatches: List[PaperSimilarity]

class SummaryResponse(BaseModel):
    summary: str

class SimilarityResponse(BaseModel):

    similarity: float