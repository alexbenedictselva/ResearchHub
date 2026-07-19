from app.utils.model_loader import get_summarizer


class SummarizerService:

    @staticmethod
    def summarize(text: str) -> str:
        summarizer = get_summarizer()
        result = summarizer(text, max_new_tokens=100)
        return result[0]["generated_text"]
