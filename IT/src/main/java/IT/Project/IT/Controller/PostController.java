package IT.Project.IT.Controller;

import IT.Project.IT.Models.FoodPost;
import IT.Project.IT.Models.Image;
import IT.Project.IT.Models.Post;
import IT.Project.IT.Repository.PostRepository;
import IT.Project.IT.Repository.FoodPostRepository;
import IT.Project.IT.Repository.ImageRepository;
import IT.Project.IT.Models.Response;
import IT.Project.IT.Models.User;
import IT.Project.IT.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://restaurant-at-unimelb-api.herokuapp.com/"})
public class PostController {

    private PostRepository postRepository;

    private FoodPostRepository foodPostRepository;
    private ImageRepository imageRepository;

    private UserRepository userRepository;

    @GetMapping("/getPost/{postId}")
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

    @DeleteMapping("/deletePost/{userId}/{postId}")
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

    @PostMapping("/updatePost/{postId}/{userId}")
    public Response updatePost(@PathVariable String postId, @PathVariable String userId, @RequestBody Post post){
        User user = userRepository.findById(userId).get();
        Post oldPost = postRepository.findById(postId).get();
        List<String> foodPostIds = oldPost.getFoodPostsId();
        for(String id : foodPostIds){
            FoodPost foodPost = foodPostRepository.findById(id).get();
            imageRepository.deleteById(foodPost.getId());
            foodPostRepository.deleteById(foodPost.getId());
        }
        byte[] imageBytes = post.getImage().getBytes();
        Image image = new Image();
        image.setImage(imageBytes);
        imageRepository.insert(image);
        post.setImage(image.getId());
        post.setFoodPostsId(post.getFoodPostsId());
        // get post created date
        LocalDate dateObj = LocalDate.now();
        // format date to YYYY-MM-DD
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = dateObj.format(formatter);
        post.setDate(date);
        postRepository.insert(post);
        List<String> newPostIds = user.getPostId();
        newPostIds.add(post.getId());
        user.setPostId(newPostIds);
        if(newPostIds.remove(postId)){
            userRepository.save(user);
        }
        postRepository.deleteById(postId);
        Response response = new Response();
        response.setStatus("true");

        return response;

    }



}
