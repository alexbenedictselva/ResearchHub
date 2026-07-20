import unittest

from app.services.keyword_service import KeywordService


class KeywordServiceTest(unittest.TestCase):
    def test_extract_keywords_from_text_returns_keywords(self):
        abstract = (
            "Transformer-based language models improve retrieval augmented generation "
            "by combining dense vector search with careful prompt engineering."
        )

        keywords = KeywordService.extract_keywords_from_text(abstract, top_n=5)

        self.assertGreaterEqual(len(keywords), 1)
        self.assertTrue(all(isinstance(keyword, str) and keyword for keyword in keywords))


if __name__ == "__main__":
    unittest.main()
