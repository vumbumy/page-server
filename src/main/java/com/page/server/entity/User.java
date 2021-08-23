package com.page.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "_USER")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;

    private String userName;

    @Column(length = 15)
    private String phoneNumber; // E.164 Format

    @JsonIgnore
    private String password;

    // TODO : ROLE 사용시
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
			name = "_USER_ROLES",
			joinColumns = @JoinColumn(name = "USER_NO"),
			inverseJoinColumns = @JoinColumn(name = "ROLE_NO"))
    private List<Role> roles;

    @Column
    private Long groupNo;

    @Column
    private Boolean isActivated;

    // return this.roles.stream()
    //  .map(SimpleGrantedAuthority::new)
    //  .collect(Collectors.toList());

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null) {
            return new ArrayList<>();
        }
        return roles.stream().map(
            role -> new SimpleGrantedAuthority(role.value)).collect(Collectors.toList()
        );
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !ObjectUtils.isEmpty(roles);
    }

    public boolean isAdmin() {
        return roles.stream().anyMatch(role -> role.value.equals(Role.PREFIX + Role.ADMIN));
    }

    public boolean isPartnerAdmin() {
        return roles.stream().anyMatch(role -> role.value.equals(Role.PREFIX + Role.PARTNER));
    }
}
