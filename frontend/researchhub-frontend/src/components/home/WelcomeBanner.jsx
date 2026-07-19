import { Box, Typography } from "@mui/material";
import { CalendarDays } from "lucide-react";

const WelcomeBanner = ({ userName }) => {
  const today = new Date();
  const formattedDate = today.toLocaleDateString("en", {
    weekday: "long",
    month: "short",
    day: "numeric",
    year: "numeric",
  });

  return (
    <Box
      sx={{
        border: "1px solid #E5E7EB",
        borderRadius: "20px",
        backgroundColor: "#fff",
        p: { xs: 3, md: 4 },
        boxShadow: "0 10px 32px rgba(15,23,42,0.05)",
        display: "flex",
        justifyContent: "space-between",
        alignItems: "flex-start",
        gap: 2,
        flexWrap: "wrap",
      }}
    >
      <Box sx={{ maxWidth: 650 }}>
        <Typography
          sx={{
            color: "#2563EB",
            fontWeight: 700,
            fontSize: "0.8rem",
            textTransform: "uppercase",
            letterSpacing: "0.15em",
            mb: 1,
          }}
        >
          Dashboard
        </Typography>
        <Typography
          variant="h4"
          sx={{
            fontSize: { xs: "1.6rem", md: "2.1rem" },
            letterSpacing: "-0.03em",
            mb: 1,
          }}
        >
          Good morning, {userName}.
        </Typography>
        <Typography
          sx={{ color: "#6B7280", fontSize: "1rem", lineHeight: 1.7 }}
        >
          Discover the latest research and accelerate your innovation with AI.
        </Typography>
      </Box>

      <Box
        sx={{
          display: "flex",
          alignItems: "center",
          gap: 1,
          color: "#6B7280",
          backgroundColor: "#F8FAFC",
          borderRadius: "999px",
          px: 1.25,
          py: 0.9,
          border: "1px solid #E5E7EB",
          whiteSpace: "nowrap",
        }}
      >
        <CalendarDays size={16} />
        <Typography sx={{ fontSize: "0.95rem", fontWeight: 600 }}>
          {formattedDate}
        </Typography>
      </Box>
    </Box>
  );
};

export default WelcomeBanner;
