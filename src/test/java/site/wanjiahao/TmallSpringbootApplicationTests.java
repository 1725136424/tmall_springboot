package site.wanjiahao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.wanjiahao.pojo.Category;
import site.wanjiahao.service.CategoryService;

import java.util.List;

@SpringBootTest
class TmallSpringbootApplicationTests {

    @Autowired
    private CategoryService categoryService;

    @Test
    void contextLoads() {
        List<Category> categories = categoryService.findAll();
        System.out.println(categories);
    }

}
