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
        // get user via Id
        User user = userRepository.findById(userId).get();
        // get user profile image
        byte[] profileImage = imageRepository.findById(user.getProfileImageID()).get().getImage();
        user.setProfileImageID(new String(profileImage));
        // get all the post which posted by this user
        List<Post> posts = new ArrayList<>();
        // get detailed post info
        if(user.getPostId().size() != 0){
            for(int i = 0; i < user.getPostId().size(); i++){
                Post post = postRepository.findById(user.getPostId().get(i)).get();
                byte[] imageBytes = imageRepository.findById(post.getImage()).get().getImage();
                String base64Image = new String(imageBytes);
                post.setImage(base64Image);
                posts.add(post);

            }
            // overwrite posts field
            user.setPosts(posts);
        }
        return user;

    }
}
