package IT.Project.IT;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoodPostRepository extends MongoRepository<FoodPost, String> {
}
