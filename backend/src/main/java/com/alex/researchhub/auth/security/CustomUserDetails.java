package com.alex.researchhub.auth.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.alex.researchhub.entity.Researcher;

public class CustomUserDetails implements UserDetails {
    private final Long researcherId;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Researcher researcher) {
        this.researcherId = researcher.getId();
        this.email = researcher.getEmail();
        this.password = researcher.getPassword();
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_RESEARCHER"));
    }

    public Long getResearcherId() { return researcherId; }
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return email; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
