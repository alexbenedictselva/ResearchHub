import re
from typing import Optional
from sklearn.feature_extraction.text import TfidfVectorizer

from app.models.request_models import Paper
from app.models.response_models import KeywordNovelty


# Common academic stopwords to suppress from keyword extraction
_STOPWORDS = (
    "the a an and or but in on at to for of with is are was were be been "
    "being have has had do does did will would could should may might shall "
    "this that these those it its we our they their he she his her i my you "
    "your from by about into through during before after above below between "
    "each other such than then there when where which who whom how all any "
    "both few more most other some such no nor not only own same so than too "
    "very just because as until while although though even also however "
    "therefore thus hence moreover furthermore nevertheless nonetheless "
    "study research paper propose proposed method approach model results "
    "show shows showed shown using used use based can also paper work"
).split()


class KeywordService:
    """
    Responsible for two things:
      1. Extracting the most important keywords from the user's abstract
         using TF-IDF scoring against the full paper corpus as background.
      2. Comparing those keywords against the retrieved papers to estimate
         how novel the user's terminology is.
    """

    @staticmethod
    def extract_keywords_from_text(user_abstract: str, top_n: int = 10) -> list[str]:
        """
        Extracts the most important keywords from a single abstract text.
        This is a lightweight version suitable for the new route.
        """
        if not user_abstract or not user_abstract.strip():
            return []

        text = re.sub(r"[^a-zA-Z\s]", " ", user_abstract.lower())
        tokens = [token for token in text.split() if token not in _STOPWORDS and len(token) > 2]

        if not tokens:
            return []

        vectorizer = TfidfVectorizer(
            stop_words=_STOPWORDS,
            ngram_range=(1, 2),
            max_features=200,
            token_pattern=r"(?u)\b[a-zA-Z][a-zA-Z\-]{2,}\b"
        )

        tfidf_matrix = vectorizer.fit_transform([" ".join(tokens)])
        feature_names = vectorizer.get_feature_names_out()
        scores = tfidf_matrix.toarray().flatten()

        ranked = sorted(zip(feature_names, scores), key=lambda item: item[1], reverse=True)
        return [term for term, score in ranked if score > 0][:top_n]

    @staticmethod
    def extract_keywords(user_abstract: str, papers: list[Paper], top_n: int = 15) -> list[str]:
        """
        Extracts the top N keywords from the user abstract by fitting a
        TF-IDF model on the full corpus (user abstract + all paper abstracts).
        The user abstract's TF-IDF scores are then ranked to find terms
        that are important in the user's text relative to the corpus.

        Args:
            user_abstract: The user's research abstract.
            papers:         Retrieved papers providing the background corpus.
            top_n:          Number of top keywords to return.

        Returns:
            List of keyword strings sorted by importance descending.
        """
        corpus = [user_abstract] + [p.abstract for p in papers if p.abstract]

        vectorizer = TfidfVectorizer(
            stop_words=_STOPWORDS,
            ngram_range=(1, 2),   # unigrams and bigrams
            max_features=500,
            token_pattern=r"(?u)\b[a-zA-Z][a-zA-Z\-]{2,}\b"  # skip numbers/symbols
        )

        tfidf_matrix = vectorizer.fit_transform(corpus)
        feature_names = vectorizer.get_feature_names_out()

        # Row 0 is the user abstract — get its TF-IDF scores
        user_scores = tfidf_matrix[0].toarray().flatten()

        # Pair each term with its score and sort descending
        scored_terms = sorted(
            zip(feature_names, user_scores),
            key=lambda x: x[1],
            reverse=True
        )

        return [term for term, score in scored_terms if score > 0][:top_n]

    @staticmethod
    def compute_keyword_novelty(
        user_keywords: list[str],
        papers: list[Paper]
    ) -> KeywordNovelty:
        """
        Compares the user's keywords against the full text of all retrieved
        papers to determine which keywords are novel (not present in any paper)
        and which overlap with existing literature.

        Args:
            user_keywords: Keywords extracted by extract_keywords().
            papers:         Retrieved papers to compare against.

        Returns:
            KeywordNovelty model with novelKeywords, overlapKeywords, and score.
        """
        # Build a single lowercased corpus string from all paper text
        paper_corpus = " ".join(
            f"{p.title} {p.abstract}" for p in papers if p.abstract
        ).lower()

        novel = []
        overlap = []

        for keyword in user_keywords:
            # Normalize keyword for matching
            normalized = keyword.lower().strip()
            if re.search(r'\b' + re.escape(normalized) + r'\b', paper_corpus):
                overlap.append(keyword)
            else:
                novel.append(keyword)

        total = len(user_keywords)
        novelty_score = round(len(novel) / total, 4) if total > 0 else 0.0

        return KeywordNovelty(
            userKeywords=user_keywords,
            novelKeywords=novel,
            overlapKeywords=overlap,
            keywordNoveltyScore=novelty_score
        )
