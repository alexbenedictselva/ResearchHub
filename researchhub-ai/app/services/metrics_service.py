from app.models.response_models import SemanticSimilarityMetrics


class MetricsService:
    """
    Responsible for computing statistical similarity metrics and the
    Innovation Score from a ranked list of paper similarity results.

    Keeps all scoring logic in one place so the formula can be tuned
    independently without touching any other service.
    """

    @staticmethod
    def compute_similarity_metrics(ranked_similarities: list[dict]) -> SemanticSimilarityMetrics:
        """
        Derives highest, average, and lowest similarity from the ranked list.

        Args:
            ranked_similarities: Output of SimilarityService.compute_ranked_similarities()
                                  already sorted descending by similarity.

        Returns:
            SemanticSimilarityMetrics with highest, average, lowest fields.
        """
        scores = [p["similarity"] for p in ranked_similarities]

        return SemanticSimilarityMetrics(
            highest=round(scores[0], 4),
            average=round(sum(scores) / len(scores), 4),
            lowest=round(scores[-1], 4)
        )

    @staticmethod
    def compute_innovation_score(
        similarity_metrics: SemanticSimilarityMetrics,
        keyword_novelty_score: float
    ) -> float:
        """
        Computes the final Innovation Score on a 0–100 scale.

        Formula:
            - Semantic component (70% weight): how dissimilar the abstract
              is from the closest existing paper.
            - Keyword component (30% weight): ratio of novel keywords not
              found in the existing literature.

        Both components are combined and clamped to [0, 100].

        Args:
            similarity_metrics:    Computed semantic similarity stats.
            keyword_novelty_score: Float between 0 and 1 from KeywordService.

        Returns:
            Innovation score as a float between 0.0 and 100.0.
        """
        semantic_component = (1 - similarity_metrics.highest) * 100 * 0.70
        keyword_component = keyword_novelty_score * 100 * 0.30

        raw_score = semantic_component + keyword_component

        return round(min(max(raw_score, 0.0), 100.0), 2)
