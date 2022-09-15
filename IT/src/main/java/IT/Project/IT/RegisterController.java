package IT.Project.IT;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;





import java.util.ArrayList;
import java.util.List;


@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class RegisterController {
    private PasswordEncoder passwordEncoder;

    private UserService userService;


    private ImageService imageService;

    private UserRepository userRepository;

    @PostMapping("/register")
    public Response register(@RequestBody Register register){

        Response response = new Response();
        // check if the email already been used
        if(userRepository.findUserByEmail(register.getEmail()) != null) {
            response.setStatus("false");
            return response;
        }
        // encode password
        this.passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = this.passwordEncoder.encode(register.getPassword());

        // convert image to byte[]
        byte[] imageBytes = register.getImage().getBytes();

        // save image to db
        Image profileImage = new Image();
        profileImage.setImage(imageBytes);
        imageService.insertImage(profileImage);
        List<String> postIds = new ArrayList<>();
        List<Post> posts = new ArrayList<>();
        // map field to user from request body
        User user = new User(register.getEmail(),
                encodedPassword,
                register.getUsername(),
                register.getFavouriteRestaurant(),
                profileImage.getId(),
                postIds,
                posts
                );

        // save user to db and get response
        return userService.createUser(user);
    }

//    @PostMapping("/find")
//    public Image find(@RequestBody Register register){
//        Response res= new Response();
//        Image img = new Image();
//        img.setImage(imageRespository.findById("6319d333e8b0773993541ae0").get().getImage());
//        byte[] test = img.getImage();
//        System.out.println(test);
//        return img;
//    }
}
