package IT.Project.IT;

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
