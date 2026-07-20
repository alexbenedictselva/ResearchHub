import { BrowserRouter, Routes, Route, Navigate, useLocation } from "react-router-dom";
import { ThemeProvider, CssBaseline } from "@mui/material";
import theme from "./theme/theme";
import LoginPage from "./pages/auth/LoginPage";
import RegisterPage from "./pages/auth/RegisterPage";
import HomePage from "./pages/HomePage";
import PaperDetailsPage from "./pages/PaperDetailsPage";
import ComparePapersPage from "./pages/ComparePapersPage";
import Navbar from "./components/home/Navbar";

function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <BrowserRouter>
        <AppContent />
      </BrowserRouter>
    </ThemeProvider>
  );
}

const AppContent = () => {
  const location = useLocation();
  const hideNavbar = ["/login", "/register"];

  return (
    <>
      {!hideNavbar.includes(location.pathname) && <Navbar />}
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/home" element={<HomePage />} />
        <Route path="/papers/:paperId" element={<PaperDetailsPage />} />
        <Route path="/compare" element={<ComparePapersPage />} />
        <Route path="*" element={<Navigate to="/login" replace />} />
      </Routes>
    </>
  );

};

export default App;
