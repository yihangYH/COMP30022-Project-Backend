package IT.Project.IT;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private UserRepository userRepository;

    private PostRepository postRepository;

    private ImageRepository imageRepository;

    @GetMapping("/getuser/{userId}")
    public User getUser(@PathVariable String userId){
        User user = userRepository.findById(userId).get();
        byte[] profileImage = imageRepository.findById(user.getProfileImageID()).get().getImage();
        user.setProfileImageID(new String(profileImage));
        List<Post> posts = new ArrayList<>();
        if(user.getPostId().size() != 0){
            for(int i = 0; i < user.getPostId().size(); i++){
                Post post = postRepository.findById(user.getPostId().get(i)).get();
                byte[] imageBytes = imageRepository.findById(post.getImage()).get().getImage();
                String base64Image = new String(imageBytes);
                post.setImage(base64Image);
                posts.add(post);

            }
            user.setPosts(posts);
        }
        return user;

    }
}
