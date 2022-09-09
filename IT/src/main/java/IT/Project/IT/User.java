package IT.Project.IT;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@Document
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private String password;

    private String username;

    private String favouriteRestaurant;

    private String profileImageID;

    public User(String email, String password, String username, String favouriteRestaurant, String profileImageID) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.favouriteRestaurant = favouriteRestaurant;
        this.profileImageID = profileImageID;
    }

    public String getProfileImageID() {
        return profileImageID;
    }

    public void setProfileImageID(String profileImageID) {
        this.profileImageID = profileImageID;
    }
// to be implemented
//    private List<Post> posts;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFavouriteRestaurant() {
        return favouriteRestaurant;
    }

    public void setFavouriteRestaurant(String favouriteRestaurant) {
        this.favouriteRestaurant = favouriteRestaurant;
    }
}
