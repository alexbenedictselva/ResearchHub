import { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import {
  Box,
  Button,
  CircularProgress,
  Grid,
  Stack,
  TextField,
  Typography,
} from "@mui/material";
import PaperCard from "../components/home/PaperCard";

const normalizePaper = (paper, fallbackId) => ({
  id: paper.paperId || paper.id || fallbackId,
  title: paper.title || "Untitled paper",
  authors: (paper.authors || []).map((a) => a.name || a).join(", "),
  year: paper.year || "N/A",
  citations: paper.citationCount || paper.citations || 0,
  domain: paper.domain || "Research",
  abstract: paper.abstractText || paper.abstract || "",
});

const ComparePapersPage = () => {
  const navigate = useNavigate();
  const [user, setUser] = useState(null);
  const [userAbstract, setUserAbstract] = useState("");
  const [loading, setLoading] = useState(false);
  const [results, setResults] = useState([]);
  const [error, setError] = useState("");
  const [selected, setSelected] = useState([]);

  useEffect(() => {
    const auth = localStorage.getItem("auth");
    if (!auth) {
      navigate("/login");
      return;
    }

    const parsed = JSON.parse(auth);
    setUser(parsed);
  }, [navigate]);

  const handleSearch = async () => {
    if (!userAbstract.trim()) return;
    setLoading(true);
    setError("");
    try {
      const response = await axios.post(
        "http://localhost:8080/api/papers/search",
        { userAbstract: userAbstract },
        {
          headers: {
            Authorization: `Bearer ${user?.accessToken}`,
          },
        },
      );

      const payload = Array.isArray(response.data)
        ? response.data
        : response.data?.papers || [];
      const normalized = payload.map((p, i) => normalizePaper(p, i + 1));
      setResults(normalized);
    } catch (err) {
      setError("Unable to perform search right now.");
      setResults([]);
    } finally {
      setLoading(false);
    }
  };

  const handleCompareToggle = (paper) => {
    setSelected((prev) =>
      prev.includes(paper.id)
        ? prev.filter((id) => id !== paper.id)
        : [...prev, paper.id],
    );
  };

  return (
    <Box
      sx={{
        minHeight: "100vh",
        backgroundColor: "#F8FAFC",
        py: { xs: 2, md: 4 },
      }}
    >
      <Box sx={{ maxWidth: 1100, mx: "auto", px: { xs: 2, md: 3 } }}>
        <Box sx={{ mb: 3 }}>
          <Typography variant="h5" sx={{ mb: 1 }}>
            Compare Papers
          </Typography>
          <Typography sx={{ color: "#6B7280" }}>
            Paste your abstract below to find similar or relevant papers to
            compare.
          </Typography>
        </Box>

        <Box sx={{ display: "flex", gap: 2, mb: 3, alignItems: "flex-start" }}>
          <TextField
            multiline
            minRows={6}
            placeholder="Paste abstract here..."
            value={userAbstract}
            onChange={(e) => setUserAbstract(e.target.value)}
            fullWidth
          />
          <Box sx={{ display: "flex", flexDirection: "column", gap: 1 }}>
            <Button
              variant="contained"
              onClick={handleSearch}
              disabled={loading || !userAbstract.trim()}
            >
              Search
            </Button>
            <Button
              variant="outlined"
              onClick={() => {
                setUserAbstract("");
                setResults([]);
                setSelected([]);
              }}
            >
              Clear
            </Button>
          </Box>
        </Box>

        {loading ? (
          <Stack direction="row" spacing={1} alignItems="center">
            <CircularProgress size={20} />
            <Typography>Searching papers...</Typography>
          </Stack>
        ) : error ? (
          <Typography color="error">{error}</Typography>
        ) : (
          <Grid container spacing={2}>
            {results.map((paper) => (
              <Grid item xs={4} sm={4} md={4} key={paper.id}>
                <PaperCard paper={paper} onCompare={handleCompareToggle} />
              </Grid>
            ))}
          </Grid>
        )}

        <Box
          sx={{ mt: 3, display: "flex", justifyContent: "flex-end", gap: 2 }}
        >
          <Button
            variant="contained"
            disabled={selected.length < 2}
            onClick={() =>
              alert(`Compare selected paper ids: ${selected.join(",")}`)
            }
          >
            Compare Selected ({selected.length})
          </Button>
        </Box>
      </Box>
    </Box>
  );
};

export default ComparePapersPage;
