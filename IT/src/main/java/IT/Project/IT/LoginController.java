package IT.Project.IT;


import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public Response login(@RequestBody Login login){
        Response response = new Response();
        User user = userRepository.findUserByEmail(login.getEmail());
        if(user != null && passwordEncoder.matches(login.getPassword(),user.getPassword())){
            response.setStatus("true");
            response.setId(user.getId());
        }else{
            response.setStatus("false");
        }
        return response;

    }
}
