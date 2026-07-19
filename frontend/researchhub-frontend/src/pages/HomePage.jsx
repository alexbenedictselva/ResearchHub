import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { Box, Grid, Stack, Typography } from "@mui/material";
import Navbar from "../components/home/Navbar";
import WelcomeBanner from "../components/home/WelcomeBanner";
import TrendingPaperSection from "../components/home/TrendingPaperSection";
import DomainTabs from "../components/home/DomainTabs";
import PaperCard from "../components/home/PaperCard";
import QuickAccessCard from "../components/home/QuickAccessCard";
import Footer from "../components/home/Footer";
import { quickAccessItems } from "../data/mockResearchData";

const domainTabs = [
  "Artificial Intelligence",
  "Cyber Security",
  "Cloud Computing",
];
const endpointMap = {
  "Artificial Intelligence": "ai",
  "Cyber Security": "cybersecurity",
  "Cloud Computing": "cloud",
};

const normalizePaper = (paper, fallbackId) => ({
  id: paper.paperId || paper.id || fallbackId,
  title: paper.title || "Untitled paper",
  authors: (paper.authors || [])
    .map((author) => author.name || author)
    .join(", "),
  year: paper.year || "N/A",
  citations: paper.citationCount || paper.citations || 0,
  domain: paper.domain || "Research",
  abstract:
    paper.abstractText ||
    paper.abstract ||
    "No abstract available at the moment.",
});

const HomePage = () => {
  const navigate = useNavigate();
  const [user, setUser] = useState(null);
  const [activeDomain, setActiveDomain] = useState(domainTabs[0]);
  const [trendingPapers, setTrendingPapers] = useState([]);
  const [domainPapers, setDomainPapers] = useState([]);
  const [loadingTrending, setLoadingTrending] = useState(true);
  const [loadingDomainPapers, setLoadingDomainPapers] = useState(true);
  const [trendingError, setTrendingError] = useState("");
  const [domainPapersError, setDomainPapersError] = useState("");

  useEffect(() => {
    const auth = localStorage.getItem("auth");
    if (!auth) {
      navigate("/login");
      return;
    }

    const parsedAuth = JSON.parse(auth);
    setUser(parsedAuth);

    const fetchTrendingPapers = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8080/api/papers/trending",
          {
            headers: {
              Authorization: `Bearer ${parsedAuth.accessToken}`,
            },
          },
        );

        const normalizedPapers = (response.data?.mostCitedPapers || [])
          .slice(0, 5)
          .map((paper, index) => normalizePaper(paper, index + 1));

        setTrendingPapers(normalizedPapers);
      } catch (error) {
        setTrendingError("Unable to load trending papers right now.");
      } finally {
        setLoadingTrending(false);
      }
    };

    fetchTrendingPapers();
  }, [navigate]);

  useEffect(() => {
    if (!user?.accessToken) return;

    const fetchDomainPapers = async () => {
      setLoadingDomainPapers(true);
      setDomainPapersError("");

      try {
        const response = await axios.get(
          `http://localhost:8080/api/papers/feed/${endpointMap[activeDomain]}`,
          {
            headers: {
              Authorization: `Bearer ${user.accessToken}`,
            },
          },
        );

        const payload =
          response.data?.papers ||
          response.data?.items ||
          response.data?.data ||
          response.data;
        const sourceList = Array.isArray(payload) ? payload : [];
        const normalizedPapers = sourceList
          .slice(0, 6)
          .map((paper, index) => normalizePaper(paper, index + 1));

        setDomainPapers(normalizedPapers);
      } catch (error) {
        setDomainPapersError(
          "Unable to load papers for this domain right now.",
        );
        setDomainPapers([]);
      } finally {
        setLoadingDomainPapers(false);
      }
    };

    fetchDomainPapers();
  }, [activeDomain, user]);

  if (!user) return null;

  return (
    <Box
      sx={{
        minHeight: "100vh",
        backgroundColor: "#F8FAFC",
        py: { xs: 2, md: 3 },
      }}
    >
      <Box sx={{ maxWidth: 1320, mx: "auto", px: { xs: 2, md: 3 } }}>
        <Navbar userName={user.fullName || "Researcher"} />

        <Stack spacing={3} sx={{ mt: 2 }}>
          <WelcomeBanner
            userName={user.fullName?.split(" ")[0] || "Researcher"}
          />
          <TrendingPaperSection
            papers={trendingPapers}
            loading={loadingTrending}
            error={trendingError}
          />

          <Box
            sx={{
              border: "1px solid #E5E7EB",
              borderRadius: "20px",
              backgroundColor: "#fff",
              p: { xs: 2.25, md: 3 },
              boxShadow: "0 10px 32px rgba(15,23,42,0.05)",
            }}
          >
            <DomainTabs
              tabs={domainTabs}
              activeTab={activeDomain}
              onTabChange={setActiveDomain}
            />

            {loadingDomainPapers ? (
              <Typography sx={{ color: "#6B7280", py: 2 }}>
                Loading papers for this domain...
              </Typography>
            ) : domainPapersError ? (
              <Typography sx={{ color: "#DC2626", py: 2 }}>
                {domainPapersError}
              </Typography>
            ) : domainPapers.length === 0 ? (
              <Typography sx={{ color: "#6B7280", py: 2 }}>
                No papers available for this domain yet.
              </Typography>
            ) : (
              <Grid container spacing={2}>
                {domainPapers.map((paper) => (
                  <Grid item xs={12} md={6} key={`${paper.id}-${paper.title}`}>
                    <PaperCard paper={paper} />
                  </Grid>
                ))}
              </Grid>
            )}
          </Box>

          <Box>
            <Box sx={{ mb: 2.5 }}>
              <Box
                sx={{
                  display: "flex",
                  justifyContent: "space-between",
                  alignItems: "center",
                  gap: 2,
                  flexWrap: "wrap",
                }}
              >
                <Box>
                  <Box
                    component="h5"
                    sx={{
                      m: 0,
                      fontSize: "1.25rem",
                      fontWeight: 700,
                      color: "#111827",
                      letterSpacing: "-0.02em",
                    }}
                  >
                    Quick access
                  </Box>
                  <Box component="p" sx={{ m: 0, color: "#6B7280", mt: 0.5 }}>
                    Jump directly into the core experiences that matter most.
                  </Box>
                </Box>
              </Box>
            </Box>
            <Grid container spacing={2}>
              {quickAccessItems.map((item) => (
                <Grid item xs={12} sm={6} lg={3} key={item.title}>
                  <QuickAccessCard item={item} />
                </Grid>
              ))}
            </Grid>
          </Box>

          <Footer />
        </Stack>
      </Box>
    </Box>
  );
};

export default HomePage;
