import app.utils.model_loader as model_loader

class SummarizerService:

    @staticmethod
    def summarize(text: str):

        result = model_loader.summarizer(
            text,
            max_length=100,
            min_length=30,
            do_sample=False
        )

        return result[0]["summary_text"]