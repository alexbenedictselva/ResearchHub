try:
    from sentence_transformers import SentenceTransformer
except Exception:  # pragma: no cover - optional dependency
    SentenceTransformer = None

embedding_model = None
summarizer = None


def load_models():
    """
    Called once at application startup.
    Only loads the embedding model which is required by the core novelty pipeline.
    The summarizer is loaded lazily on first use to avoid startup failures
    caused by transformers version incompatibilities.
    """
    global embedding_model

    if embedding_model is None:
        if SentenceTransformer is None:
            print("sentence-transformers is not installed; skipping embedding model load.")
            return

        print("Loading Sentence Transformer...")
        embedding_model = SentenceTransformer("sentence-transformers/all-MiniLM-L6-v2")
        print("Sentence Transformer Loaded Successfully.")


def get_summarizer():
    """
    Lazily loads the summarizer pipeline on first call.
    Separated from startup so the app runs even if the summarizer fails.
    """
    global summarizer

    if summarizer is None:
        from transformers import pipeline
        print("Loading T5 Model...")
        summarizer = pipeline("text-generation", model="t5-small", tokenizer="t5-small")
        print("T5 Loaded Successfully.")

    return summarizer
