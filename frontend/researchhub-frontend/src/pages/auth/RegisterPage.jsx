import { useState, useMemo } from 'react';
import { useForm, Controller } from 'react-hook-form';
import { Link as RouterLink, useNavigate } from 'react-router-dom';
import axios from 'axios';
import {
  Box, Typography, TextField, Button, Checkbox, FormControlLabel,
  Divider, Link, InputAdornment, IconButton, CircularProgress, Alert,
  MenuItem, LinearProgress,
} from '@mui/material';
import { User, Mail, Lock, Eye, EyeOff, Building2 } from 'lucide-react';
import AuthLayout from '../../components/auth/AuthLayout';

const RESEARCH_FIELDS = [
  'Computer Science & AI', 'Biomedical & Life Sciences', 'Physics & Astronomy',
  'Chemistry & Materials', 'Mathematics & Statistics', 'Social Sciences',
  'Environmental Sciences', 'Engineering', 'Economics & Finance', 'Other',
];

const GoogleIcon = () => (
  <svg width="18" height="18" viewBox="0 0 18 18" fill="none" xmlns="http://www.w3.org/2000/svg">
    <path d="M17.64 9.2c0-.637-.057-1.251-.164-1.84H9v3.481h4.844c-.209 1.125-.843 2.078-1.796 2.717v2.258h2.908c1.702-1.567 2.684-3.875 2.684-6.615z" fill="#4285F4" />
    <path d="M9 18c2.43 0 4.467-.806 5.956-2.18l-2.908-2.259c-.806.54-1.837.86-3.048.86-2.344 0-4.328-1.584-5.036-3.711H.957v2.332A8.997 8.997 0 0 0 9 18z" fill="#34A853" />
    <path d="M3.964 10.71A5.41 5.41 0 0 1 3.682 9c0-.593.102-1.17.282-1.71V4.958H.957A8.996 8.996 0 0 0 0 9c0 1.452.348 2.827.957 4.042l3.007-2.332z" fill="#FBBC05" />
    <path d="M9 3.58c1.321 0 2.508.454 3.44 1.345l2.582-2.58C13.463.891 11.426 0 9 0A8.997 8.997 0 0 0 .957 4.958L3.964 6.29C4.672 4.163 6.656 3.58 9 3.58z" fill="#EA4335" />
  </svg>
);

const getPasswordStrength = (password) => {
  if (!password) return { score: 0, label: '', color: 'transparent' };
  let score = 0;
  if (password.length >= 8) score++;
  if (/[A-Z]/.test(password)) score++;
  if (/[0-9]/.test(password)) score++;
  if (/[^A-Za-z0-9]/.test(password)) score++;
  const map = [
    { label: 'Too weak', color: '#DC2626' },
    { label: 'Weak', color: '#F97316' },
    { label: 'Fair', color: '#EAB308' },
    { label: 'Strong', color: '#16A34A' },
    { label: 'Very strong', color: '#15803D' },
  ];
  return { score, ...map[score] };
};

const RegisterPage = () => {
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirm, setShowConfirm] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [serverError, setServerError] = useState('');

  const navigate = useNavigate();
  const { register, handleSubmit, watch, control, formState: { errors } } = useForm({ mode: 'onChange' });
  const passwordValue = watch('password', '');
  const strength = useMemo(() => getPasswordStrength(passwordValue), [passwordValue]);

  const onSubmit = async (data) => {
    setIsLoading(true);
    setServerError('');
    try {
      const response = await axios.post('http://localhost:8080/api/auth/register', {
        fullName: data.fullName,
        email: data.email,
        password: data.password,
      });
      const { accessToken, tokenType, fullName, email, researcherId, expiresInMs } = response.data.data;
      localStorage.setItem('auth', JSON.stringify({ accessToken, tokenType, fullName, email, researcherId, expiresInMs }));
      navigate('/home');
    } catch (err) {
      const msg = err.response?.data?.message || 'Registration failed. Please try again.';
      setServerError(msg);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <AuthLayout>
      <Typography variant="h5" sx={{ mb: 0.75, letterSpacing: '-0.02em' }}>
        Create your account
      </Typography>
      <Typography variant="body2" sx={{ mb: 3 }}>
        Join thousands of researchers on ResearchHub X
      </Typography>

      {serverError && (
        <Alert severity="error" sx={{ mb: 2.5, borderRadius: '10px', fontSize: '0.875rem' }}>
          {serverError}
        </Alert>
      )}

      <Box component="form" onSubmit={handleSubmit(onSubmit)} noValidate>
        <TextField
          fullWidth label="Full Name" size="small" sx={{ mb: 2 }}
          error={!!errors.fullName} helperText={errors.fullName?.message}
          InputProps={{ startAdornment: <InputAdornment position="start"><User size={16} color="#9CA3AF" /></InputAdornment> }}
          {...register('fullName', {
            required: 'Full name is required',
            minLength: { value: 2, message: 'Name must be at least 2 characters' },
          })}
        />

        <TextField
          fullWidth label="Email Address" type="email" size="small" sx={{ mb: 2 }}
          error={!!errors.email} helperText={errors.email?.message}
          InputProps={{ startAdornment: <InputAdornment position="start"><Mail size={16} color="#9CA3AF" /></InputAdornment> }}
          {...register('email', {
            required: 'Email is required',
            pattern: { value: /^[^\s@]+@[^\s@]+\.[^\s@]+$/, message: 'Enter a valid email address' },
          })}
        />

        <TextField
          fullWidth label="Password" type={showPassword ? 'text' : 'password'} size="small" sx={{ mb: 0.75 }}
          error={!!errors.password} helperText={errors.password?.message}
          InputProps={{
            startAdornment: <InputAdornment position="start"><Lock size={16} color="#9CA3AF" /></InputAdornment>,
            endAdornment: (
              <InputAdornment position="end">
                <IconButton size="small" onClick={() => setShowPassword((p) => !p)} edge="end" aria-label="toggle password visibility">
                  {showPassword ? <EyeOff size={16} color="#9CA3AF" /> : <Eye size={16} color="#9CA3AF" />}
                </IconButton>
              </InputAdornment>
            ),
          }}
          {...register('password', {
            required: 'Password is required',
            minLength: { value: 8, message: 'Password must be at least 8 characters' },
          })}
        />

        {/* Password strength indicator */}
        {passwordValue && (
          <Box sx={{ mb: 2 }}>
            <LinearProgress
              variant="determinate"
              value={(strength.score / 4) * 100}
              sx={{
                height: 3, borderRadius: 2, backgroundColor: '#E5E7EB',
                '& .MuiLinearProgress-bar': { backgroundColor: strength.color, borderRadius: 2 },
              }}
            />
            <Typography sx={{ fontSize: '0.75rem', color: strength.color, mt: 0.5, fontWeight: 500 }}>
              {strength.label}
            </Typography>
          </Box>
        )}

        <TextField
          fullWidth label="Confirm Password" type={showConfirm ? 'text' : 'password'} size="small" sx={{ mb: 2 }}
          error={!!errors.confirmPassword} helperText={errors.confirmPassword?.message}
          InputProps={{
            startAdornment: <InputAdornment position="start"><Lock size={16} color="#9CA3AF" /></InputAdornment>,
            endAdornment: (
              <InputAdornment position="end">
                <IconButton size="small" onClick={() => setShowConfirm((p) => !p)} edge="end" aria-label="toggle confirm password visibility">
                  {showConfirm ? <EyeOff size={16} color="#9CA3AF" /> : <Eye size={16} color="#9CA3AF" />}
                </IconButton>
              </InputAdornment>
            ),
          }}
          {...register('confirmPassword', {
            required: 'Please confirm your password',
            validate: (val) => val === passwordValue || 'Passwords do not match',
          })}
        />

        <TextField
          fullWidth label="Institution / University (optional)" size="small" sx={{ mb: 2 }}
          InputProps={{ startAdornment: <InputAdornment position="start"><Building2 size={16} color="#9CA3AF" /></InputAdornment> }}
          {...register('institution')}
        />

        <Controller
          name="researchField"
          control={control}
          defaultValue=""
          rules={{ required: 'Please select your research field' }}
          render={({ field }) => (
            <TextField
              {...field}
              select fullWidth label="Research Field" size="small" sx={{ mb: 2.5 }}
              error={!!errors.researchField} helperText={errors.researchField?.message}
            >
              {RESEARCH_FIELDS.map((f) => (
                <MenuItem key={f} value={f} sx={{ fontSize: '0.875rem' }}>{f}</MenuItem>
              ))}
            </TextField>
          )}
        />

        <FormControlLabel
          sx={{ mb: 3, alignItems: 'flex-start' }}
          control={
            <Checkbox
              size="small"
              sx={{ mt: '-2px' }}
              {...register('agreeTerms', { required: 'You must agree to the terms' })}
            />
          }
          label={
            <Typography sx={{ fontSize: '0.8125rem', color: errors.agreeTerms ? '#DC2626' : '#374151', lineHeight: 1.5 }}>
              I agree to the{' '}
              <Link href="#" sx={{ color: '#2563EB', fontWeight: 500, textDecoration: 'none', '&:hover': { textDecoration: 'underline' } }}>
                Terms of Service
              </Link>{' '}
              and{' '}
              <Link href="#" sx={{ color: '#2563EB', fontWeight: 500, textDecoration: 'none', '&:hover': { textDecoration: 'underline' } }}>
                Privacy Policy
              </Link>
              {errors.agreeTerms && (
                <Box component="span" sx={{ display: 'block', color: '#DC2626', fontSize: '0.75rem', mt: 0.25 }}>
                  {errors.agreeTerms.message}
                </Box>
              )}
            </Typography>
          }
        />

        <Button
          type="submit" fullWidth variant="contained"
          disabled={isLoading} sx={{ height: 44, mb: 2 }}
        >
          {isLoading ? <CircularProgress size={20} sx={{ color: '#fff' }} /> : 'Create Account'}
        </Button>

        <Divider sx={{ my: 2.5, color: '#9CA3AF', fontSize: '0.8125rem', '&::before, &::after': { borderColor: '#E5E7EB' } }}>
          OR
        </Divider>

        <Button fullWidth variant="outlined" startIcon={<GoogleIcon />} sx={{ height: 44, mb: 3 }}>
          Continue with Google
        </Button>

        <Typography sx={{ textAlign: 'center', fontSize: '0.875rem', color: '#6B7280' }}>
          Already have an account?{' '}
          <Link
            component={RouterLink} to="/login"
            sx={{ color: '#2563EB', fontWeight: 600, textDecoration: 'none', '&:hover': { textDecoration: 'underline' } }}
          >
            Sign In
          </Link>
        </Typography>
      </Box>
    </AuthLayout>
  );
};

export default RegisterPage;
