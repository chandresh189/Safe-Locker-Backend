package com.example.lockerSecurity.Controller;

import com.example.lockerSecurity.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.lockerSecurity.entity.AuthRequest;
import com.example.lockerSecurity.entity.User;
import com.example.lockerSecurity.service.UserService;
import com.example.lockerSecurity.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "*")
public class WelcomeController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    String globalPattern = "";

    @GetMapping("/")
    public String welcome() {
        return "Welcome";
    }

    @GetMapping("/getUser/{username}/{secretCode}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username, @PathVariable long secretCode) {
        System.out.println(secretCode);
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByUsername(username, secretCode));

    }

    //	@GetMapping("/getEyePattern/{username}")
//	public String getEyePatternByUsername(@PathVariable String username) {
//		return userService.getEyePatternByUsername(username);
//	}
    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest, HttpServletRequest request) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            String userName = authRequest.getUsername();
            String password = authRequest.getPassword();
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userName, password);

            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(auth);
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);

            User u = userRepository.findByUsername(userName);

            // Create a new session and add the security context.
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
            session.setAttribute("pattern", u.getEyePattern());
            globalPattern = u.getEyePattern();
        } catch (Exception e) {
            throw new Exception("invalid username/password");
        }
        return jwtUtil.generateToken(authRequest.getUsername());
    }

    @PostMapping("/sendOtp/{username}")
    public String sendOtp(@PathVariable("username") String username) {
        return userService.sendOtp(username);
    }

    @PostMapping("/saveUser")
    public User saveUser(@RequestBody User user) {
        System.out.println("SAVING USER");
        return userService.saveUser(user);
    }

    @GetMapping("/getEyePattern")
    public String getP(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
//        return session.getAttribute("pattern").toString();
        return globalPattern;
    }
}
