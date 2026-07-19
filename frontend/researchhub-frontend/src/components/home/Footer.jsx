import { Box, Typography } from "@mui/material";

const Footer = () => {
  return (
    <Box
      sx={{
        py: 3,
        borderTop: "1px solid #E5E7EB",
        mt: 4,
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        flexWrap: "wrap",
        gap: 1.5,
      }}
    >
      <Typography sx={{ color: "#6B7280", fontSize: "0.9rem" }}>
        ResearchHub X • AI-powered research discovery
      </Typography>
      <Typography sx={{ color: "#6B7280", fontSize: "0.9rem" }}>
        Updated just now
      </Typography>
    </Box>
  );
};

export default Footer;
