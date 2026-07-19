from datetime import datetime
from typing import Optional

from app.models.request_models import Paper
from app.models.response_models import ResearchMaturity


# Thresholds used to classify research area maturity
_CITATION_THRESHOLDS = {
    "saturated":   500,
    "established": 150,
    "developing":  30,
}

_YEAR_RECENCY_WINDOW = 3  # papers within this many years are considered recent


class MaturityService:
    """
    Responsible for estimating the maturity of a research area based on
    the publication years and citation counts of the retrieved papers.

    Maturity labels:
        Emerging    — very recent papers, low citations
        Developing  — growing area, moderate citations
        Established — well-cited, older literature
        Saturated   — heavily cited, long-standing field
    """

    @staticmethod
    def analyze(papers: list[Paper]) -> ResearchMaturity:
        """
        Computes average year, average citation count, a normalized maturity
        score, and a human-readable maturity label.

        Papers missing year or citationCount are excluded from those specific
        calculations but do not cause the method to fail.

        Args:
            papers: List of Paper objects from the request.

        Returns:
            ResearchMaturity model with all fields populated.
        """
        current_year = datetime.now().year

        years = [p.year for p in papers if p.year is not None]
        citations = [p.citationCount for p in papers if p.citationCount is not None]

        avg_year: Optional[float] = round(sum(years) / len(years), 1) if years else None
        avg_citations: Optional[float] = round(sum(citations) / len(citations), 1) if citations else None

        maturity_score, maturity_label = MaturityService._classify(
            avg_year=avg_year,
            avg_citations=avg_citations,
            current_year=current_year
        )

        return ResearchMaturity(
            averageYear=avg_year,
            averageCitationCount=avg_citations,
            maturityLabel=maturity_label,
            maturityScore=maturity_score
        )

    @staticmethod
    def _classify(
        avg_year: Optional[float],
        avg_citations: Optional[float],
        current_year: int
    ) -> tuple[float, str]:
        """
        Derives a normalized maturity score [0, 1] and label from the
        average year and citation count.

        Score closer to 0 = Emerging, closer to 1 = Saturated.
        When data is missing, falls back to citation-only or year-only scoring.
        """
        citation_score = 0.5  # neutral default when data is absent
        year_score = 0.5

        if avg_citations is not None:
            if avg_citations >= _CITATION_THRESHOLDS["saturated"]:
                citation_score = 1.0
            elif avg_citations >= _CITATION_THRESHOLDS["established"]:
                citation_score = 0.75
            elif avg_citations >= _CITATION_THRESHOLDS["developing"]:
                citation_score = 0.5
            else:
                citation_score = 0.25

        if avg_year is not None:
            years_ago = current_year - avg_year
            # Normalize: 0 years ago → 0.0 (very recent), 20+ years ago → 1.0 (old)
            year_score = round(min(years_ago / 20.0, 1.0), 4)

        # Weighted combination: citations carry more signal than recency
        maturity_score = round(citation_score * 0.65 + year_score * 0.35, 4)

        if maturity_score >= 0.75:
            label = "Saturated"
        elif maturity_score >= 0.50:
            label = "Established"
        elif maturity_score >= 0.25:
            label = "Developing"
        else:
            label = "Emerging"

        return maturity_score, label
