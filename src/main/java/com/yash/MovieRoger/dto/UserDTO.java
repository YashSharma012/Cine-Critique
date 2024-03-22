package com.yash.MovieRoger.dto;

import com.yash.MovieRoger.enums.Role;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
public class UserDTO {
    private long id;

    @NotBlank(message = "User name is mandatory")
    private String username;

    @NotBlank(message = "Password is mandatory")
    private String password;

    private Role role;

    @NotBlank(message = "Mobile is mandatory")
    private String mobile;

    @NotBlank(message = "Email is mandatory")
    private String email;

    private List<TicketDTO> tickets;
}
