package org.sid.ebankingbackend.security;

import org.sid.ebankingbackend.dtos.CustomerDTO;
import org.sid.ebankingbackend.entities.Customer;
import org.sid.ebankingbackend.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class SecurityController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private JwtEncoder jwtEncoder;

    @GetMapping("/profile")
    public Authentication authentication(Authentication authentication){
        return authentication;
    }

    @PostMapping("/login")
    public Map<String,String> login(String username,String password){
        org.springframework.security.core.Authentication authentication =
                authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        Instant instant = Instant.now();
        String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuedAt(instant)
                .expiresAt(instant.plus(10, ChronoUnit.DAYS))
                .subject(username)
                .claim("scope",scope)
                .build();

        JwtEncoderParameters jwtEncoderParameters =
                JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS512).build(),jwtClaimsSet);

        String jwtToken = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
        return Map.of("access-token",jwtToken);
    }

    @PostMapping("/register")
    public Map<String, String> register(String name,String email,String password){
        Customer customer = new Customer();
        customer.setPassword(password);
        customer.setName(name);
        customer.setEmail(email);
        System.out.println(bankAccountService.registerCustomer(customer));

        return login(email,password);
    }

}
