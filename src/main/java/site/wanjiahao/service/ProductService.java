package site.wanjiahao.service;

import site.wanjiahao.pojo.Category;
import site.wanjiahao.pojo.Page4Navigator;
import site.wanjiahao.pojo.Product;
import site.wanjiahao.pojo.Property;

public interface ProductService {

    Page4Navigator<Product> findAll(int cid, int start, int size, int navigateNum);

    Page4Navigator<Product> findAll(int cid, int start, int size);

    Product findOne(int id);

    Product save(Product product);

    void delete(int id);

    Product update(Product product);
}
