package com.lokapos.entities;

import com.lokapos.enums.USER_ROLE_ENUM;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account extends BaseEntity implements UserDetails {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;


    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private USER_ROLE_ENUM role;

    @Column(name = "is_verified_email")
    private Boolean isVerifiedEmail;


    @JoinColumn(name = "business_id")
    @ManyToOne
    private Business business;

    @JoinColumn(name = "merchant_id")
    @ManyToOne
    private Merchant merchant;

    @JoinColumn(name = "active_shift_id")
    @ManyToOne
    private Shift activeShift;

    @Column(name = "fcm_token")
    private String fcmToken;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShiftAccount> shiftAccounts;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
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
