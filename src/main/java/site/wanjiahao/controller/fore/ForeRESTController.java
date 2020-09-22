package site.wanjiahao.controller.fore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import site.wanjiahao.comparator.*;
import site.wanjiahao.pojo.*;
import site.wanjiahao.service.*;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private PropertyValueService propertyValueService;

    @Autowired
    private ReviewService reviewService;

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

    @GetMapping("/fore_product/{pid}")
    public Map<String, Object> product(@PathVariable("pid") int pid) {
        Product product = productService.findOne(pid);

        List<ProductImage> productSingleImages = productImageService.listSingleProductImage(product);
        List<ProductImage> productDetailImages = productImageService.listDetailProductImage(product);
        product.setProductSingleImages(productSingleImages);
        product.setProductDetailImages(productDetailImages);

        List<PropertyValue> pvs = propertyValueService.list(product);
        List<Review> reviews = reviewService.findByProduct(product);
        productService.setSaleAndReviewNumber(product);
        productImageService.setFirstProductImages(product);

        Map<String,Object> map= new HashMap<>();
        map.put("product", product);
        map.put("pvs", pvs);
        map.put("reviews", reviews);

        return map;
    }

    @GetMapping("/fore_checkLogin")
    public RESTFULResult checkLogin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            restfulResult.setMessage("已登录");
            restfulResult.setSuccess(true);
        } else {
            restfulResult.setMessage("未登录");
            restfulResult.setSuccess(false);
        }
        return restfulResult;
    }


    @GetMapping("fore_category/{cid}")
    public Object category(@PathVariable int cid,String sort) {
        Category c = categoryService.findOne(cid);
        productService.fill(c);
        productService.setSaleAndReviewNumber(c.getProducts());
        categoryService.removeCategoryFromProduct(c);
        if(null!=sort){
            switch(sort){
                case "review":
                    c.getProducts().sort(new ProductReviewComparator());
                    break;
                case "date" :
                    c.getProducts().sort(new ProductDateComparator());
                    break;

                case "saleCount" :
                    c.getProducts().sort(new ProductSaleCountComparator());
                    break;

                case "price":
                    c.getProducts().sort(new ProductPriceComparator());
                    break;

                case "all":
                    c.getProducts().sort(new ProductAllComparator());
                    break;
            }
        }

        return c;
    }

    @PostMapping("fore_search")
    public Object search( String keyword){
        if(null == keyword) {
            keyword = "";
        }
        List<Product> ps= productService.search(keyword,0,20);
        productImageService.setFirstProductImages(ps);
        productService.setSaleAndReviewNumber(ps);
        return ps;
    }

}
