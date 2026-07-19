import { Box, Typography, useMediaQuery, useTheme } from '@mui/material';
import { motion } from 'framer-motion';
import { BookOpen } from 'lucide-react';
import ResearchIllustration from './ResearchIllustration';

const cardVariants = {
  hidden: { opacity: 0, y: 24 },
  visible: { opacity: 1, y: 0, transition: { duration: 0.4, ease: 'easeOut' } },
};

const AuthLayout = ({ children }) => {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down('md'));

  return (
    <Box sx={{ display: 'flex', minHeight: '100vh', backgroundColor: '#F8FAFC' }}>
      {/* Left Panel */}
      {!isMobile && (
        <Box
          sx={{
            width: '40%',
            background: 'linear-gradient(160deg, #1e3a8a 0%, #1D4ED8 50%, #2563EB 100%)',
            display: 'flex',
            flexDirection: 'column',
            justifyContent: 'center',
            alignItems: 'center',
            px: 6,
            py: 8,
            position: 'relative',
            overflow: 'hidden',
          }}
        >
          {/* Subtle background texture */}
          <Box
            sx={{
              position: 'absolute', inset: 0, opacity: 0.04,
              backgroundImage: 'radial-gradient(circle at 1px 1px, white 1px, transparent 0)',
              backgroundSize: '28px 28px',
            }}
          />

          <Box sx={{ position: 'relative', zIndex: 1, maxWidth: 380, textAlign: 'center' }}>
            {/* Logo */}
            <Box sx={{ display: 'flex', alignItems: 'center', justifyContent: 'center', gap: 1.5, mb: 5 }}>
              <Box
                sx={{
                  width: 40, height: 40, borderRadius: '10px',
                  backgroundColor: 'rgba(255,255,255,0.15)',
                  border: '1px solid rgba(255,255,255,0.25)',
                  display: 'flex', alignItems: 'center', justifyContent: 'center',
                }}
              >
                <BookOpen size={20} color="#fff" />
              </Box>
              <Typography sx={{ color: '#fff', fontWeight: 700, fontSize: '1.125rem', letterSpacing: '-0.01em' }}>
                ResearchHub X
              </Typography>
            </Box>

            <Typography
              variant="h4"
              sx={{ color: '#fff', fontSize: '1.875rem', lineHeight: 1.25, mb: 2.5, letterSpacing: '-0.02em' }}
            >
              Empowering Research Through AI
            </Typography>

            <Typography
              sx={{ color: 'rgba(255,255,255,0.72)', fontSize: '0.9375rem', lineHeight: 1.7, mb: 6 }}
            >
              Search papers, analyze innovation, summarize research, and discover knowledge using AI.
            </Typography>

            <ResearchIllustration />

            {/* Feature pills */}
            <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 1, justifyContent: 'center', mt: 5 }}>
              {['AI Analysis', 'Paper Search', 'Summarization', 'Knowledge Graph'].map((label) => (
                <Box
                  key={label}
                  sx={{
                    px: 1.75, py: 0.6,
                    borderRadius: '20px',
                    border: '1px solid rgba(255,255,255,0.2)',
                    backgroundColor: 'rgba(255,255,255,0.08)',
                    color: 'rgba(255,255,255,0.8)',
                    fontSize: '0.75rem',
                    fontWeight: 500,
                    letterSpacing: '0.01em',
                  }}
                >
                  {label}
                </Box>
              ))}
            </Box>
          </Box>
        </Box>
      )}

      {/* Right Panel */}
      <Box
        sx={{
          flex: 1,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          justifyContent: 'center',
          px: { xs: 2, sm: 4 },
          py: 6,
          overflowY: 'auto',
        }}
      >
        {/* Mobile logo */}
        {isMobile && (
          <Box sx={{ display: 'flex', alignItems: 'center', gap: 1.5, mb: 4 }}>
            <Box
              sx={{
                width: 36, height: 36, borderRadius: '9px',
                backgroundColor: '#2563EB',
                display: 'flex', alignItems: 'center', justifyContent: 'center',
              }}
            >
              <BookOpen size={18} color="#fff" />
            </Box>
            <Typography sx={{ fontWeight: 700, fontSize: '1.0625rem', color: '#111827', letterSpacing: '-0.01em' }}>
              ResearchHub X
            </Typography>
          </Box>
        )}

        <motion.div
          variants={cardVariants}
          initial="hidden"
          animate="visible"
          style={{ width: '100%', maxWidth: 420 }}
        >
          <Box
            sx={{
              backgroundColor: '#fff',
              borderRadius: '16px',
              border: '1px solid #E5E7EB',
              boxShadow: '0 4px 6px -1px rgba(0,0,0,0.05), 0 10px 40px -8px rgba(0,0,0,0.08)',
              p: { xs: 3.5, sm: 4.5 },
            }}
          >
            {children}
          </Box>
        </motion.div>
      </Box>
    </Box>
  );
};

export default AuthLayout;
