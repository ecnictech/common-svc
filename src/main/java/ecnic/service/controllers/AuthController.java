package ecnic.service.controllers;

import ecnic.service.models.requests.RequestRegisterUser;
import ecnic.service.models.responses.ResponseLogin;
import ecnic.service.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("user/check/{username}")
    public ResponseEntity<Boolean> isUsernameExsist(@PathVariable String username) {
        Boolean b = userService.checkUsernameExist(username);
        return new ResponseEntity<>(b ? HttpStatus.FOUND : HttpStatus.NOT_FOUND);
    }

    @GetMapping("user/login")
    public ResponseEntity<ResponseLogin> login(@RequestParam String username, @RequestParam String password) {
        ResponseLogin login = userService.login(username, password);
        return new ResponseEntity<>(login, HttpStatus.OK);
    }

    @PostMapping("user/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void registerUser(@RequestBody RequestRegisterUser requestUser, HttpServletRequest httpServletRequest) {
        userService.registerUser(requestUser, httpServletRequest);
    }

}
