import { Box, Button, InputBase, Typography } from "@mui/material";
import { Search } from "lucide-react";

const SearchBar = () => {
  return (
    <Box
      sx={{
        border: "1px solid #E5E7EB",
        borderRadius: "20px",
        backgroundColor: "#fff",
        boxShadow: "0 16px 40px rgba(15,23,42,0.06)",
        p: { xs: 2, md: 2.5 },
      }}
    >
      <Typography
        sx={{ color: "#111827", fontWeight: 700, fontSize: "1rem", mb: 1.5 }}
      >
        Quick research search
      </Typography>
      <Box
        sx={{
          display: "flex",
          alignItems: "center",
          gap: 1.25,
          border: "1px solid #E5E7EB",
          borderRadius: "16px",
          px: 1.5,
          py: 1,
          backgroundColor: "#F8FAFC",
          flexWrap: { xs: "wrap", md: "nowrap" },
        }}
      >
        <Box
          sx={{ display: "flex", alignItems: "center", flex: 1, minWidth: 0 }}
        >
          <Search size={18} color="#6B7280" />
          <InputBase
            fullWidth
            placeholder="Search papers, authors, keywords, DOI..."
            sx={{ ml: 1.25, color: "#111827", fontSize: "1rem" }}
          />
        </Box>
        <Button
          variant="contained"
          sx={{
            borderRadius: "12px",
            px: 2.25,
            py: 1,
            minWidth: { xs: "100%", md: "auto" },
          }}
        >
          Search
        </Button>
      </Box>
    </Box>
  );
};

export default SearchBar;
