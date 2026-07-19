from pydantic import BaseModel, Field
from typing import List, Optional


class SummaryRequest(BaseModel):
    abstract: str


class SimilarityRequest(BaseModel):
    text1: str
    text2: str


class Paper(BaseModel):
    """
    Represents a single paper retrieved from Semantic Scholar.
    year and citationCount are optional because Semantic Scholar
    does not guarantee their presence for every paper.
    """
    paperId: str
    title: str
    abstract: str
    year: Optional[int] = Field(default=None, description="Publication year of the paper")
    citationCount: Optional[int] = Field(default=None, description="Number of citations the paper has received")


class NoveltyRequest(BaseModel):
    """
    The main request payload sent by the Spring Boot backend.
    Contains the user's abstract and the list of related papers
    retrieved from Semantic Scholar.
    """
    userAbstract: str = Field(..., min_length=50, description="The user's research abstract to be analyzed")
    papers: List[Paper] = Field(..., min_length=1, description="Related papers retrieved from Semantic Scholar")
