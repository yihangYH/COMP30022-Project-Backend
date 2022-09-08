package IT.Project.IT;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public Response createUser(User user){
        userRepository.insert(user);

        Response response = new Response();
        response.setStatus("true");
        return  response;
    }
}
