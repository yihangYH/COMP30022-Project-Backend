package IT.Project.IT.Controller;

import IT.Project.IT.Models.Post;
import IT.Project.IT.Models.User;
import IT.Project.IT.Repository.ImageRepository;
import IT.Project.IT.Repository.PostRepository;
import IT.Project.IT.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://restaurant-at-unimelb-api.herokuapp.com", "https://restaurant-at-unimelb.herokuapp.com"})
public class UserController {

    private UserRepository userRepository;

    private PostRepository postRepository;

    private ImageRepository imageRepository;

    @GetMapping("/getUser/{userId}")
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
