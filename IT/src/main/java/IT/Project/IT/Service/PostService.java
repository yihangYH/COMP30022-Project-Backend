package IT.Project.IT.Service;

import IT.Project.IT.Models.Post;
import IT.Project.IT.Repository.PostRepository;
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
