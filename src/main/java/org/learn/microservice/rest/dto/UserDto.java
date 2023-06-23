package org.learn.microservice.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        description = "UserDto Model information"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Integer id;
    // User firstName should not be null or empty

    @Schema(description = "User First Name")
    @NotEmpty(message = "User firstName should not be null or empty")
    private String firstName;
    // User second Name should not be null or empty
    @Schema(description = "User Last Name")
    @NotEmpty(message = "User second Name should not be null or empty")
    private String lastName;

    // User email should not be null or empty
    // email id should be valid
    @Schema(description = "User Email")
    @NotEmpty(message = "User email should not be null or empty")
    @Email(message = "email id should be valid")
    private String email;
}
