package site.wanjiahao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import site.wanjiahao.utils.PortUtils;

@SpringBootApplication
@EnableCaching // 开启redis缓存
@EnableElasticsearchRepositories(basePackages = "site.wanjiahao.es")
@EnableJpaRepositories(basePackages = {"site.wanjiahao.mapper", "site.wanjiahao.pojo"})
public class Application {

    static {
        PortUtils.checkPort(6379, "Redis", true);
        PortUtils.checkPort(9200, "ElasticSearch", true);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
