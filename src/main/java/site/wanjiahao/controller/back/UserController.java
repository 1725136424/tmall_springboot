package site.wanjiahao.controller.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.wanjiahao.pojo.Page4Navigator;
import site.wanjiahao.pojo.User;
import site.wanjiahao.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public Page4Navigator<User> findAll(@RequestParam(name = "start", defaultValue = "0") int start,
                                        @RequestParam(name = "size", defaultValue = "5") int size) {
        start = start < 0? 0: start;
        return userService.findAll(start, size);
    }
}
