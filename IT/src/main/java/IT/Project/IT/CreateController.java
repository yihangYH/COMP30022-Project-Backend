package IT.Project.IT;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CreateController {
    private ImageService imageService;

    private PostService postService;

    private UserRepository userRepository;
    @PostMapping("/create/{userId}")
    public Post create(@RequestBody Post post, @PathVariable String userId){

        // get res image bytes
        byte[] imageBytes = post.getImage().getBytes();
        // create a new img obj
        Image resImg = new Image();
        // save this new img to DB collection
        resImg.setImage(imageBytes);
        imageService.insertImage(resImg);

        // get new img id and set to post img
        post.setImage(resImg.getId());

        // get post created date
        LocalDate dateObj = LocalDate.now();
        // format date to YYYY-MM-DD
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = dateObj.format(formatter);
        // set post date
        post.setDate(date);
        // create an empty FoodPost array, used to store FoodPost when get request is called
        List<FoodPost> foodPosts = new ArrayList<>();
        List<String> foodPostIds = new ArrayList<>();
        for(int i = 0; i < post.getFoodPostsId().size(); i++ ){
            foodPostIds.add(post.getFoodPostsId().get(i));
        }
        post.setFoodPostsId(post.getFoodPostsId());
        post.setFoodPosts(foodPosts);

        // create a post and save to DB collection
        postService.createPost(post);

        // find the user id who created this post
        User user = userRepository.findById(userId).get();

        // get original post id
        List<String> ids = user.getPostId();

        // add newly created post id to this list
        ids.add(post.getId());
        user.setPostId(ids);
        // update user data in db
        userRepository.save(user);

        // create response, try catch need to be implemented
        Response response = new Response();
        return post;
    }
}
