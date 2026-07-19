import {
  Box,
  Button,
  IconButton,
  InputBase,
  Typography,
  alpha,
} from "@mui/material";
import { Bell, Search, Sparkles } from "lucide-react";
import { motion } from "framer-motion";

const Navbar = ({ userName }) => {
  const initials =
    userName
      ?.split(" ")
      .map((part) => part[0])
      .slice(0, 2)
      .join("")
      .toUpperCase() || "RH";

  return (
    <Box
      component="nav"
      sx={{
        display: "flex",
        alignItems: "center",
        justifyContent: "space-between",
        gap: 2,
        flexWrap: "wrap",
        px: { xs: 0, md: 0 },
        py: 2,
      }}
    >
      <Box
        sx={{ display: "flex", alignItems: "center", gap: 1.5, minWidth: 0 }}
      >
        <Box
          sx={{
            width: 40,
            height: 40,
            borderRadius: "12px",
            background: "linear-gradient(135deg, #2563EB 0%, #1D4ED8 100%)",
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            color: "#fff",
            boxShadow: "0 8px 24px rgba(37, 99, 235, 0.22)",
          }}
        >
          <Sparkles size={18} />
        </Box>
        <Box sx={{ minWidth: 0 }}>
          <Typography
            variant="h6"
            sx={{ fontWeight: 700, letterSpacing: "-0.02em", color: "#111827" }}
          >
            ResearchHub X
          </Typography>
          <Typography
            variant="body2"
            sx={{ color: "#6B7280", fontSize: "0.8rem" }}
          >
            Intelligence for discovery
          </Typography>
        </Box>
      </Box>

      <Box
        sx={{
          display: "flex",
          alignItems: "center",
          gap: 1.25,
          flex: { xs: "1 1 100%", md: "0 1 480px" },
          justifyContent: "flex-end",
          flexWrap: "wrap",
        }}
      >
        <Box
          sx={{
            display: "flex",
            alignItems: "center",
            border: "1px solid #E5E7EB",
            borderRadius: "999px",
            backgroundColor: "#fff",
            px: 1.25,
            py: 0.6,
            minWidth: { xs: "100%", md: 360 },
            boxShadow: "0 6px 18px rgba(15,23,42,0.04)",
          }}
        >
          <Search size={18} color="#6B7280" />
          <InputBase
            placeholder="Search papers, authors, keywords, DOI..."
            sx={{ ml: 1, flex: 1, color: "#111827", fontSize: "0.95rem" }}
          />
          <Button
            variant="contained"
            size="small"
            sx={{
              borderRadius: "999px",
              px: 1.5,
              py: 0.75,
              ml: 1,
              minWidth: "auto",
            }}
          >
            Search
          </Button>
        </Box>

        <IconButton
          size="small"
          sx={{
            border: "1px solid #E5E7EB",
            backgroundColor: "#fff",
            color: "#111827",
            width: 42,
            height: 42,
            "&:hover": {
              backgroundColor: alpha("#2563EB", 0.06),
              borderColor: "#2563EB",
            },
          }}
        >
          <Bell size={18} />
        </IconButton>

        <motion.div whileHover={{ scale: 1.03 }}>
          <Box
            sx={{
              display: "flex",
              alignItems: "center",
              gap: 1,
              border: "1px solid #E5E7EB",
              backgroundColor: "#fff",
              borderRadius: "999px",
              px: 0.75,
              py: 0.35,
            }}
          >
            <Box
              sx={{
                width: 34,
                height: 34,
                borderRadius: "50%",
                background: "linear-gradient(135deg, #111827 0%, #2563EB 100%)",
                color: "#fff",
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                fontSize: "0.8rem",
                fontWeight: 700,
              }}
            >
              {initials}
            </Box>
            <Typography
              sx={{ color: "#111827", fontWeight: 600, fontSize: "0.95rem" }}
            >
              {userName}
            </Typography>
          </Box>
        </motion.div>
      </Box>
    </Box>
  );
};

export default Navbar;
