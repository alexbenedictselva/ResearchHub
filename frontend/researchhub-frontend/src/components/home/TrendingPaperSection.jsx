import { Box, CircularProgress, Typography } from "@mui/material";
import TrendingPaperCard from "./TrendingPaperCard";

const TrendingPaperSection = ({ papers, loading, error }) => {
  return (
    <Box>
      <Box
        sx={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          mb: 2.5,
          gap: 2,
          flexWrap: "wrap",
        }}
      >
        <Box>
          <Typography variant="h5" sx={{ letterSpacing: "-0.02em", mb: 0.5 }}>
            Top 5 trending papers
          </Typography>
          <Typography sx={{ color: "#6B7280" }}>
            Curated discoveries with strong momentum across the research
            community.
          </Typography>
        </Box>
      </Box>

      {loading ? (
        <Box
          sx={{
            display: "flex",
            alignItems: "center",
            gap: 1.5,
            color: "#6B7280",
          }}
        >
          <CircularProgress size={20} />
          <Typography>Loading trending papers...</Typography>
        </Box>
      ) : error ? (
        <Typography sx={{ color: "#DC2626" }}>{error}</Typography>
      ) : (
        <Box
          sx={{
            display: "flex",
            gap: 2,
            overflowX: "auto",
            pb: 1.2,
            scrollSnapType: "x proximity",
            "&::-webkit-scrollbar": { display: "none" },
          }}
        >
          {papers.map((paper) => (
            <Box
              key={paper.id}
              sx={{
                scrollSnapAlign: "start",
                flex: { xs: "0 0 280px", sm: "0 0 320px" },
              }}
            >
              <TrendingPaperCard paper={paper} />
            </Box>
          ))}
        </Box>
      )}
    </Box>
  );
};

export default TrendingPaperSection;
