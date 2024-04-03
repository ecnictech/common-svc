package ecnic.service.models.requests;

import ecnic.service.constants.EcnicUserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record RequestRegisterUser(
        @NotNull String username,
        @NotNull String password,
        @NotNull String firstName,
        String lastName,
        @NotNull @Email String email,
        String address,
        @NotNull Set<EcnicUserRole> roles
) {
}
