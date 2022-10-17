package IT.Project.IT;

import IT.Project.IT.Models.Post;
import IT.Project.IT.Models.User;
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
public class PostFunctionTest {

    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepositoryTest;
    @Autowired
    private PostRepository postRepositoryTest;

    @BeforeAll
    void dbSetup(){
        this.passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = this.passwordEncoder.encode("abcd1234");
        User user = new User("asd@demo.com",encodedPassword,"test","test",
                "631d91784100e55744e910a1", new ArrayList<>(), new ArrayList<>());

        userRepositoryTest.insert(user);

        Post post = new Post();
        post.setName("mock data");
        post.setLocation("mock location");
        post.setTitle("mock title");
        post.setComment("mock comment");
        post.setRate(5.0);
        post.setImage("631d91784100e55744e910a1");
        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = dateObj.format(formatter);
        post.setDate(date);
        post.setFoodPosts(new ArrayList<>());
        post.setFoodPostsId(new ArrayList<>());

        postRepositoryTest.insert(post);
        String postId = post.getId();
        List<String> postIds = new ArrayList<>();
        postIds.add(postId);
        user.setPostId(postIds);

        userRepositoryTest.save(user);

    }

    @Test
    void itShouldCreateAPost(){
        Post post = new Post();
        post.setName("mock data");
        post.setLocation("mock location");
        post.setTitle("mock title");
        post.setComment("mock comment");
        post.setRate(5.0);
        post.setImage("631d91784100e55744e910a1");
        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = dateObj.format(formatter);
        post.setDate(date);
        post.setFoodPosts(new ArrayList<>());
        post.setFoodPostsId(new ArrayList<>());

        postRepositoryTest.insert(post);
        assertThat(postRepositoryTest.findById(post.getId())).isNotNull();
    }

    @Test
    void itShouldFindPostById() {
        User resultUser = userRepositoryTest.findUserByEmail("asd@demo.com");
        assertThat(resultUser.getEmail()).isEqualTo("asd@demo.com");
        assertThat(resultUser.getUsername()).isEqualTo("test");
        assertThat(resultUser.getProfileImageID()).isEqualTo("631d91784100e55744e910a1");
        String postId = resultUser.getPostId().get(0);
        Post post = postRepositoryTest.findById(postId).get();
        assertThat(post).isNotNull();
        assertThat(post.getDate()).isNotNull();
        assertThat(post.getImage()).isEqualTo("631d91784100e55744e910a1");
        assertThat(post.getComment()).isEqualTo("mock comment");
        assertThat(post.getName()).isEqualTo("mock data");
        assertThat(post.getLocation()).isEqualTo("mock location");
        assertThat(post.getTitle()).isEqualTo("mock title");
        assertThat(post.getRate()).isEqualTo(5.0);
    }
}
