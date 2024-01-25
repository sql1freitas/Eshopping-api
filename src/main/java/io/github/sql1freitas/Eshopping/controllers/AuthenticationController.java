package io.github.sql1freitas.Eshopping.controllers;

import io.github.sql1freitas.Eshopping.dto.AuthenticationDto;
import io.github.sql1freitas.Eshopping.dto.LoginResponseDto;
import io.github.sql1freitas.Eshopping.dto.RegisterDto;
import io.github.sql1freitas.Eshopping.entity.Users;
import io.github.sql1freitas.Eshopping.enums.UserRole;
import io.github.sql1freitas.Eshopping.infra.security.TokenService;
import io.github.sql1freitas.Eshopping.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;
    @PostMapping("/login")
    public ResponseEntity login (@RequestBody @Valid AuthenticationDto data){

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        SecurityContextHolder.getContext().setAuthentication(auth);

        var token = tokenService.generateToken((Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping("/register/admin")
    public ResponseEntity register(@RequestBody @Valid RegisterDto data){

        if(this.userRepository.findByLogin(data.login()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        Users newUser = new Users(data.login(), encryptedPassword, data.role());

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();

    }



}
