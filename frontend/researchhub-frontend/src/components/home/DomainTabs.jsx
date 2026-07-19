import { Box, Button, Typography } from "@mui/material";
import { motion } from "framer-motion";

const DomainTabs = ({ tabs, activeTab, onTabChange }) => {
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
            Latest research by domain
          </Typography>
          <Typography sx={{ color: "#6B7280" }}>
            Browse the latest papers filtered by your area of focus.
          </Typography>
        </Box>
      </Box>

      <Box sx={{ display: "flex", gap: 1, flexWrap: "wrap", mb: 2.5 }}>
        {tabs.map((tab) => {
          const selected = tab === activeTab;
          return (
            <motion.div key={tab} whileTap={{ scale: 0.97 }}>
              <Button
                variant={selected ? "contained" : "outlined"}
                onClick={() => onTabChange(tab)}
                sx={{
                  borderRadius: "999px",
                  px: 1.5,
                  py: 0.8,
                  fontSize: "0.9rem",
                  minWidth: "auto",
                  borderColor: selected ? "transparent" : "#E5E7EB",
                }}
              >
                {tab}
              </Button>
            </motion.div>
          );
        })}
      </Box>
    </Box>
  );
};

export default DomainTabs;
