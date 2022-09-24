package IT.Project.IT.Controller;


import IT.Project.IT.Models.FoodPost;
import IT.Project.IT.Models.Image;
import IT.Project.IT.Models.Post;
import IT.Project.IT.Models.Response;
import IT.Project.IT.Repository.FoodPostRepository;
import IT.Project.IT.Repository.ImageRepository;
import IT.Project.IT.Repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://restaurant-at-unimelb-api.herokuapp.com", "https://restaurant-at-unimelb.herokuapp.com"})
public class FoodPostController {

    private FoodPostRepository foodPostRepository;

    private ImageRepository imageRepository;

    private PostRepository postRepository;

    @PostMapping("/creatFoodPost")
    public Response createFoodPost(@RequestBody FoodPost foodPost){
        // get image bytes from request body
        byte[] foodImageBytes = foodPost.getFoodImage().getBytes();
        // new a Image obj
        Image foodImage = new Image();
        // set img obj image to food image bytes
        foodImage.setImage(foodImageBytes);
        // insert a new image to image collection
        imageRepository.insert(foodImage);

        // set this img id to the food post
        foodPost.setFoodImage(foodImage.getId());

        // create a new food post
        foodPostRepository.insert(foodPost);

        // create response, try catch need to be added
        Response response = new Response();
        response.setId(foodPost.getId());
        response.setStatus("true");
        return response;
    }

    @DeleteMapping("/deleteFoodPost/{foodPostId}/{postId}")
    public Response deleteFoodPost(@PathVariable String foodPostId, @PathVariable String postId){
        FoodPost foodPost = foodPostRepository.findById(foodPostId).get();
        Post post = postRepository.findById(postId).get();
        List<String> foodPostIds = post.getFoodPostsId();
        if(foodPostIds.remove(foodPostId)){
            post.setFoodPostsId(foodPostIds);
            postRepository.save(post);
        }
        String imageId = foodPost.getFoodImage();
        imageRepository.deleteById(imageId);
        foodPostRepository.deleteById(foodPostId);

        Response response = new Response();
        response.setStatus("true");

        return response;
    }
}
