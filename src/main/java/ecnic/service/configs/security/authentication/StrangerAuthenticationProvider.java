package ecnic.service.configs.security.authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Arrays;
import java.util.List;

public class StrangerAuthenticationProvider implements AuthenticationProvider {

    private final List<String> passwords;

    public StrangerAuthenticationProvider(String... password) {
        this.passwords = Arrays.asList(password);
    }

    @Override
    public Authentication authenticate(Authentication authenticationToken) throws AuthenticationException {
        var authentication = (StrangerAuthentication) authenticationToken;
        if (this.passwords.contains(authentication.getCredentials())) {
            return StrangerAuthentication.authenticated();
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return StrangerAuthentication.class.isAssignableFrom(authentication);
    }
}
