package site.wanjiahao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.wanjiahao.pojo.Category;
import site.wanjiahao.pojo.Page4Navigator;
import site.wanjiahao.service.CategoryService;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public Page4Navigator<Category> findAll(@RequestParam(name = "start", defaultValue = "0") int start,
                                            @RequestParam(name = "size", defaultValue = "5") int size) {

        // 逻辑判断
        start = start < 0? 0: start;
        return categoryService.findAll(start, size);
    }

}
