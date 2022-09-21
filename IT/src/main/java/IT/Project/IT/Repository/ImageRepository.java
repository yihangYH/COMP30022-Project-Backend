package IT.Project.IT.Repository;

import IT.Project.IT.Models.Image;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ImageRepository extends MongoRepository<Image, String> {
    @Query
    Image findImageById(ObjectId _id);


}
