package site.wanjiahao.controller.fore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;
import site.wanjiahao.pojo.Category;
import site.wanjiahao.pojo.RESTFULResult;
import site.wanjiahao.pojo.User;
import site.wanjiahao.service.CategoryService;
import site.wanjiahao.service.ProductService;
import site.wanjiahao.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class ForeRESTController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private RESTFULResult restfulResult;

    @GetMapping("/fore_home")
    public List<Category> home() {
        List<Category> categories = categoryService.findAll();
        productService.fill(categories);
        productService.fillByRow(categories);
        categoryService.removeCategoryFromProduct(categories);
        return categories;
    }

    @PostMapping("/fore_register")
    public RESTFULResult register(@RequestBody User user) {
        try {
            String name =  user.getUsername();
            String password = user.getPassword();
            // 转义html非编码字段
            name = HtmlUtils.htmlEscape(name);
            user.setUsername(name);
            boolean exist = userService.isExist(name);
            if(exist){
                restfulResult.setMessage("用户名已经被使用,不能使用");
                restfulResult.setSuccess(false);
                return restfulResult;
            }
            user.setPassword(password);
            userService.save(user);
            restfulResult.setMessage("注册成功");
            restfulResult.setSuccess(true);
            return restfulResult;
        } catch (Exception e) {
            e.printStackTrace();
            restfulResult.setMessage("未知异常");
            restfulResult.setSuccess(false);
            return restfulResult;
        }
    }


    @PostMapping("/fore_login")
    public Object login(@RequestBody User user, HttpSession session) {
        String username =  user.getUsername();
        username = HtmlUtils.htmlEscape(username);
        User resUser = userService.findByUsernameAndPassword(username, user.getPassword());
        if(null == resUser){
            String message ="账号密码错误";
            restfulResult.setMessage(message);
            restfulResult.setSuccess(false);
        }
        else{
            session.setAttribute("user", user);
            restfulResult.setMessage("登录成功");
            restfulResult.setSuccess(true);
        }
        return restfulResult;
    }

}
