package IT.Project.IT;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public void createPost(Post post){
        postRepository.insert(post);
    }

}
