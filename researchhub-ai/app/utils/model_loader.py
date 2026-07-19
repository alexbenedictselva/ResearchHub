from transformers import pipeline
from sentence_transformers import SentenceTransformer

summarizer = None
embedding_model = None


def load_models():
    global summarizer
    global embedding_model

    if summarizer is None:

        print("Loading T5 Model...")

        summarizer = pipeline(
            task="summarization",
            model="t5-small",
            tokenizer="t5-small"
        )

        print("T5 Loaded Successfully.")

    if embedding_model is None:

        print("Loading Sentence Transformer...")

        embedding_model = SentenceTransformer(
            "sentence-transformers/all-MiniLM-L6-v2"
        )

        print("Sentence Transformer Loaded Successfully.")