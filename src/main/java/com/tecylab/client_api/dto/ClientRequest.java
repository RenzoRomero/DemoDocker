package com.tecylab.client_api.dto;

import com.tecylab.client_api.model.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClientRequest(

        @NotBlank(message = "Name is required")
        @Schema(description = "First name of the client", example = "John")
        String name,

        @NotBlank(message = "Last name is required")
        @Schema(description = "Last name of the client", example = "Doe")
        String lastName,

        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        @Schema(description = "Email of the client", example = "john.doe@example.com")
        String email,

        @NotNull(message = "Status is required")
        @Schema(description = "Status of the client", example = "ACTIVE")
        Status status

) {
}
