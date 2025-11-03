package anisimov.iblab1.controller;

import jakarta.validation.Valid;
import anisimov.iblab1.dto.JwtAuthenticationResponseDTO;
import anisimov.iblab1.dto.MessageInfoDTO;
import anisimov.iblab1.dto.LoginRequestDTO;
import anisimov.iblab1.dto.RegisterRequestDTO;
import anisimov.iblab1.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authenticationService;

    @PostMapping("/register")
    public JwtAuthenticationResponseDTO register(@RequestBody @Valid RegisterRequestDTO request) {
        return authenticationService.register(request);
    }

    @PostMapping("/login")
    public JwtAuthenticationResponseDTO login(@RequestBody @Valid LoginRequestDTO request) {
        return authenticationService.login(request);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageInfoDTO error(Exception ex) {
        return new MessageInfoDTO(ex.getMessage());
    }
}
