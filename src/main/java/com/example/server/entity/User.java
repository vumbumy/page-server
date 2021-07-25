package com.example.server.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "_USER")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private Long userNo;

    private String userName;

    @Column(length = 15)
    private String phoneNumber; // E.164 Format

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

    // return this.roles.stream()
    //  .map(SimpleGrantedAuthority::new)
    //  .collect(Collectors.toList());
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
        return true;
    }

}
