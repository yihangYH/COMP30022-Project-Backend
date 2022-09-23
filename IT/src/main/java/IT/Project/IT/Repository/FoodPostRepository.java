package IT.Project.IT.Repository;

import IT.Project.IT.Models.FoodPost;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoodPostRepository extends MongoRepository<FoodPost, String> {
}
