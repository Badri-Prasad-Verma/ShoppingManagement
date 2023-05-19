package com.shoppingManagement.controller;

import com.shoppingManagement.entity.Role;
import com.shoppingManagement.entity.User;
import com.shoppingManagement.payload.LoginDto;
import com.shoppingManagement.payload.UserDto;
import com.shoppingManagement.repository.RoleRepository;
import com.shoppingManagement.repository.UserRepository;
import com.shoppingManagement.service.EmailService;
import com.shoppingManagement.seurity.JWTAuthResponse;
import com.shoppingManagement.seurity.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
   private EmailService emailService;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    public RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> singUpUser(@RequestBody UserDto userDto) throws MessagingException {
        if(userRepository.existsByEmail(userDto.getEmail())){
            return new ResponseEntity<>("Email is already exists",HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByFirstName(userDto.getFirstName())){
            return new ResponseEntity<>("Firstname is already exists",HttpStatus.BAD_REQUEST);
        }

        User user=new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());

        Role role = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(role));

        userRepository.save(user);
        String emailTo = userDto.getEmail();
        String emailSubject = "Registration Confirmation";
        String emailText = "Dear " + userDto.getFirstName() + "," +
                "\n\nThank you for registering with us. Your login credentials are:" +
                "\n\nEmail: " + userDto.getEmail() + "\nPassword: " + userDto.getPassword() +
                "\n\nPlease keep this information confidential.\n\nBest regards,\nTata Consultancy service";
        emailService.sendEmail(emailTo,emailSubject,emailText);
        return new ResponseEntity<>("User registered successfully",HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto
                                                                    loginDto){
        Authentication authentication = authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(
                loginDto.getFirstNameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponse(token));
    }


}
