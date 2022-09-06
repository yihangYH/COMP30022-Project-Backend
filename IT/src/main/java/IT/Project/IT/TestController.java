package IT.Project.IT;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TestController {

    @GetMapping("api/v1/test")
    public String getTest(){
        return "this is a test";
    }
}
