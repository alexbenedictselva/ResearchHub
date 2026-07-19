from app.models.response_models import (
    InnovationReport,
    SemanticSimilarityMetrics,
    KeywordNovelty,
    ResearchMaturity,
)


class ReportService:
    """
    Responsible for generating a human-readable, explainable innovation report
    based on all computed metrics.

    Currently uses rule-based logic to produce strengths, weaknesses,
    recommendations, and a narrative summary. The generate() method signature
    is intentionally stable so an LLM (e.g. via HuggingFace or OpenAI) can
    be injected as an alternative generator later without touching the
    orchestrator or router.
    """

    @staticmethod
    def generate(
        innovation_score: float,
        similarity_metrics: SemanticSimilarityMetrics,
        keyword_novelty: KeywordNovelty,
        research_maturity: ResearchMaturity,
    ) -> InnovationReport:
        """
        Produces an InnovationReport by evaluating each metric against
        defined thresholds and assembling contextual feedback.

        Args:
            innovation_score:    Final computed innovation score (0–100).
            similarity_metrics:  Highest, average, lowest similarity stats.
            keyword_novelty:     Keyword overlap and novelty ratio.
            research_maturity:   Maturity label and score of the research area.

        Returns:
            InnovationReport with strengths, weaknesses, recommendations, summary.
        """
        strengths = ReportService._evaluate_strengths(
            innovation_score, similarity_metrics, keyword_novelty, research_maturity
        )
        weaknesses = ReportService._evaluate_weaknesses(
            innovation_score, similarity_metrics, keyword_novelty, research_maturity
        )
        recommendations = ReportService._build_recommendations(
            similarity_metrics, keyword_novelty, research_maturity
        )
        summary = ReportService._build_summary(
            innovation_score, similarity_metrics, keyword_novelty, research_maturity
        )

        return InnovationReport(
            strengths=strengths,
            weaknesses=weaknesses,
            recommendations=recommendations,
            summary=summary
        )

    # ── Internal rule evaluators ─────────────────────────────────────────────

    @staticmethod
    def _evaluate_strengths(
        score: float,
        sim: SemanticSimilarityMetrics,
        kw: KeywordNovelty,
        mat: ResearchMaturity,
    ) -> list[str]:
        strengths = []

        if sim.highest < 0.4:
            strengths.append(
                "Your abstract is semantically distinct from all retrieved papers, "
                "indicating strong conceptual originality."
            )
        elif sim.highest < 0.6:
            strengths.append(
                "Your abstract shows moderate semantic distance from existing work, "
                "suggesting a meaningful contribution."
            )

        if kw.keywordNoveltyScore >= 0.6:
            strengths.append(
                f"{len(kw.novelKeywords)} of your keywords ({', '.join(kw.novelKeywords[:5])}) "
                "are not present in the retrieved literature, indicating novel terminology."
            )

        if mat.maturityLabel in ("Emerging", "Developing"):
            strengths.append(
                f"You are working in a {mat.maturityLabel.lower()} research area, "
                "which offers greater opportunity for impactful contributions."
            )

        if score >= 70:
            strengths.append(
                "The overall innovation score is high, reflecting strong differentiation "
                "from the existing body of work."
            )

        if not strengths:
            strengths.append(
                "The abstract addresses a recognized research problem with some degree "
                "of differentiation from existing work."
            )

        return strengths

    @staticmethod
    def _evaluate_weaknesses(
        score: float,
        sim: SemanticSimilarityMetrics,
        kw: KeywordNovelty,
        mat: ResearchMaturity,
    ) -> list[str]:
        weaknesses = []

        if sim.highest >= 0.75:
            weaknesses.append(
                "The abstract is highly similar to at least one existing paper. "
                "This significantly reduces perceived novelty."
            )
        elif sim.highest >= 0.6:
            weaknesses.append(
                "There is notable semantic overlap with existing papers. "
                "Consider differentiating your core contribution more explicitly."
            )

        if kw.keywordNoveltyScore < 0.3:
            weaknesses.append(
                f"Most of your keywords ({', '.join(kw.overlapKeywords[:5])}) already appear "
                "in the existing literature, suggesting limited terminological novelty."
            )

        if mat.maturityLabel in ("Established", "Saturated"):
            weaknesses.append(
                f"The research area is {mat.maturityLabel.lower()}, meaning the field is "
                "competitive and incremental contributions may be harder to distinguish."
            )

        if score < 40:
            weaknesses.append(
                "The overall innovation score is low. The abstract may need significant "
                "repositioning to establish a clear novel contribution."
            )

        if not weaknesses:
            weaknesses.append(
                "No critical weaknesses detected, but further differentiation of the "
                "core contribution could strengthen the novelty claim."
            )

        return weaknesses

    @staticmethod
    def _build_recommendations(
        sim: SemanticSimilarityMetrics,
        kw: KeywordNovelty,
        mat: ResearchMaturity,
    ) -> list[str]:
        recommendations = []

        if sim.highest >= 0.6:
            recommendations.append(
                "Explicitly state how your approach differs from the most similar existing "
                "work. Highlight your unique methodology, dataset, or problem framing."
            )

        if kw.keywordNoveltyScore < 0.4 and kw.novelKeywords:
            recommendations.append(
                f"Emphasize novel concepts such as {', '.join(kw.novelKeywords[:3])} "
                "more prominently in your abstract to signal originality."
            )

        if kw.keywordNoveltyScore < 0.3:
            recommendations.append(
                "Consider introducing domain-specific terminology that better captures "
                "the unique aspects of your contribution."
            )

        if mat.maturityLabel in ("Established", "Saturated"):
            recommendations.append(
                "Since this is a mature research area, clearly articulate what gap or "
                "limitation in prior work your research addresses."
            )

        if mat.maturityLabel == "Emerging":
            recommendations.append(
                "Leverage the emerging nature of this field by grounding your work in "
                "foundational references while clearly staking out new territory."
            )

        if sim.average > 0.65:
            recommendations.append(
                "The average similarity across all papers is high. Consider narrowing "
                "your research scope to a more specific sub-problem."
            )

        if not recommendations:
            recommendations.append(
                "Maintain the current level of differentiation and ensure the abstract "
                "clearly communicates the core novel contribution in the opening sentences."
            )

        return recommendations

    @staticmethod
    def _build_summary(
        score: float,
        sim: SemanticSimilarityMetrics,
        kw: KeywordNovelty,
        mat: ResearchMaturity,
    ) -> str:
        if score >= 70:
            verdict = "demonstrates strong novelty"
        elif score >= 45:
            verdict = "shows moderate novelty"
        else:
            verdict = "shows limited novelty"

        return (
            f"Your research abstract {verdict} with an Innovation Score of {score}/100. "
            f"The highest semantic similarity to any existing paper is {sim.highest:.2f}, "
            f"and the average similarity across all retrieved papers is {sim.average:.2f}. "
            f"Keyword analysis identified {len(kw.novelKeywords)} novel keyword(s) out of "
            f"{len(kw.userKeywords)} extracted, yielding a keyword novelty ratio of "
            f"{kw.keywordNoveltyScore:.0%}. "
            f"The research area is classified as {mat.maturityLabel} "
            f"(maturity score: {mat.maturityScore:.2f}). "
            f"{'Focus on amplifying your unique contributions to maximize impact.' if score < 70 else 'Your abstract is well-positioned for a strong novelty claim.'}"
        )
