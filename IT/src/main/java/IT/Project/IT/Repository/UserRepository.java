package IT.Project.IT.Repository;

import IT.Project.IT.Models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findUserByEmail(String email);


}
