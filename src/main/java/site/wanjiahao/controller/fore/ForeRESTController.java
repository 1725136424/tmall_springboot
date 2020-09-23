package site.wanjiahao.controller.fore;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import site.wanjiahao.comparator.*;
import site.wanjiahao.field.RealmConst;
import site.wanjiahao.pojo.*;
import site.wanjiahao.service.*;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderService orderService;

    @Value("${shiro.credentials-matcher.hashAlgorithmName}")
    private String hashAlgorithmName;

    @Value("${shiro.credentials-matcher.hashIterations}")
    private int hashIterations;

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
            // 加密处理
            SimpleHash hash = new SimpleHash(hashAlgorithmName, password, RealmConst.SALT,
                    hashIterations);
            user.setPassword(hash.toString());
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
        Subject subject = SecurityUtils.getSubject();
        // 创建一个令牌
        UsernamePasswordToken token = new UsernamePasswordToken(username, user.getPassword());
        try {
            subject.login(token);
            User resUser = userService.findByName(username);
            session.setAttribute("user", resUser);
            restfulResult.setSuccess(true);
            restfulResult.setMessage("登录成功");
        } catch (UnknownAccountException e) {
            restfulResult.setSuccess(false);
            restfulResult.setMessage("账号错误");
        } catch (IncorrectCredentialsException e) {
            restfulResult.setSuccess(false);
            restfulResult.setMessage("密码错误");
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
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
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

    @GetMapping("/fore_buyone")
    public Object buyOne(int pid, int num, HttpSession session) {
        return buyOneAndAddCart(pid,num,session);
    }

    @GetMapping("/fore_buy")
    public Map<String, Object> buy(String[] oiid, HttpSession session){
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        for (String strid : oiid) {
            int id = Integer.parseInt(strid);
            OrderItem oi= orderItemService.findOne(id);
            total = total.add(oi.getProduct().getPromotePrice().multiply(BigDecimal.valueOf(oi.getNum())));
            orderItems.add(oi);
        }
        // 保存session中方便日后好创建订单，结算
        productImageService.setFirstProductImagesOnOrderItems(orderItems);
        session.setAttribute("ois", orderItems);
        Map<String,Object> map = new HashMap<>();
        map.put("orderItems", orderItems);
        map.put("total", total);
        return map;
    }

    @GetMapping("/fore_addCart")
    public RESTFULResult addCart(int pid, int num, HttpSession session) {
        try {
            // 订单项编号
            buyOneAndAddCart(pid, num, session);
            restfulResult.setMessage("加入成功");
            restfulResult.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            restfulResult.setMessage("加入成功");
            restfulResult.setSuccess(true);
        }
        return restfulResult;
    }

    // 查看购物车
    @GetMapping("/fore_cart")
    public List<OrderItem> cart(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<OrderItem> ois = orderItemService.findByUserAndOrderIsNull(user);
        productImageService.setFirstProductImagesOnOrderItems(ois);
        return ois;
    }

    @GetMapping("fore_changeOrderItem")
    public Object changeOrderItem( HttpSession session, int pid, int num) {
        try {
            User user =(User)  session.getAttribute("user");
            List<OrderItem> ois = orderItemService.findByUserAndOrderIsNull(user);
            for (OrderItem oi : ois) {
                if(oi.getProduct().getId().equals(pid)){
                    oi.setNum(num);
                    orderItemService.update(oi);
                    break;
                }
            }
            restfulResult.setMessage("修改成功");
            restfulResult.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            restfulResult.setMessage("修改失败");
            restfulResult.setSuccess(false);
        }
        return restfulResult;
    }

    @GetMapping("fore_deleteOrderItem")
    public Object deleteOrderItem(int oiid){
        try {
            orderItemService.delete(oiid);
            restfulResult.setMessage("删除成功");
            restfulResult.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            restfulResult.setMessage("删除失败");
            restfulResult.setSuccess(false);
        }
        return restfulResult;
    }


    @PostMapping("/fore_createOrder")
    public Map<String, Object> createOrder(@RequestBody Order order, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String orderCode =
                new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) +  new Random().nextInt(10000);
        order.setOrderCode(orderCode);
        order.setCreateDate(new Date());
        order.setUser(user);
        order.setStatus(OrderService.waitPay);
        List<OrderItem> ois= (List<OrderItem>) session.getAttribute("ois");
        BigDecimal total = orderService.save(order,ois);
        Map<String,Object> map = new HashMap<>();
        map.put("oid", order.getId());
        map.put("total", total);
        return map;
    }

    @GetMapping("fore_payed")
    public Order payed(int oid) {
        Order order = orderService.findOne(oid);
        order.setStatus(OrderService.waitDelivery);
        order.setPayDate(new Date());
        orderService.update(order);
        return order;
    }


    @GetMapping("fore_bought")
    public Object bought(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Order> os = orderService.listByUserWithoutDelete(user);
        orderItemService.fill(os);
//        orderService.removeOrderFromOrderItem(os);
        return os;
    }

    @GetMapping("fore_confirmPay")
    public Object confirmPay(int oid) {
        Order o = orderService.findOne(oid);
        orderItemService.fill(o);
        orderService.cacl(o);
//        orderService.removeOrderFromOrderItem(o);
        return o;
    }

    @GetMapping("fore_orderConfirmed")
    public Object orderConfirmed(int oid, HttpSession session) {
        try {
            Order o = orderService.findOne(oid);
            o.setStatus(OrderService.waitReview);
            o.setConfirmDate(new Date());
            orderService.update(o);
            session.removeAttribute("ois");
            restfulResult.setMessage("订单确认成功");
            restfulResult.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            restfulResult.setMessage("订单确认失败");
            restfulResult.setSuccess(false);
        }

        return restfulResult;
    }

    @PutMapping("fore_deleteOrder")
    public Object deleteOrder(int oid){
        try {
            Order o = orderService.findOne(oid);
            o.setStatus(OrderService.delete);
            orderService.update(o);
            restfulResult.setMessage("删除成功");
            restfulResult.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            restfulResult.setMessage("删除失败");
            restfulResult.setSuccess(false);
        }
        return restfulResult;
    }

    @GetMapping("fore_review")
    public Object review(int oid) {
        Order o = orderService.findOne(oid);
        orderItemService.fill(o);
//        orderService.removeOrderFromOrderItem(o);
        Product p = o.getOrderItems().get(0).getProduct();
        List<Review> reviews = reviewService.findByProduct(p);
        productService.setSaleAndReviewNumber(p);
        Map<String,Object> map = new HashMap<>();
        map.put("p", p);
        map.put("o", o);
        map.put("reviews", reviews);
        return map;
    }

    @PostMapping("fore_doreview")
    public Object doReview( HttpSession session,int oid,int pid,String content) {
        try {
            Order o = orderService.findOne(oid);
            o.setStatus(OrderService.finish);
            orderService.update(o);
            Product p = productService.findOne(pid);
            content = HtmlUtils.htmlEscape(content);
            User user = (User) session.getAttribute("user");
            Review review = new Review();
            review.setContent(content);
            review.setProduct(p);
            review.setCreateDate(new Date());
            review.setUser(user);
            reviewService.save(review);
            restfulResult.setMessage("删除成功");
            restfulResult.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            restfulResult.setMessage("删除失败");
            restfulResult.setSuccess(false);
        }
        return restfulResult;
    }

    // 购物流程，当前商品没有在订单项中，新建，有就增加数量
    private int buyOneAndAddCart(int pid, int num, HttpSession session) {
        // 查询当前产品
        Product product = productService.findOne(pid);
        int oiid = 0;
        // 查询当前用户
        User user =(User)  session.getAttribute("user");
        boolean found = false;
        // 查询当前用户未生成订单的订单项
        List<OrderItem> ois = orderItemService.findByUserAndOrderIsNull(user);
        for (OrderItem oi : ois) {
            if(oi.getProduct().getId().equals(product.getId())){
                oi.setNum(oi.getNum() + num);
                orderItemService.update(oi);
                found = true;
                oiid = oi.getId();
                break;
            }
        }

        if(!found){
            OrderItem oi = new OrderItem();
            oi.setUser(user);
            oi.setProduct(product);
            oi.setNum(num);
            orderItemService.save(oi);
            oiid = oi.getId();
        }
        return oiid;
    }
}
