package IT.Project.IT;


import IT.Project.IT.Controller.RegisterController;
import IT.Project.IT.Models.User;
import IT.Project.IT.Repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;



@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserFunctionTest {

    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepositoryTest;

    private RegisterController registerController;

    @BeforeAll
    void dbSetup(){
        this.passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = this.passwordEncoder.encode("abcd1234");
        User user = new User("asd@demo.com",encodedPassword,"test","test",
                "631d91784100e55744e910a1", new ArrayList<>(), new ArrayList<>());

        userRepositoryTest.insert(user);

    }

    @Test
    void itShouldCreateAUser(){
        this.passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = this.passwordEncoder.encode("abcd1234");
        User user = new User("mock@demo.com",encodedPassword,"test","test",
                "631d91784100e55744e910a1", new ArrayList<>(), new ArrayList<>());

        userRepositoryTest.insert(user);
        assertThat(userRepositoryTest.findUserByEmail("abcd1234")).isNotNull();

    }

    @Test
    void itShouldFindUserByEmail() {
        User resultUser = userRepositoryTest.findUserByEmail("asd@demo.com");
        assertThat(resultUser.getEmail()).isEqualTo("asd@demo.com");
        assertThat(resultUser.getUsername()).isEqualTo("test");
        assertThat(resultUser.getProfileImageID()).isEqualTo("631d91784100e55744e910a1");
    }

    @Test
    void passwordShouldBeEncoded() {
        User resultUser = userRepositoryTest.findUserByEmail("asd@demo.com");
        String password = "abcd1234";
        assertThat(passwordEncoder.matches(password, resultUser.getPassword())).isTrue();
    }

    @Test
    void emailShouldNotBeDuplicated(){
        assertThat(userRepositoryTest.findUserByEmail("zxc@demo.com")).isNull();
    }

    @Test
    void duplicatedEmail(){
        assertThat(userRepositoryTest.findUserByEmail("asd@demo.com")).isNotNull();
    }
}