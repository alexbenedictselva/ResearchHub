from pydantic import BaseModel
from typing import List
class SummaryRequest(BaseModel):
    abstract: str

class SimilarityRequest(BaseModel):

    text1: str

    text2: str


class Paper(BaseModel):

    paperId: str

    title: str

    abstract: str


class NoveltyRequest(BaseModel):

    userAbstract: str

    papers: List[Paper]