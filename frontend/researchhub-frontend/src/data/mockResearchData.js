export const trendingPapers = [
  {
    id: 1,
    title: "Adaptive Reasoning Agents for Scientific Discovery",
    authors: "M. Chen, A. Patel, S. Rivera",
    year: 2025,
    citations: 184,
    domain: "Artificial Intelligence",
    abstract:
      "A framework for combining retrieval-augmented reasoning with domain-specific multimodal evidence to accelerate hypothesis generation.",
  },
  {
    id: 2,
    title: "Federated Learning for Secure Clinical Research Collaboration",
    authors: "L. Kim, D. Singh, R. Alvarez",
    year: 2024,
    citations: 152,
    domain: "Machine Learning",
    abstract:
      "An end-to-end privacy-preserving system for training predictive models across distributed medical institutions without centralizing data.",
  },
  {
    id: 3,
    title: "Zero-Trust Architectures for Next-Generation Cloud Infrastructure",
    authors: "J. Foster, N. Patel, E. Wong",
    year: 2023,
    citations: 221,
    domain: "Cyber Security",
    abstract:
      "A practical blueprint for deploying continuous identity verification and policy-aware segmentation in modern cloud ecosystems.",
  },
  {
    id: 4,
    title: "Vision-Language Models for Scientific Figure Understanding",
    authors: "A. Hassan, K. Moore, P. Sundar",
    year: 2025,
    citations: 129,
    domain: "Computer Vision",
    abstract:
      "A multimodal approach that links chart semantics and paper context to improve structured extraction from visual research artifacts.",
  },
  {
    id: 5,
    title: "Energy-Efficient Edge Inference for IoT Sensing Systems",
    authors: "T. Brooks, H. Li, M. Ortega",
    year: 2024,
    citations: 97,
    domain: "Internet of Things",
    abstract:
      "Optimization techniques for reducing model latency and power draw while retaining accuracy in constrained edge deployments.",
  },
];

export const domainTabs = [
  "Artificial Intelligence",
  "Machine Learning",
  "Cyber Security",
  "Cloud Computing",
  "Computer Vision",
  "Natural Language Processing",
  "Software Engineering",
  "Internet of Things",
];

export const domainPapers = {
  "Artificial Intelligence": [
    {
      title: "Neuro-Symbolic Planning for Long-Horizon Research Agents",
      authors: "C. Davis, P. Smith",
      year: 2025,
      citations: 88,
      abstract:
        "A hybrid planning strategy that grounds agent behavior in symbolic constraints while maintaining flexible language-based reasoning.",
    },
    {
      title: "Benchmarking Causal Reasoning in Scientific LLMs",
      authors: "I. Rahman, V. Osei",
      year: 2024,
      citations: 74,
      abstract:
        "A structured evaluation suite for measuring whether language models can identify and intervene on causal mechanisms in scientific tasks.",
    },
  ],
  "Machine Learning": [
    {
      title:
        "Self-Supervised Representation Learning for Rare Biomedical Signals",
      authors: "N. Ahmed, R. Kline",
      year: 2024,
      citations: 113,
      abstract:
        "A contrastive learning recipe for extracting robust features from limited and noisy biological datasets.",
    },
    {
      title: "Sparse Mixture-of-Experts for Efficient Scientific Models",
      authors: "S. Rao, B. Nguyen",
      year: 2023,
      citations: 65,
      abstract:
        "An architecture that routes computation dynamically to improve scale and reduce training cost for research workloads.",
    },
  ],
  "Cyber Security": [
    {
      title: "Automated Threat Intelligence Graph Construction",
      authors: "L. Thompson, M. Flores",
      year: 2025,
      citations: 91,
      abstract:
        "A graph-based method for correlating indicators of compromise across evolving attack campaigns and reporting chains.",
    },
    {
      title: "Behavioral Biometrics for Continuous Authentication",
      authors: "D. Park, E. Zou",
      year: 2024,
      citations: 79,
      abstract:
        "A privacy-aware authentication framework that evaluates interaction patterns to detect account takeover behavior.",
    },
  ],
  "Cloud Computing": [
    {
      title: "Elastic Resource Orchestration for Multi-Cloud AI Workloads",
      authors: "M. Santos, K. Yoon",
      year: 2024,
      citations: 102,
      abstract:
        "A scheduling system that balances latency, cost, and reliability across heterogeneous cloud environments.",
    },
    {
      title: "Serverless Data Pipelines for Research Reproducibility",
      authors: "J. Lee, H. Wang",
      year: 2023,
      citations: 63,
      abstract:
        "An infrastructure pattern for building portable and reproducible experimental workflows with minimal operational overhead.",
    },
  ],
  "Computer Vision": [
    {
      title: "Open-World Object Detection for Scientific Instruments",
      authors: "A. Gomez, P. Reddy",
      year: 2025,
      citations: 84,
      abstract:
        "A detection system that identifies novel instrument components beyond a fixed label set in laboratory settings.",
    },
    {
      title: "3D Reconstruction from Sparse Microscopy Images",
      authors: "Q. Zhang, R. Bhatia",
      year: 2024,
      citations: 71,
      abstract:
        "A volumetric reconstruction approach tailored to low-sample microscopy stacks with strong priors and geometry consistency.",
    },
  ],
  "Natural Language Processing": [
    {
      title: "Scientific Summarization with Evidence-grounded Retrieval",
      authors: "E. Morales, T. Chen",
      year: 2025,
      citations: 96,
      abstract:
        "A summarization pipeline that anchors generated insights in cited evidence and preserves factual nuance from source papers.",
    },
    {
      title: "Cross-Domain Paper Classification for Research Discovery",
      authors: "Y. Lin, S. James",
      year: 2024,
      citations: 68,
      abstract:
        "A robust classifier for mapping academic papers into emerging research domains with minimal supervision.",
    },
  ],
  "Software Engineering": [
    {
      title: "Verification-First Development for AI Systems",
      authors: "C. Murphy, B. Shah",
      year: 2024,
      citations: 87,
      abstract:
        "A disciplined engineering approach that combines automated testing, formal checks, and release gating for safe AI deployment.",
    },
    {
      title: "Developer Tooling for Reproducible Experiment Tracking",
      authors: "H. Singh, V. Cruz",
      year: 2023,
      citations: 58,
      abstract:
        "A tooling stack for logging experiment configurations, outputs, and review state across collaborative research teams.",
    },
  ],
  "Internet of Things": [
    {
      title: "Adaptive Sensing for Long-Lived Environmental Monitoring",
      authors: "T. Osborne, Y. Chen",
      year: 2025,
      citations: 77,
      abstract:
        "A sensing strategy that dynamically adjusts sampling rates to extend operation while preserving signal fidelity in field deployments.",
    },
    {
      title: "Secure Mesh Networking for Remote Research Sites",
      authors: "R. Silva, A. Morgan",
      year: 2024,
      citations: 62,
      abstract:
        "A resilient networking stack designed for sparse connectivity and intermittent power in distributed research environments.",
    },
  ],
};

export const quickAccessItems = [
  {
    title: "Search Papers",
    description:
      "Discover foundational and emerging work with semantic search.",
    icon: "Search",
    href: "/home",
  },
  {
    title: "AI Novelty Checker",
    description: "Compare your idea against prior art in seconds.",
    icon: "Sparkles",
    href: "/home",
  },
  {
    title: "Saved Papers",
    description: "Keep important studies organized in your personal library.",
    icon: "Bookmark",
    href: "/home",
  },
  {
    title: "Research Profile",
    description: "Track your reading history and research interests.",
    icon: "UserRound",
    href: "/home",
  },
];
