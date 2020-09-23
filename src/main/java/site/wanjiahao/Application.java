package site.wanjiahao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import site.wanjiahao.utils.PortUtils;

@SpringBootApplication
@EnableCaching // 开启redis缓存
public class Application {

    static {
        PortUtils.checkPort(6379, "Redis", true);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
