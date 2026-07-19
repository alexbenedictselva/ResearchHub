import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { Link as RouterLink, useNavigate } from 'react-router-dom';
import axios from 'axios';
import {
  Box, Typography, TextField, Button, Checkbox, FormControlLabel,
  Divider, Link, InputAdornment, IconButton, CircularProgress, Alert,
} from '@mui/material';
import { Mail, Lock, Eye, EyeOff } from 'lucide-react';
import AuthLayout from '../../components/auth/AuthLayout';

const GoogleIcon = () => (
  <svg width="18" height="18" viewBox="0 0 18 18" fill="none" xmlns="http://www.w3.org/2000/svg">
    <path d="M17.64 9.2c0-.637-.057-1.251-.164-1.84H9v3.481h4.844c-.209 1.125-.843 2.078-1.796 2.717v2.258h2.908c1.702-1.567 2.684-3.875 2.684-6.615z" fill="#4285F4" />
    <path d="M9 18c2.43 0 4.467-.806 5.956-2.18l-2.908-2.259c-.806.54-1.837.86-3.048.86-2.344 0-4.328-1.584-5.036-3.711H.957v2.332A8.997 8.997 0 0 0 9 18z" fill="#34A853" />
    <path d="M3.964 10.71A5.41 5.41 0 0 1 3.682 9c0-.593.102-1.17.282-1.71V4.958H.957A8.996 8.996 0 0 0 0 9c0 1.452.348 2.827.957 4.042l3.007-2.332z" fill="#FBBC05" />
    <path d="M9 3.58c1.321 0 2.508.454 3.44 1.345l2.582-2.58C13.463.891 11.426 0 9 0A8.997 8.997 0 0 0 .957 4.958L3.964 6.29C4.672 4.163 6.656 3.58 9 3.58z" fill="#EA4335" />
  </svg>
);

const LoginPage = () => {
  const [showPassword, setShowPassword] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [serverError, setServerError] = useState('');

  const navigate = useNavigate();
  const { register, handleSubmit, formState: { errors } } = useForm({ mode: 'onBlur' });

  const onSubmit = async (data) => {
    setIsLoading(true);
    setServerError('');
    try {
      const response = await axios.post('http://localhost:8080/api/auth/login', {
        email: data.email,
        password: data.password,
      });
      const { accessToken, tokenType, fullName, email, researcherId, expiresInMs } = response.data.data;
      localStorage.setItem('auth', JSON.stringify({ accessToken, tokenType, fullName, email, researcherId, expiresInMs }));
      navigate('/home');
    } catch (err) {
      const msg = err.response?.data?.message || 'Invalid email or password. Please try again.';
      setServerError(msg);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <AuthLayout>
      <Typography variant="h5" sx={{ mb: 0.75, letterSpacing: '-0.02em' }}>
        Welcome back
      </Typography>
      <Typography variant="body2" sx={{ mb: 3.5 }}>
        Sign in to your ResearchHub X account
      </Typography>

      {serverError && (
        <Alert severity="error" sx={{ mb: 2.5, borderRadius: '10px', fontSize: '0.875rem' }}>
          {serverError}
        </Alert>
      )}

      <Box component="form" onSubmit={handleSubmit(onSubmit)} noValidate>
        <TextField
          fullWidth
          label="Email Address"
          type="email"
          size="small"
          sx={{ mb: 2 }}
          error={!!errors.email}
          helperText={errors.email?.message}
          InputProps={{
            startAdornment: (
              <InputAdornment position="start">
                <Mail size={16} color="#9CA3AF" />
              </InputAdornment>
            ),
          }}
          {...register('email', {
            required: 'Email is required',
            pattern: { value: /^[^\s@]+@[^\s@]+\.[^\s@]+$/, message: 'Enter a valid email address' },
          })}
        />

        <TextField
          fullWidth
          label="Password"
          type={showPassword ? 'text' : 'password'}
          size="small"
          sx={{ mb: 1 }}
          error={!!errors.password}
          helperText={errors.password?.message}
          InputProps={{
            startAdornment: (
              <InputAdornment position="start">
                <Lock size={16} color="#9CA3AF" />
              </InputAdornment>
            ),
            endAdornment: (
              <InputAdornment position="end">
                <IconButton size="small" onClick={() => setShowPassword((p) => !p)} edge="end" aria-label="toggle password visibility">
                  {showPassword ? <EyeOff size={16} color="#9CA3AF" /> : <Eye size={16} color="#9CA3AF" />}
                </IconButton>
              </InputAdornment>
            ),
          }}
          {...register('password', { required: 'Password is required' })}
        />

        <Box sx={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', mb: 3 }}>
          <FormControlLabel
            control={<Checkbox size="small" {...register('rememberMe')} />}
            label={<Typography sx={{ fontSize: '0.8125rem', color: '#374151' }}>Remember me</Typography>}
          />
          <Link
            component={RouterLink}
            to="/forgot-password"
            sx={{ fontSize: '0.8125rem', color: '#2563EB', textDecoration: 'none', fontWeight: 500, '&:hover': { textDecoration: 'underline' } }}
          >
            Forgot password?
          </Link>
        </Box>

        <Button
          type="submit"
          fullWidth
          variant="contained"
          disabled={isLoading}
          sx={{ height: 44, mb: 2 }}
        >
          {isLoading ? <CircularProgress size={20} sx={{ color: '#fff' }} /> : 'Sign In'}
        </Button>

        <Divider sx={{ my: 2.5, color: '#9CA3AF', fontSize: '0.8125rem', '&::before, &::after': { borderColor: '#E5E7EB' } }}>
          OR
        </Divider>

        <Button
          fullWidth
          variant="outlined"
          startIcon={<GoogleIcon />}
          sx={{ height: 44, mb: 3 }}
        >
          Continue with Google
        </Button>

        <Typography sx={{ textAlign: 'center', fontSize: '0.875rem', color: '#6B7280' }}>
          Don't have an account?{' '}
          <Link
            component={RouterLink}
            to="/register"
            sx={{ color: '#2563EB', fontWeight: 600, textDecoration: 'none', '&:hover': { textDecoration: 'underline' } }}
          >
            Create Account
          </Link>
        </Typography>
      </Box>
    </AuthLayout>
  );
};

export default LoginPage;
