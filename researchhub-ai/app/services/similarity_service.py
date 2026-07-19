from sklearn.metrics.pairwise import cosine_similarity

import app.utils.model_loader as model_loader


class SimilarityService:

    @staticmethod
    def compare(text1, text2):

        embeddings = model_loader.embedding_model.encode(
            [text1, text2]
        )

        similarity = cosine_similarity(
            [embeddings[0]],
            [embeddings[1]]
        )[0][0]

        return round(float(similarity), 4)