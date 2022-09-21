package IT.Project.IT.Repository;

import IT.Project.IT.Models.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {
}
