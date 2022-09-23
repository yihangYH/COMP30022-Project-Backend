package IT.Project.IT.Service;

import IT.Project.IT.Models.Image;
import IT.Project.IT.Repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRespository;

    public void insertImage(Image image){
        imageRespository.insert(image);

//        Response response = new Response();
//        response.setStatus("true");
//        return  response;
    }
}
