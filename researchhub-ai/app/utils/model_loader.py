from transformers import pipeline

summarizer = None

def load_model():
    global summarizer

    if summarizer is None:
        print("Loading T5-Small Summarization Model...")

        summarizer = pipeline(
            task="summarization",
            model="t5-small",
            tokenizer="t5-small"
        )

        print("AI Model Loaded Successfully.")