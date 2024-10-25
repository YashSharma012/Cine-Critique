package com.yash.MovieRoger.model;

import com.yash.MovieRoger.dto.UserDTO;
import com.yash.MovieRoger.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "mobile"),
        @UniqueConstraint(columnNames = "email")
})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String username;

    @Column(name="password",nullable = false)
    private String password;

    @NotNull
    @Column(name = "mobile", nullable = false)
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number should be between 10 to 15 digits and can start with '+'")
    private String mobile;

    @NotNull
    @Column(name = "email", nullable = false)
    @Email(message = "Email should be valid")
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Ticket> ticketEntities;

    @Enumerated(EnumType.STRING)
    @Column(name = "role" ,columnDefinition = "varchar(30) default 'USER'")
    private Role role;

    public static User toEntity(UserDTO userDTO) {
        return User.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .mobile(userDTO.getMobile())
                .role(userDTO.getRole())
                .build();
    }

    public static UserDTO toResource(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .role(user.getRole())
                .tickets(Ticket.toResource(user.getTicketEntities()))
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(this.role.toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return username;
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
