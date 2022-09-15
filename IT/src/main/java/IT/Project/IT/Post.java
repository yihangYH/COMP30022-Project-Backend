package IT.Project.IT;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class Post {

    @Id
    private String id;

    private String name;

    private String location;

    private String title;

    private String comment;

    private double rate;

    private String image;

    private String date;

    private List<String> foodPostsId;

    private List<FoodPost> foodPosts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getFoodPostsId() {
        return foodPostsId;
    }

    public void setFoodPostsId(List<String> foodPostsId) {
        this.foodPostsId = foodPostsId;
    }

    public List<FoodPost> getFoodPosts() {
        return foodPosts;
    }

    public void setFoodPosts(List<FoodPost> foodPosts) {
        this.foodPosts = foodPosts;
    }
}
