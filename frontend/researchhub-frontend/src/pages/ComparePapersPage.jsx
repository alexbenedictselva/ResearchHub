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
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Divider,
  List,
  ListItem,
  ListItemText,
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

  const [dialogOpen, setDialogOpen] = useState(false);
  const [dialogPaper, setDialogPaper] = useState(null);
  const [dialogLoading, setDialogLoading] = useState(false);
  const [dialogResult, setDialogResult] = useState(null);
  const [dialogError, setDialogError] = useState("");

  const handleOpenCompareDialog = async (paper) => {
    setDialogPaper(paper);
    setDialogOpen(true);
    setDialogLoading(true);
    setDialogResult(null);
    setDialogError("");

    try {
      const body = {
        userAbstract: userAbstract,
        papers: [{ paperId: paper.id }],
      };

      const response = await axios.post(
        "http://localhost:8080/api/ai/novelty-check",
        body,
        {
          headers: {
            Authorization: `Bearer ${user?.accessToken}`,
          },
        },
      );

      setDialogResult(response.data);
    } catch (err) {
      setDialogError("Unable to run novelty check right now.");
    } finally {
      setDialogLoading(false);
    }
  };

  const handleCloseDialog = () => {
    setDialogOpen(false);
    setDialogPaper(null);
    setDialogResult(null);
    setDialogError("");
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
              <Grid item xs={12} sm={6} md={4} key={paper.id}>
                <PaperCard
                  paper={paper}
                  onCompare={(p) => {
                    handleCompareToggle(p);
                    handleOpenCompareDialog(p);
                  }}
                />
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

      <Dialog open={dialogOpen} onClose={handleCloseDialog} maxWidth="md" fullWidth>
        <DialogTitle>
          {`User Abstract vs ${dialogPaper ? dialogPaper.title : "Paper"}`}
        </DialogTitle>
        <DialogContent dividers>
          <Box sx={{ display: "flex", gap: 3, flexDirection: { xs: "column", md: "row" } }}>
            <Box sx={{ flex: 1 }}>
              <Typography variant="subtitle1" sx={{ fontWeight: 700, mb: 1 }}>
                Your abstract
              </Typography>
              <Box sx={{ backgroundColor: "#fff", p: 2, borderRadius: 1, border: "1px solid #E5E7EB" }}>
                <Typography sx={{ whiteSpace: "pre-wrap", color: "#374151" }}>
                  {userAbstract || "(no abstract provided)"}
                </Typography>
              </Box>
            </Box>

            <Box sx={{ flex: 1 }}>
              <Typography variant="subtitle1" sx={{ fontWeight: 700, mb: 1 }}>
                {dialogPaper ? dialogPaper.title : "Paper"}
              </Typography>
              <Box sx={{ mb: 2 }}>
                <Typography sx={{ color: "#6B7280" }}>{dialogPaper?.authors}</Typography>
                <Typography sx={{ color: "#6B7280" }}>{dialogPaper?.year}</Typography>
              </Box>

              {dialogLoading ? (
                <Stack direction="row" spacing={1} alignItems="center">
                  <CircularProgress size={20} />
                  <Typography>Running novelty check...</Typography>
                </Stack>
              ) : dialogError ? (
                <Typography color="error">{dialogError}</Typography>
              ) : dialogResult ? (
                <Box sx={{ display: "flex", flexDirection: "column", gap: 2 }}>
                  <Box>
                    <Typography sx={{ fontWeight: 800, fontSize: "1.5rem" }}>
                      Innovation Score: {dialogResult.innovationScore ?? "-"}
                    </Typography>
                  </Box>

                  <Divider />

                  <Box>
                    <Typography sx={{ fontWeight: 700 }}>Semantic similarity</Typography>
                    <List dense>
                      <ListItem>
                        <ListItemText primary={`Highest: ${dialogResult.semanticSimilarity?.highest ?? "-"}`} />
                      </ListItem>
                      <ListItem>
                        <ListItemText primary={`Average: ${dialogResult.semanticSimilarity?.average ?? "-"}`} />
                      </ListItem>
                      <ListItem>
                        <ListItemText primary={`Lowest: ${dialogResult.semanticSimilarity?.lowest ?? "-"}`} />
                      </ListItem>
                    </List>
                  </Box>

                  <Divider />

                  <Box>
                    <Typography sx={{ fontWeight: 700 }}>Top matches</Typography>
                    <List>
                      {(dialogResult.topMatches || []).map((m) => (
                        <ListItem key={m.paperId}>
                          <ListItemText primary={m.title} secondary={`Similarity: ${m.similarity} • Year: ${m.year} • Citations: ${m.citationCount}`} />
                        </ListItem>
                      ))}
                    </List>
                  </Box>

                  <Divider />

                  <Box>
                    <Typography sx={{ fontWeight: 700 }}>Keyword novelty</Typography>
                    <Typography sx={{ color: "#6B7280" }}>Novelty score: {dialogResult.keywordNovelty?.keywordNoveltyScore ?? "-"}</Typography>
                    <Box sx={{ mt: 1 }}>
                      <Typography sx={{ fontWeight: 600 }}>Novel keywords</Typography>
                      <Typography sx={{ color: "#374151", whiteSpace: "pre-wrap" }}>
                        {(dialogResult.keywordNovelty?.novelKeywords || []).join(", ")}
                      </Typography>
                    </Box>
                  </Box>

                  <Divider />

                  <Box>
                    <Typography sx={{ fontWeight: 700 }}>Research maturity</Typography>
                    <Typography>Average year: {dialogResult.researchMaturity?.averageYear ?? "-"}</Typography>
                    <Typography>Average citations: {dialogResult.researchMaturity?.averageCitationCount ?? "-"}</Typography>
                    <Typography>Label: {dialogResult.researchMaturity?.maturityLabel ?? "-"}</Typography>
                  </Box>

                  <Divider />

                  <Box>
                    <Typography sx={{ fontWeight: 700 }}>Report summary</Typography>
                    <Typography sx={{ mt: 1 }}>{dialogResult.report?.summary}</Typography>
                    <Box sx={{ mt: 1 }}>
                      <Typography sx={{ fontWeight: 700 }}>Recommendations</Typography>
                      <List>
                        {(dialogResult.report?.recommendations || []).map((r, i) => (
                          <ListItem key={i}><ListItemText primary={r} /></ListItem>
                        ))}
                      </List>
                    </Box>
                  </Box>
                </Box>
              ) : (
                <Typography sx={{ color: "#6B7280" }}>No result yet.</Typography>
              )}
            </Box>
          </Box>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDialog}>Close</Button>
        </DialogActions>
        </Dialog>
    </Box>
  );
};

export default ComparePapersPage;
