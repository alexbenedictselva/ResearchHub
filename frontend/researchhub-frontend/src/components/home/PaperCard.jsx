import { Box, Button, Typography } from "@mui/material";
import { ArrowRight } from "lucide-react";
import { motion } from "framer-motion";

const PaperCard = ({ paper }) => {
  return (
    <motion.div
      whileHover={{ y: -3, scale: 1.01 }}
      transition={{ type: "spring", stiffness: 220, damping: 20 }}
    >
      <Box
        sx={{
          border: "1px solid #E5E7EB",
          borderRadius: "16px",
          backgroundColor: "#fff",
          p: 2.25,
          boxShadow: "0 10px 28px rgba(15,23,42,0.05)",
          display: "flex",
          flexDirection: "column",
          gap: 1.25,
          height: "100%",
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
          <Typography
            sx={{
              color: "#111827",
              fontWeight: 700,
              fontSize: "0.98rem",
              lineHeight: 1.5,
            }}
          >
            {paper.title}
          </Typography>
          <Typography
            sx={{
              color: "#6B7280",
              fontSize: "0.84rem",
              fontWeight: 600,
              whiteSpace: "nowrap",
            }}
          >
            {paper.year}
          </Typography>
        </Box>

        <Typography
          sx={{ color: "#6B7280", fontSize: "0.9rem", lineHeight: 1.6 }}
        >
          {paper.authors}
        </Typography>

        <Box
          sx={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
            color: "#6B7280",
            fontSize: "0.9rem",
          }}
        >
          <Typography>{paper.citations} citations</Typography>
          <Typography sx={{ fontWeight: 600 }}>{paper.year}</Typography>
        </Box>

        <Typography
          sx={{
            color: "#4B5563",
            fontSize: "0.92rem",
            lineHeight: 1.7,
            display: "-webkit-box",
            WebkitLineClamp: 3,
            WebkitBoxOrient: "vertical",
            overflow: "hidden",
          }}
        >
          {paper.abstract}
        </Typography>

        <Button
          variant="outlined"
          endIcon={<ArrowRight size={16} />}
          sx={{ mt: "auto", borderRadius: "12px", alignSelf: "flex-start" }}
        >
          View Details
        </Button>
      </Box>
    </motion.div>
  );
};

export default PaperCard;
