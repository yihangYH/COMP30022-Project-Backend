package IT.Project.IT;


import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;



@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepositoryTest;

    private RegisterController registerController;

    @BeforeAll
    void dbSetup(){
        User user = new User("asd@demo.com","abcd1234","test","test",
                "631d91784100e55744e910a1", new ArrayList<>(), new ArrayList<>());

        userRepositoryTest.insert(user);

    }


    @Test
    void itShouldFindUserByEmail() {
        User resultUser = userRepositoryTest.findUserByEmail("asd@demo.com");
        System.out.println(resultUser.getId());
        assertThat(resultUser.getEmail()).isEqualTo("asd@demo.com");
        assertThat(resultUser.getUsername()).isEqualTo("test");
        assertThat(resultUser.getProfileImageID()).isEqualTo("631d91784100e55744e910a1");
    }
}