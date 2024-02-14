package greatBot.service.botUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"https://genuine-haupia-09d816.netlify.app/"})
public class UserInfoController {

    private final UserInfoSaver saver = new UserInfoSaver();

    @GetMapping("/update")
    public void updateUserInfo(
            @RequestParam("uid") long uid,
            @RequestParam("group") int group
    ) throws Exception {
        saver.updateInfo(uid, group);
    }
}
