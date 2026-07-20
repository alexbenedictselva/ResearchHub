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
import { useNavigate } from "react-router-dom";
import { useState, useEffect, useRef } from "react";
import axios from "axios";
import { List, ListItem, ListItemText } from "@mui/material";

const Navbar = ({ userName }) => {
  const navigate = useNavigate();
  const storedAuth =
    typeof window !== "undefined" ? localStorage.getItem("auth") : null;
  const derivedName = (() => {
    try {
      if (userName) return userName;
      if (!storedAuth) return null;
      const parsed = JSON.parse(storedAuth);
      return parsed.fullName || parsed.userName || null;
    } catch (e) {
      return null;
    }
  })();

  const initials =
    derivedName
      ?.split(" ")
      .map((part) => part[0])
      .slice(0, 2)
      .join("")
      .toUpperCase() || "RH";

  const [searchQuery, setSearchQuery] = useState("");
  const [searchLoading, setSearchLoading] = useState(false);
  const [searchResults, setSearchResults] = useState([]);
  const [showSearchResults, setShowSearchResults] = useState(false);
  const wrapperRef = useRef(null);

  const normalize = (paper, fallbackId) => ({
    id: paper.paperId || paper.id || fallbackId,
    title: paper.title || paper.name || "Untitled",
    authors: (paper.authors || []).map((a) => a.name || a).join(", "),
    year: paper.year || paper.publicationDate || "N/A",
    abstract: paper.abstractText || paper.abstract || "",
  });

  const handleNavbarSearch = async () => {
    const q = (searchQuery || "").trim();
    if (!q) return setShowSearchResults(true);
    setSearchLoading(true);
    setSearchResults([]);
    try {
      const auth = localStorage.getItem("auth");
      if (!auth) {
        navigate("/login");
        return;
      }
      const parsed = JSON.parse(auth);
      const res = await axios.post(
        "http://localhost:8080/api/papers/search",
        { userAbstract: q },
        { headers: { Authorization: `Bearer ${parsed.accessToken}` } },
      );

      const payload = Array.isArray(res.data)
        ? res.data
        : res.data?.papers || [];
      const normalized = (payload || []).map((p, i) => normalize(p, i + 1));
      setSearchResults(normalized);
      setShowSearchResults(true);
    } catch (e) {
      setSearchResults([]);
      setShowSearchResults(true);
    } finally {
      setSearchLoading(false);
    }
  };

  useEffect(() => {
    function handleDocClick(e) {
      if (!wrapperRef.current) return;
      if (!wrapperRef.current.contains(e.target)) {
        setShowSearchResults(false);
      }
    }
    document.addEventListener("mousedown", handleDocClick);
    return () => document.removeEventListener("mousedown", handleDocClick);
  }, []);

  return (
    <Box
      component="nav"
      sx={{
        display: "flex",
        alignItems: "center",
        justifyContent: "space-between",
        gap: 2,
        flexWrap: "wrap",
        px: { xs: 2, md: 3 },
        py: 2,
        backgroundColor: "#fff",
        boxShadow: "0 6px 20px rgba(15,23,42,0.06)",
        borderBottom: "1px solid #E5E7EB",
        position: "sticky",
        top: 0,
        zIndex: 1200,
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
        <Box sx={{ display: "flex", gap: 1, alignItems: "center", mr: 1 }}>
          <Button
            size="small"
            variant="text"
            onClick={() => navigate("/home")}
            sx={{ textTransform: "none", color: "#2563EB", fontWeight: 600 }}
          >
            Home
          </Button>
          <Button
            size="small"
            variant="text"
            onClick={() => navigate("/compare")}
            sx={{ textTransform: "none", color: "#2563EB", fontWeight: 600 }}
          >
            Compare Papers
          </Button>
        </Box>
        <Box
          ref={wrapperRef}
          sx={{ position: "relative", display: "flex", alignItems: "center" }}
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
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              onKeyDown={(e) => {
                if (e.key === "Enter") handleNavbarSearch();
              }}
              sx={{ ml: 1, flex: 1, color: "#111827", fontSize: "0.95rem" }}
            />
            <Button
              variant="contained"
              size="small"
              onClick={handleNavbarSearch}
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

          {showSearchResults && (
            <Box
              sx={{
                position: "absolute",
                left: 0,
                right: 0,
                top: "calc(100% + 8px)",
                backgroundColor: "#fff",
                border: "1px solid #E5E7EB",
                borderRadius: 2,
                boxShadow: "0 12px 40px rgba(2,6,23,0.08)",
                zIndex: 1400,
                maxHeight: 340,
                overflow: "auto",
                px: 1,
                py: 1,
              }}
            >
              {searchLoading ? (
                <Typography sx={{ color: "#6B7280", p: 1 }}>
                  Searching...
                </Typography>
              ) : searchResults.length === 0 ? (
                <Typography sx={{ color: "#6B7280", p: 1 }}>
                  No results
                </Typography>
              ) : (
                <List disablePadding>
                  {searchResults.map((p) => (
                    <ListItem
                      key={p.id}
                      secondaryAction={
                        <Button
                          size="small"
                          onClick={() => navigate(`/papers/${p.id}`)}
                        >
                          View
                        </Button>
                      }
                      sx={{ borderBottom: "1px solid #F3F4F6" }}
                    >
                      <ListItemText primary={p.title} secondary={p.authors} />
                    </ListItem>
                  ))}
                </List>
              )}
            </Box>
          )}
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
