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
//        this.passwordEncoder = new BCryptPasswordEncoder();
//        String encodedPassword = this.passwordEncoder.encode(register.getPassword());
//        boolean test = this.passwordEncoder.matches(register.getPassword(), encodedPassword);
//
//        User user = new User();
//        user.setEmail(register.getEmail());
//        user.setPassword(encodedPassword);
//        user.setFavouriteRestaurant(register.getFavouriteRestaurant());
//        user.setUsername(register.getUsername());
//        byte[] bytes = register.getImage().getBytes();
//        user.setTestFile(bytes);
//        System.out.println(encodedPassword);
//        System.out.println(test);
//        String s = new String(bytes);
//        System.out.println(s);
//        Response response = new Response();
//        response = userService.createUser(user);
//        System.out.println(user.getId());
//        return response;
    }
}
