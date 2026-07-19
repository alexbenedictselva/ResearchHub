from pydantic import BaseModel

class SummaryRequest(BaseModel):
    abstract: str