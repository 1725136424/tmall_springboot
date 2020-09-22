package site.wanjiahao.controller.fore;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

// 跳转控制器
@Controller
public class ForePageController {

    @GetMapping("/")
    public String index(){
        return "redirect:home";
    }

    @GetMapping("/home")
    public String home(){
        return "fore/home";
    }

    @GetMapping("/register")
    public String register(){
        return "fore/register";
    }

    @GetMapping("/registerSuccess")
    public String registerSuccess(){
        return "fore/registerSuccess";
    }

    @GetMapping("/login")
    public String login(){
        return "fore/login";
    }

    @GetMapping("/fore_logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:home";
    }

    @GetMapping("/product")
    public String product(){
        return "fore/product";
    }

    @GetMapping("/category")
    public String category(){
        return "fore/category";
    }

    @GetMapping("/search")
    public String searchResult(){
        return "fore/search";
    }

    @GetMapping("/buy")
    public String buy(){
        return "fore/buy";
    }

    @GetMapping("/cart")
    public String cart(){
        return "fore/cart";
    }

    @GetMapping("/alipay")
    public String alipay(){
        return "fore/alipay";
    }

    @GetMapping("/payed")
    public String payed(){
        return "fore/payed";
    }

    @GetMapping("/bought")
    public String bought(){
        return "fore/bought";
    }

    @GetMapping("/confirmPay")
    public String confirmPay(){
        return "fore/confirmPay";
    }

    @GetMapping("/orderConfirmed")
    public String orderConfirmed(){
        return "fore/orderConfirmed";
    }

    @GetMapping("/review")
    public String review(){
        return "fore/review";
    }
}
