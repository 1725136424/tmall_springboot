package site.wanjiahao.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import site.wanjiahao.pojo.Product;

public interface ProductESMapper extends ElasticsearchRepository<Product, Integer> {
}
