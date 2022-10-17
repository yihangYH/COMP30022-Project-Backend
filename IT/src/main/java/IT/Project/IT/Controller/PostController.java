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
@CrossOrigin(origins = {"http://localhost:3000", "https://restaurant-at-unimelb-api.herokuapp.com", "https://restaurant-at-unimelb.herokuapp.com"})
public class PostController {

    private PostRepository postRepository;

    private FoodPostRepository foodPostRepository;
    private ImageRepository imageRepository;

    private UserRepository userRepository;

    @GetMapping("/getPost/{postId}")
    public Post getPost(@PathVariable String postId){
        // find the post via postId
        Post post = postRepository.findById(postId).get();
        // get post image Id
        String imageId = post.getImage();
        // use imageId to find the image string and convert it to byte array
        byte[] imageBytes = imageRepository.findById(imageId).get().getImage();
        // overwrite the post image filed with byte array
        post.setImage(new String(imageBytes));
        // init foodPosts array
        List<FoodPost> foodPosts = new ArrayList<>();
        // based on the post FoodPostsId field to find all the food post
        if(post.getFoodPostsId() != null){
            for(int i =0 ;i < post.getFoodPostsId().size(); i++){
                // find foodpost via id
                FoodPost foodPost = foodPostRepository.findById(post.getFoodPostsId().get(i)).get();
                // get foodPost image
                byte[] foodImgBytes = imageRepository.findById(foodPost.getFoodImage()).get().getImage();
                // overwrite foodpost image with byte array
                foodPost.setFoodImage(new String(foodImgBytes));
                // add this foodpost to the list
                foodPosts.add(foodPost);
            }
        }
        // overwrite post FoodPosts field.
        post.setFoodPosts(foodPosts);
        // return the post obj
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
        // find the user via userId
        User user = userRepository.findById(userId).get();
        // find the old post via postId
        Post oldPost = postRepository.findById(postId).get();
        // find old foodPostIds
        List<String> foodPostIds = oldPost.getFoodPostsId();
        // based on the foodPostsIds find all the foodPost and delete it
        for(String id : foodPostIds){
            FoodPost foodPost = foodPostRepository.findById(id).get();
            // delete foodPost image first
            imageRepository.deleteById(foodPost.getId());
            // delete foodPost
            foodPostRepository.deleteById(foodPost.getId());
        }
        // get post image byte
        byte[] imageBytes = post.getImage().getBytes();
        Image image = new Image();
        image.setImage(imageBytes);
        // save new image to the MongoDB
        imageRepository.insert(image);
        post.setImage(image.getId());
        post.setFoodPostsId(post.getFoodPostsId());
        // get post created date
        LocalDate dateObj = LocalDate.now();
        // format date to YYYY-MM-DD
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = dateObj.format(formatter);
        post.setDate(date);
        // save the new post
        postRepository.insert(post);
        List<String> newPostIds = user.getPostId();
        newPostIds.add(post.getId());
        user.setPostId(newPostIds);
        // remove the old post from postIds and save the user
        if(newPostIds.remove(postId)){
            userRepository.save(user);
        }
        // delete old post
        postRepository.deleteById(postId);
        // create new response and set to true
        Response response = new Response();
        response.setStatus("true");

        // return api response
        return response;

    }



}
