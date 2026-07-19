from app.models.request_models import NoveltyRequest
from app.models.response_models import NoveltyAnalysisResponse, TopMatch
from app.services.similarity_service import SimilarityService
from app.services.metrics_service import MetricsService
from app.services.keyword_service import KeywordService
from app.services.maturity_service import MaturityService
from app.services.report_service import ReportService


class NoveltyOrchestrator:
    """
    Coordinates the full novelty analysis pipeline by delegating each
    concern to its dedicated service and assembling the final response.

    This class contains zero business logic — it only defines the sequence
    of service calls and maps their outputs into the response model.

    Pipeline:
        1. Compute semantic similarity + ranking   → SimilarityService
        2. Compute statistical metrics             → MetricsService
        3. Extract keywords + keyword novelty      → KeywordService
        4. Compute innovation score                → MetricsService
        5. Analyze research maturity               → MaturityService
        6. Generate explainable report             → ReportService
        7. Assemble and return NoveltyAnalysisResponse
    """

    @staticmethod
    def analyze(request: NoveltyRequest) -> NoveltyAnalysisResponse:

        # ── Step 1: Semantic similarity + ranking ────────────────────────────
        ranked_similarities = SimilarityService.compute_ranked_similarities(
            user_abstract=request.userAbstract,
            papers=request.papers
        )

        # ── Step 2: Statistical similarity metrics ───────────────────────────
        similarity_metrics = MetricsService.compute_similarity_metrics(ranked_similarities)

        # ── Step 3: Keyword extraction + keyword novelty ─────────────────────
        user_keywords = KeywordService.extract_keywords(
            user_abstract=request.userAbstract,
            papers=request.papers
        )
        keyword_novelty = KeywordService.compute_keyword_novelty(
            user_keywords=user_keywords,
            papers=request.papers
        )

        # ── Step 4: Innovation score (semantic + keyword components) ─────────
        innovation_score = MetricsService.compute_innovation_score(
            similarity_metrics=similarity_metrics,
            keyword_novelty_score=keyword_novelty.keywordNoveltyScore
        )

        # ── Step 5: Research maturity analysis ───────────────────────────────
        research_maturity = MaturityService.analyze(request.papers)

        # ── Step 6: Explainable report generation ────────────────────────────
        report = ReportService.generate(
            innovation_score=innovation_score,
            similarity_metrics=similarity_metrics,
            keyword_novelty=keyword_novelty,
            research_maturity=research_maturity
        )

        # ── Step 7: Assemble final response ──────────────────────────────────
        top_matches = [
            TopMatch(
                paperId=p["paperId"],
                title=p["title"],
                similarity=p["similarity"],
                year=p["year"],
                citationCount=p["citationCount"]
            )
            for p in ranked_similarities[:5]
        ]

        return NoveltyAnalysisResponse(
            innovationScore=innovation_score,
            semanticSimilarity=similarity_metrics,
            topMatches=top_matches,
            keywordNovelty=keyword_novelty,
            researchMaturity=research_maturity,
            report=report
        )
