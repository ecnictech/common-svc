package ecnic.service.configs.security.jwt;

import ecnic.service.constants.EcnicUserRole;
import ecnic.service.models.entities.EcnicUser;
import ecnic.service.repositories.EcnicUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

@Component
public class JwtUserDetailsService implements UserDetailsService {

    private final EcnicUserRepository ecnicUserRepository;

    public JwtUserDetailsService(EcnicUserRepository ecnicUserRepository) {
        this.ecnicUserRepository = ecnicUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EcnicUser user = ecnicUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found!", username)));
        return new User(username, user.getPassword(), mappingAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mappingAuthorities(Set<EcnicUserRole> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.name())).toList();
    }


}

