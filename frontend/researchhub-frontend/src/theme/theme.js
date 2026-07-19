import { createTheme } from '@mui/material/styles';

const theme = createTheme({
  palette: {
    background: { default: '#F8FAFC' },
    primary: { main: '#2563EB', dark: '#1D4ED8' },
    error: { main: '#DC2626' },
    success: { main: '#16A34A' },
    text: { primary: '#111827', secondary: '#6B7280' },
  },
  typography: {
    fontFamily: '"Inter", "Plus Jakarta Sans", sans-serif',
    h4: { fontWeight: 700, color: '#111827' },
    h5: { fontWeight: 700, color: '#111827' },
    body2: { color: '#6B7280' },
  },
  shape: { borderRadius: 10 },
  components: {
    MuiOutlinedInput: {
      styleOverrides: {
        root: {
          borderRadius: 10,
          backgroundColor: '#fff',
          '&:hover .MuiOutlinedInput-notchedOutline': { borderColor: '#2563EB' },
          '&.Mui-focused .MuiOutlinedInput-notchedOutline': {
            borderColor: '#2563EB',
            borderWidth: 1.5,
          },
        },
        notchedOutline: { borderColor: '#E5E7EB' },
      },
    },
    MuiButton: {
      styleOverrides: {
        root: {
          borderRadius: 10,
          textTransform: 'none',
          fontWeight: 600,
          fontSize: '0.9375rem',
          transition: 'all 0.2s ease',
        },
        containedPrimary: {
          boxShadow: '0 1px 3px rgba(37,99,235,0.3)',
          '&:hover': {
            backgroundColor: '#1D4ED8',
            boxShadow: '0 4px 12px rgba(37,99,235,0.35)',
            transform: 'translateY(-1px)',
          },
          '&:active': { transform: 'translateY(0)' },
        },
        outlined: {
          borderColor: '#E5E7EB',
          color: '#111827',
          '&:hover': {
            borderColor: '#2563EB',
            backgroundColor: 'rgba(37,99,235,0.04)',
            transform: 'translateY(-1px)',
          },
        },
      },
    },
    MuiInputLabel: {
      styleOverrides: {
        root: { color: '#6B7280', '&.Mui-focused': { color: '#2563EB' } },
      },
    },
    MuiCheckbox: {
      styleOverrides: { root: { color: '#E5E7EB', '&.Mui-checked': { color: '#2563EB' } } },
    },
  },
});

export default theme;
