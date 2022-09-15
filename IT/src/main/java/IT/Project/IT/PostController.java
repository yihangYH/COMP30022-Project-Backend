package IT.Project.IT;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {

    private PostRepository postRepository;

    private FoodPostRepository foodPostRepository;
    private ImageRepository imageRepository;

    @GetMapping("/getpost/{postId}")
    public Post getPost(@PathVariable String postId){
        Post post = postRepository.findById(postId).get();
        String imageId = post.getImage();
        byte[] imageBytes = imageRepository.findById(imageId).get().getImage();
        post.setImage(new String(imageBytes));
        List<FoodPost> foodPosts = new ArrayList<>();
        if(post.getFoodPostsId() != null){
            for(int i =0 ;i < post.getFoodPostsId().size(); i++){
                FoodPost foodPost = foodPostRepository.findById(post.getFoodPostsId().get(i)).get();
                byte[] foodImgBytes = imageRepository.findById(foodPost.getFoodImage()).get().getImage();
                foodPost.setFoodImage(new String(foodImgBytes));
                foodPosts.add(foodPost);
            }
        }
        post.setFoodPosts(foodPosts);
        return post;
    }



}
