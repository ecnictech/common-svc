package ecnic.service.models.responses;

import ecnic.service.constants.EcnicUserRole;
import ecnic.service.models.entities.MenuAccess;

import java.util.Set;

public record ResponseLogin(
        String username, String password, String firstName,
        String lastName, String email, String address,
        Set<EcnicUserRole> roles,
        Set<MenuAccess> menus
) {
}
