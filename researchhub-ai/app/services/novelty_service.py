from sklearn.metrics.pairwise import cosine_similarity

import app.utils.model_loader as model_loader


class NoveltyService:

    @staticmethod
    def check_novelty(user_abstract, papers):

        texts = [user_abstract]

        for paper in papers:
            texts.append(paper.abstract)

        embeddings = model_loader.embedding_model.encode(texts)

        user_embedding = embeddings[0]

        similarities = []

        for i, paper in enumerate(papers):

            similarity = cosine_similarity(
                [user_embedding],
                [embeddings[i + 1]]
            )[0][0]

            similarities.append({
                "paperId": paper.paperId,
                "title": paper.title,
                "similarity": round(float(similarity), 4)
            })

        similarities.sort(
            key=lambda x: x["similarity"],
            reverse=True
        )

        highest = similarities[0]["similarity"]

        average = sum(
            p["similarity"] for p in similarities
        ) / len(similarities)

        novelty = round((1 - highest) * 100, 2)

        return {

            "noveltyScore": novelty,

            "highestSimilarity": highest,

            "averageSimilarity": round(average, 4),

            "topMatches": similarities[:5]

        }