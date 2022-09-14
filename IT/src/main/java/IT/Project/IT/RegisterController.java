package IT.Project.IT;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class RegisterController {
    private PasswordEncoder passwordEncoder;
    private UserService userService;
    @PostMapping("/register")
    public Response register(@RequestBody Register register){
        // encode password
        this.passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = this.passwordEncoder.encode(register.getPassword());
    }
}
