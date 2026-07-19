import { Box, Typography } from "@mui/material";
import {
  ArrowRight,
  Bookmark,
  Search,
  Sparkles,
  UserRound,
} from "lucide-react";
import { motion } from "framer-motion";

const iconMap = {
  Search,
  Sparkles,
  Bookmark,
  UserRound,
};

const QuickAccessCard = ({ item }) => {
  const Icon = iconMap[item.icon];

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
          alignItems: "flex-start",
          justifyContent: "space-between",
          gap: 2,
          height: "100%",
          cursor: "pointer",
        }}
      >
        <Box sx={{ display: "flex", alignItems: "flex-start", gap: 1.25 }}>
          <Box
            sx={{
              width: 40,
              height: 40,
              borderRadius: "12px",
              backgroundColor: "rgba(37,99,235,0.08)",
              color: "#2563EB",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              flexShrink: 0,
            }}
          >
            <Icon size={18} />
          </Box>
          <Box>
            <Typography sx={{ color: "#111827", fontWeight: 700, mb: 0.5 }}>
              {item.title}
            </Typography>
            <Typography
              sx={{ color: "#6B7280", fontSize: "0.9rem", lineHeight: 1.6 }}
            >
              {item.description}
            </Typography>
          </Box>
        </Box>
        <ArrowRight size={18} color="#6B7280" />
      </Box>
    </motion.div>
  );
};

export default QuickAccessCard;
