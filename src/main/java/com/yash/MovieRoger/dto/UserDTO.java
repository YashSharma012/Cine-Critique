package com.yash.MovieRoger.dto;

import com.yash.MovieRoger.enums.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDTO {
    private Long id;

    @NotBlank(message = "User name is mandatory")
    @Column(name = "username", unique = true)
    private String username;

    @NotEmpty(message = "Password is mandatory")
    private String password;

    @NotNull(message = "Role is required")
    private Role role;

    @NotEmpty(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number")
    private String mobile;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Enter a valid email")
    private String email;

    private List<TicketDTO> tickets;
}
