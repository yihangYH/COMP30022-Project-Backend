package IT.Project.IT;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {

    private PostRepository postRepository;

    private FoodPostRepository foodPostRepository;
    private ImageRepository imageRepository;

    private UserRepository userRepository;

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

    @DeleteMapping("/deletepost/{userId}/{postId}")
    public Response deletePost(@PathVariable String userId, @PathVariable String postId){
        // get post based on the postId
        Post post = postRepository.findById(postId).get();
        // get user based on the userId
        User user = userRepository.findById(userId).get();
        // get all the postId from user
        List<String> posts = user.getPostId();
        // remove postId from user PostId array
        if(posts.remove(postId)){
            user.setPostId(posts);
            // update user collection  with the latest data
            userRepository.save(user);
        }

        // init a list to store the foodPostId which related to the post user want to delete
        List<String> foodPostId = new ArrayList<>();

        // get all the food id from post
        for(String id : post.getFoodPostsId()){
            foodPostId.add(id);
        }
        // delete all the food and food image
        for(String id : foodPostId){
            FoodPost foodPost = foodPostRepository.findById(id).get();
            imageRepository.deleteById(foodPost.getFoodImage());
            foodPostRepository.deleteById(id);
        }
        // delete post image;
        imageRepository.deleteById(post.getImage());
        // delete the post
        postRepository.deleteById(postId);

        Response response = new Response();
        response.setStatus("true");
        return response;
    }



}
