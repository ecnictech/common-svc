package ecnic.service.configs.security.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class StrangerAuthentication implements Authentication {
    private final List<SimpleGrantedAuthority> authorities;
    private final boolean authenticated;
    private final String password;

    private StrangerAuthentication(List<SimpleGrantedAuthority> authorities, String password) {
        this.authorities = Collections.unmodifiableList(authorities);
        this.authenticated = !CollectionUtils.isEmpty(authorities);
        this.password = password;
    }

    public static StrangerAuthentication authenticated() {
        return new StrangerAuthentication(List.of(new SimpleGrantedAuthority("ROLE_stranger")), null);
    }

    public static StrangerAuthentication authenticationToken(String password) {
        return new StrangerAuthentication(Collections.emptyList(), password);
    }

    @Override
    public Object getPrincipal() {
        return "Mr Stranger";
    }

    @Override
    public String getName() {
        return "Mr Stranger";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }


    @Override
    public String getCredentials() {
        return password;
    }

    @Override
    public Object getDetails() {
        return null;
    }


    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new RuntimeException("yeah don't do this please 💣");
    }

}
