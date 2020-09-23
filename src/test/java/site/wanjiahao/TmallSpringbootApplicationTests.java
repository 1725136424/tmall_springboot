package site.wanjiahao;

import com.sun.corba.se.spi.ior.ObjectKey;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import site.wanjiahao.pojo.Category;
import site.wanjiahao.pojo.User;
import site.wanjiahao.service.CategoryService;
import java.util.List;

@SpringBootTest
class TmallSpringbootApplicationTests {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CredentialsMatcher credentialsMatcher;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void contextLoads() {
        List<Category> categories = categoryService.findAll();
        System.out.println(categories);
    }

    @Test
    void test1() {
        String haogege = new Md5Hash("1725136424", "haodada", 2).toString();
        System.out.println(haogege);
    }
}
