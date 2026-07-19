import numpy as np
import app.utils.model_loader as model_loader


class EmbeddingService:
    """
    Responsible solely for converting text into vector embeddings
    using the loaded Sentence Transformer model.

    All other services that need embeddings must go through this class
    to avoid duplicating model access logic across the codebase.
    """

    @staticmethod
    def encode(texts: list[str]) -> np.ndarray:
        """
        Encodes a list of texts into a 2D numpy array of embeddings.

        Args:
            texts: List of strings to encode.

        Returns:
            A numpy array of shape (len(texts), embedding_dim).
        """
        return model_loader.embedding_model.encode(texts, convert_to_numpy=True)

    @staticmethod
    def encode_single(text: str) -> np.ndarray:
        """
        Convenience method to encode a single string.

        Returns:
            A 1D numpy array representing the embedding.
        """
        return model_loader.embedding_model.encode([text], convert_to_numpy=True)[0]
