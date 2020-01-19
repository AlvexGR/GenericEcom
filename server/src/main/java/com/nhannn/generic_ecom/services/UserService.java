package com.nhannn.generic_ecom.services;

import com.nhannn.generic_ecom.helpers.enums.Role;
import com.nhannn.generic_ecom.helpers.enums.UserStatus;
import com.nhannn.generic_ecom.models.User;
import com.nhannn.generic_ecom.models.AuthenticatedUser;
import com.nhannn.generic_ecom.models.apis.login.GoogleLoginRequest;
import com.nhannn.generic_ecom.repositories.interfaces.IUserRepository;
import com.nhannn.generic_ecom.security.JwtToken;
import com.nhannn.generic_ecom.security.JwtUserDetailsService;
import com.nhannn.generic_ecom.services.interfaces.IUserService;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.UUID;

/**
 * Author: nhannn
 */
@Service("userSer")
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtToken jwtToken;
    private final JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    public UserService(
            @Qualifier("userRepo") IUserRepository userRepository,
            AuthenticationManager authenticationManager,
            JwtToken jwtToken,
            JwtUserDetailsService jwtUserDetailsService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtToken = jwtToken;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    /**
     * Insert new user into database
     * @param user new user to sign up
     * @return true or false for signing up successfully
     */
    @Override
    public boolean signUp(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.insert(user);
        return true;
    }

    /**
     * Validate credentials and generate access token
     * @param email username
     * @param password password
     * @param rememberMe access token can use forever if true
     * @return User with access token
     */
    @Override
    public AuthenticatedUser login(String email, String password, Boolean rememberMe) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(email);
            String token = jwtToken.generateToken(userDetails, rememberMe);
            AuthenticatedUser authenticatedUser = new AuthenticatedUser();
            authenticatedUser.setUser(userRepository.getByEmail(email));
            authenticatedUser.setAccessToken(token);
            return authenticatedUser;
        } catch (AuthenticationException ex) {
            return null;
        }
    }

    @Override
    public User getById(String id) {
        return userRepository.getById(User.class, id);
    }

    /**
     * Create default user (customer role) with random password
     * @return new User
     */
    private User createDefault() {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        RandomStringGenerator passwordGen = new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(CharacterPredicates.DIGITS, CharacterPredicates.LETTERS)
                .build();
        user.setPassword(passwordGen.generate(10));
        user.setCreatedAt(new Date());
        user.setRole(Role.CUSTOMER);
        user.setStatus(UserStatus.DEACTIVATED);
        return user;
    }

    /**
     * Login with google account
     * If user exists then login
     * Else create new user then login
     * @param googleLoginRequest google account info
     * @return LoginResponse
     */
    @Override
    public AuthenticatedUser loginWithGoogle(GoogleLoginRequest googleLoginRequest) {
        // call API to google to verify access token
        RestTemplate restTemplate = new RestTemplate();
        final String url = "https://www.googleapis.com/oauth2/v1/tokeninfo?access_token="
                + googleLoginRequest.getAccessToken();
        String result = restTemplate.getForObject(url, String.class);
        JSONObject credential = new JSONObject(result);

        if (credential.get("error") != null
                || !(boolean)credential.get("verified_email")
                || !credential.get("email").equals(googleLoginRequest.getEmail())) {
            // Invalid token
            return null;
        }
        User user = userRepository.getByEmail(googleLoginRequest.getEmail());
        // If user doesn't exist => insert
        if (user == null) {
            user = createDefault();
            user.setEmail(googleLoginRequest.getEmail());
            user.setFirstName(googleLoginRequest.getFirstName());
            user.setLastName(googleLoginRequest.getLastName());
            user.setPhoneNumber(googleLoginRequest.getPhoneNubmer());
            userRepository.insert(user);
        }
        // Create token
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(user.getEmail(), user.getPassword());
        String token = jwtToken.generateToken(userDetails, false);
        AuthenticatedUser authenticatedUser = new AuthenticatedUser();
        authenticatedUser.setUser(user);
        authenticatedUser.setAccessToken(token);
        return authenticatedUser;
    }


    /**
     * Check if email already exists
     * @param email to check
     * @return true or false if the email is already existed
     */
    @Override
    public boolean containsEmail(String email) {
        return userRepository.countByEmail(email) > 0;
    }
}
