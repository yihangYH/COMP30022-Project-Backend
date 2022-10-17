package IT.Project.IT;

import IT.Project.IT.Models.FoodPost;
import IT.Project.IT.Models.Post;
import IT.Project.IT.Models.User;
import IT.Project.IT.Repository.FoodPostRepository;
import IT.Project.IT.Repository.PostRepository;
import IT.Project.IT.Repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;



@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FoodPostFunctionTest {

    @Autowired
    private FoodPostRepository foodPostRepositoryTest;

    @Autowired
    private PostRepository postRepositoryTest;

    private String foodPostIdMock;

    @BeforeAll
    void dbSetup(){
        FoodPost foodPost = new FoodPost();
        foodPost.setFoodImage("mock image");
        foodPost.setComment("mock comment");
        foodPost.setName("mock name");
        foodPost.setRate(5.0);

        foodPostRepositoryTest.insert(foodPost);
        foodPostIdMock = foodPost.getId();
    }

    @Test
    void foodPostShouldFoundById(){
        FoodPost foodPost = foodPostRepositoryTest.findById(foodPostIdMock).get();
        assertThat(foodPost).isNotNull();
        assertThat(foodPost.getFoodImage()).
                isNotNull().
                isNotEmpty().
                isEqualTo("mock image");
        assertThat(foodPost.getRate()).isNotNull().isEqualTo(5.0);
        assertThat(foodPost.getComment()).isNotNull().isEqualTo("mock comment");
        assertThat(foodPost.getName()).isNotNull().isEqualTo("mock name");
        assertThat(foodPost.getFoodImage()).isNotNull().isEqualTo("mock image");

    }


}
