import { Box, Button, Chip, Typography } from "@mui/material";
import { BookmarkPlus, Eye } from "lucide-react";
import { motion } from "framer-motion";
import { useNavigate } from "react-router-dom";

const TrendingPaperCard = ({ paper }) => {
  const navigate = useNavigate();

  const handleViewDetails = () => {
    navigate(`/papers/${paper.id}`);
  };

  return (
    <motion.div
      whileHover={{ y: -4, scale: 1.01 }}
      transition={{ type: "spring", stiffness: 220, damping: 20 }}
    >
      <Box
        sx={{
          minWidth: { xs: 280, sm: 320 },
          border: "1px solid #E5E7EB",
          borderRadius: "16px",
          backgroundColor: "#fff",
          p: 2.5,
          boxShadow: "0 10px 28px rgba(15,23,42,0.05)",
          display: "flex",
          flexDirection: "column",
          gap: 1.5,
          height: "100%",
          transition: "all 0.2s ease",
        }}
      >
        <Box
          sx={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "flex-start",
            gap: 1,
          }}
        >
          <Chip
            label={paper.domain}
            size="small"
            sx={{
              bgcolor: "rgba(37,99,235,0.08)",
              color: "#2563EB",
              fontWeight: 600,
              borderRadius: "999px",
            }}
          />
          <Typography
            sx={{ color: "#6B7280", fontSize: "0.85rem", fontWeight: 600 }}
          >
            {paper.year}
          </Typography>
        </Box>

        <Box>
          <Typography
            sx={{
              color: "#111827",
              fontWeight: 700,
              fontSize: "1rem",
              lineHeight: 1.5,
              mb: 0.75,
            }}
          >
            {paper.title}
          </Typography>
          <Typography
            sx={{ color: "#6B7280", fontSize: "0.9rem", lineHeight: 1.6 }}
          >
            {paper.authors}
          </Typography>
        </Box>

        <Box
          sx={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
            color: "#6B7280",
            fontSize: "0.9rem",
          }}
        >
          <Typography sx={{ fontWeight: 600 }}>
            {paper.citations ?? 0} citations
          </Typography>
          <Typography>{paper.domain || "Research"}</Typography>
        </Box>

        <Typography
          sx={{
            color: "#4B5563",
            fontSize: "0.92rem",
            lineHeight: 1.7,
            display: "-webkit-box",
            WebkitLineClamp: 2,
            WebkitBoxOrient: "vertical",
            overflow: "hidden",
          }}
        >
          {paper.abstract || "No abstract available at the moment."}
        </Typography>

        <Box sx={{ display: "flex", gap: 1, mt: "auto", pt: 0.5 }}>
          <Button
            variant="contained"
            startIcon={<Eye size={16} />}
            onClick={handleViewDetails}
            sx={{ flex: 1, borderRadius: "12px" }}
          >
            View Details
          </Button>
          <Button
            variant="outlined"
            startIcon={<BookmarkPlus size={16} />}
            sx={{ borderRadius: "12px" }}
          >
            Save
          </Button>
        </Box>
      </Box>
    </motion.div>
  );
};

export default TrendingPaperCard;
