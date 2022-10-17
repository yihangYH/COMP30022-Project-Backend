package IT.Project.IT.Controller;


import IT.Project.IT.Models.Login;
import IT.Project.IT.Models.Response;
import IT.Project.IT.Models.User;
import IT.Project.IT.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://restaurant-at-unimelb-api.herokuapp.com", "https://restaurant-at-unimelb.herokuapp.com"})
public class LoginController {

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public Response login(@RequestBody Login login){
        // init response obj
        Response response = new Response();
        // try to get user info from Mongodb
        try{
            // find user by user Email
            User user = userRepository.findUserByEmail(login.getEmail());
            // check if user exists and if the password is matching
            if(user != null && passwordEncoder.matches(login.getPassword(),user.getPassword())){
                // if user exists and password is matching, set response true and
                // id = userId
                response.setStatus("true");
                response.setId(user.getId());
            }else{
                // else set response to false
                response.setStatus("false");
            }
        }catch(Exception ex){
            // if any exception occur, set response to false
            response.setStatus("false");
        }


        return response;

    }
}
