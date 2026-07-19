import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";
import {
  Box,
  Chip,
  CircularProgress,
  Divider,
  Grid,
  Link,
  Stack,
  Typography,
} from "@mui/material";
import { ArrowLeft, ExternalLink, BookOpen, Quote, Users } from "lucide-react";

const PaperDetailsPage = () => {
  const navigate = useNavigate();
  const { paperId } = useParams();
  const [paper, setPaper] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const auth = localStorage.getItem("auth");
    if (!auth) {
      navigate("/login");
      return;
    }

    const parsedAuth = JSON.parse(auth);

    const fetchPaperDetails = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/api/papers/${paperId}/details`,
          {
            headers: {
              Authorization: `Bearer ${parsedAuth.accessToken}`,
            },
          },
        );

        setPaper(response.data);
      } catch (err) {
        setError("Unable to load paper details right now.");
      } finally {
        setLoading(false);
      }
    };

    fetchPaperDetails();
  }, [navigate, paperId]);

  if (loading) {
    return (
      <Box
        sx={{
          minHeight: "100vh",
          backgroundColor: "#F8FAFC",
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
        }}
      >
        <Stack direction="row" spacing={1.5} alignItems="center">
          <CircularProgress size={24} />
          <Typography color="#6B7280">Loading paper details...</Typography>
        </Stack>
      </Box>
    );
  }

  if (error || !paper) {
    return (
      <Box
        sx={{
          minHeight: "100vh",
          backgroundColor: "#F8FAFC",
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          px: 3,
        }}
      >
        <Box
          sx={{
            backgroundColor: "#fff",
            border: "1px solid #E5E7EB",
            borderRadius: "20px",
            p: 4,
            maxWidth: 640,
            width: "100%",
          }}
        >
          <Typography variant="h5" sx={{ mb: 1 }}>
            Paper unavailable
          </Typography>
          <Typography color="#6B7280">
            {error || "No details were returned for this paper."}
          </Typography>
        </Box>
      </Box>
    );
  }

  const references = (paper.references || []).slice(0, 5);

  return (
    <Box
      sx={{
        minHeight: "100vh",
        backgroundColor: "#F8FAFC",
        py: { xs: 2, md: 4 },
      }}
    >
      <Box sx={{ maxWidth: 1100, mx: "auto", px: { xs: 2, md: 3 } }}>
        <Box sx={{ display: "flex", alignItems: "center", gap: 1, mb: 3 }}>
          <Box
            component="button"
            onClick={() => navigate("/home")}
            sx={{
              border: "none",
              background: "transparent",
              cursor: "pointer",
              display: "flex",
              alignItems: "center",
              gap: 1,
              color: "#2563EB",
              fontWeight: 600,
              p: 0,
            }}
          >
            <ArrowLeft size={18} />
            Back to dashboard
          </Box>
        </Box>

        <Box
          sx={{
            backgroundColor: "#fff",
            border: "1px solid #E5E7EB",
            borderRadius: "24px",
            boxShadow: "0 18px 40px rgba(15,23,42,0.05)",
            p: { xs: 3, md: 4 },
          }}
        >
          <Stack spacing={2.5}>
            <Box>
              <Typography
                variant="h4"
                sx={{
                  fontSize: { xs: "1.6rem", md: "2rem" },
                  letterSpacing: "-0.02em",
                  mb: 1,
                }}
              >
                {paper.title}
              </Typography>
              <Typography
                color="#6B7280"
                sx={{ fontSize: "1rem", lineHeight: 1.7 }}
              >
                {paper.abstractText ||
                  "No abstract is available for this paper."}
              </Typography>
            </Box>

            <Stack
              direction={{ xs: "column", md: "row" }}
              spacing={2}
              flexWrap="wrap"
            >
              <Chip
                label={`Year: ${paper.year || "N/A"}`}
                sx={{
                  backgroundColor: "#F8FAFC",
                  color: "#111827",
                  border: "1px solid #E5E7EB",
                }}
              />
              <Chip
                label={`Citations: ${paper.citationCount || 0}`}
                sx={{
                  backgroundColor: "#F8FAFC",
                  color: "#111827",
                  border: "1px solid #E5E7EB",
                }}
              />
              <Chip
                label={`References: ${paper.referenceCount || 0}`}
                sx={{
                  backgroundColor: "#F8FAFC",
                  color: "#111827",
                  border: "1px solid #E5E7EB",
                }}
              />
              {paper.doi ? (
                <Chip
                  label={`DOI: ${paper.doi}`}
                  sx={{
                    backgroundColor: "#F8FAFC",
                    color: "#111827",
                    border: "1px solid #E5E7EB",
                  }}
                />
              ) : null}
            </Stack>

            <Divider />

            <Grid container spacing={3}>
              <Grid item xs={12} lg={7}>
                <Box
                  sx={{
                    border: "1px solid #E5E7EB",
                    borderRadius: "16px",
                    p: 2.5,
                    backgroundColor: "#F8FAFC",
                  }}
                >
                  <Typography
                    variant="h6"
                    sx={{
                      mb: 1.2,
                      display: "flex",
                      alignItems: "center",
                      gap: 1,
                    }}
                  >
                    <Users size={18} />
                    Authors
                  </Typography>
                  <Box sx={{ display: "flex", flexWrap: "wrap", gap: 1 }}>
                    {(paper.authors || []).map((author) => (
                      <Chip
                        key={author.authorId || author.name}
                        label={author.name}
                        sx={{
                          backgroundColor: "rgba(37,99,235,0.08)",
                          color: "#2563EB",
                          maxWidth: "100%",
                          whiteSpace: "normal",
                          height: "auto",
                          py: 0.5,
                        }}
                      />
                    ))}
                  </Box>
                </Box>
              </Grid>

              <Grid item xs={12} lg={5}>
                <Box
                  sx={{
                    border: "1px solid #E5E7EB",
                    borderRadius: "16px",
                    p: 2.5,
                    backgroundColor: "#F8FAFC",
                  }}
                >
                  <Typography
                    variant="h6"
                    sx={{
                      mb: 1.2,
                      display: "flex",
                      alignItems: "center",
                      gap: 1,
                    }}
                  >
                    <BookOpen size={18} />
                    Publication details
                  </Typography>
                  <Typography color="#6B7280" sx={{ lineHeight: 1.8 }}>
                    {paper.venue
                      ? `Venue: ${paper.venue}`
                      : "Venue not provided."}
                    <br />
                    {paper.journal
                      ? `Journal: ${paper.journal}`
                      : "Journal not provided."}
                    <br />
                    {paper.publicationDate
                      ? `Publication date: ${paper.publicationDate}`
                      : "Publication date not provided."}
                  </Typography>
                </Box>
              </Grid>
            </Grid>

            <Box
              sx={{
                border: "1px solid #E5E7EB",
                borderRadius: "16px",
                p: 2.5,
                backgroundColor: "#F8FAFC",
              }}
            >
              <Typography
                variant="h6"
                sx={{ mb: 1.2, display: "flex", alignItems: "center", gap: 1 }}
              >
                <Quote size={18} />
                References
              </Typography>
              {references.length > 0 ? (
                <Stack spacing={1.25}>
                  {references.map((reference) => (
                    <Box
                      key={reference.paperId}
                      sx={{
                        border: "1px solid #E5E7EB",
                        borderRadius: "12px",
                        px: 1.5,
                        py: 1.1,
                        backgroundColor: "#fff",
                      }}
                    >
                      <Typography sx={{ color: "#111827", fontWeight: 600 }}>
                        {reference.title}
                      </Typography>
                    </Box>
                  ))}
                </Stack>
              ) : (
                <Typography color="#6B7280">
                  No references were returned for this paper.
                </Typography>
              )}
            </Box>

            {paper.url ? (
              <Box>
                <Link
                  href={paper.url}
                  target="_blank"
                  rel="noreferrer"
                  sx={{
                    display: "inline-flex",
                    alignItems: "center",
                    gap: 1,
                    color: "#2563EB",
                    fontWeight: 600,
                  }}
                >
                  Open source link <ExternalLink size={16} />
                </Link>
              </Box>
            ) : null}
          </Stack>
        </Box>
      </Box>
    </Box>
  );
};

export default PaperDetailsPage;
