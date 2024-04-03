package ecnic.service.services;

import ecnic.service.models.entities.EcnicUser;
import ecnic.service.models.requests.RequestRegisterUser;
import ecnic.service.models.responses.ResponseLogin;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

    public Boolean checkUsernameExist(String username);

    public EcnicUser registerUser(RequestRegisterUser requestRegisterUser, HttpServletRequest httpServletRequest);

    public ResponseLogin login(String username, String password);

    public Boolean logout(String username);
}
