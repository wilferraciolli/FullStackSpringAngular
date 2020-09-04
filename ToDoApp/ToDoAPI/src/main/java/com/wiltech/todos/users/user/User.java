package com.wiltech.todos.users.user;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type User.
 * Tis entity is used by Spring security framework to validate users.
 */
@Entity
@Table(name = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "User.checkUsernameExists", query = "SELECT COUNT(u.id) FROM User u WHERE u.username = :username")
public class User implements UserDetails {

    private static final long serialVersionUID = 4076308342289028053L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    private Boolean active;

    @ElementCollection(fetch = FetchType.EAGER)
    //    @CollectionTable(name = "userRoles", joinColumns = @JoinColumn(name = "userId", referencedColumnName = "id"))
    @Column(name = "role")
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    //    @OneToOne(cascade = CascadeType.ALL)
    //    @JoinColumn(name = "personId", referencedColumnName = "id")
    //    PersonNew person;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(toList());
    }

    public void updateUser(final String username, final String password, final List<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public void activateAccount() {
        this.active = true;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
        return this.active;
    }
}
