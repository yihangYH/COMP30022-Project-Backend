package IT.Project.IT;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class FoodPostController {

    private FoodPostRepository foodPostRepository;

    private ImageRepository imageRepository;

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
        response.setId(foodPost.getId());
        return response;
    }
}
