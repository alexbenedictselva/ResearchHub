import numpy as np
from sklearn.metrics.pairwise import cosine_similarity

from app.models.request_models import Paper
from app.services.embedding_service import EmbeddingService


class SimilarityService:
    """
    Responsible for computing cosine similarity between the user abstract
    and each retrieved paper, then ranking the results by similarity score.

    Depends on EmbeddingService for vector generation — does not touch
    the model directly.
    """

    @staticmethod
    def compute_ranked_similarities(
        user_abstract: str,
        papers: list[Paper]
    ) -> list[dict]:
        """
        Encodes the user abstract and all paper abstracts in a single batch
        call for efficiency, then computes cosine similarity for each paper.

        Args:
            user_abstract: The user's research abstract.
            papers:         List of Paper objects from the request.

        Returns:
            List of dicts sorted by similarity descending, each containing:
            paperId, title, similarity, year, citationCount.
        """
        # Batch encode user abstract + all paper abstracts in one call
        texts = [user_abstract] + [p.abstract for p in papers]
        embeddings: np.ndarray = EmbeddingService.encode(texts)

        user_embedding = embeddings[0]
        paper_embeddings = embeddings[1:]

        results = []

        for i, paper in enumerate(papers):
            score = cosine_similarity(
                [user_embedding],
                [paper_embeddings[i]]
            )[0][0]

            results.append({
                "paperId": paper.paperId,
                "title": paper.title,
                "similarity": round(float(score), 4),
                "year": paper.year,
                "citationCount": paper.citationCount
            })

        results.sort(key=lambda x: x["similarity"], reverse=True)

        return results

    @staticmethod
    def compare(text1: str, text2: str) -> float:
        """
        Compares two arbitrary texts and returns their cosine similarity.
        Used by the standalone /ai/similarity endpoint.
        """
        embeddings = EmbeddingService.encode([text1, text2])

        score = cosine_similarity(
            [embeddings[0]],
            [embeddings[1]]
        )[0][0]

        return round(float(score), 4)
