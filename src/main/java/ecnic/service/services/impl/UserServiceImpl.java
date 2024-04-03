package ecnic.service.services.impl;

import ecnic.service.models.entities.EcnicUser;
import ecnic.service.models.requests.RequestRegisterUser;
import ecnic.service.models.responses.ResponseLogin;
import ecnic.service.repositories.EcnicUserRepository;
import ecnic.service.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final EcnicUserRepository repository;

    public UserServiceImpl(EcnicUserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Boolean checkUsernameExist(String username) {
        var ecnicUser = repository.findByUsername(username).orElse(null);
        return ecnicUser != null;
    }

    @Override
    public EcnicUser registerUser(RequestRegisterUser rru, HttpServletRequest hsr) {
        var newUser = EcnicUser.builder()
                .username(rru.username())
                .firstName(rru.firstName())
                .password(passwordEncoder.encode(rru.password()))
                .lastName(rru.lastName())
                .address(rru.address())
                .email(rru.email())
                .saltPassword(rru.firstName())
                .roles(rru.roles())
                .email(rru.email())
                .ipv4(hsr.getRemoteAddr())
                .build();
        repository.save(newUser);
        return newUser;
    }

    @Override
    public ResponseLogin login(String username, String password) {
        var ecnicUser = repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found!", username)));
        if (!ecnicUser.equals(username)) {
            //throw new CustomException(tr)
        }
        return null;
    }

    @Override
    public Boolean logout(String username) {
        return null;
    }
}
