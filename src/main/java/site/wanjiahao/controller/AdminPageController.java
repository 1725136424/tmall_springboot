package site.wanjiahao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {

    @GetMapping("/admin")
    public String admin() {
        return "redirect:/admin_category_list";
    }

    @GetMapping("/admin_category_list")
    public String listCategory() {
        return "admin/listCategory";
    }
}
