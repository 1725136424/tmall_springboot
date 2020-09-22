package site.wanjiahao.service;

import site.wanjiahao.pojo.Category;
import site.wanjiahao.pojo.Page4Navigator;
import site.wanjiahao.pojo.Product;

import java.util.List;

public interface ProductService {

    Page4Navigator<Product> findAll(int cid, int start, int size, int navigateNum);

    Page4Navigator<Product> findAll(int cid, int start, int size);

    List<Product> findByCategory(Category category);

    Product findOne(int id);

    Product save(Product product);

    void delete(int id);

    Product update(Product product);

    void fill(List<Category> categories);

    void fill(Category category);

    void fillByRow(List<Category> categories);

    void setSaleAndReviewNumber(Product product);

    void setSaleAndReviewNumber(List<Product> products);

    List<Product> search(String keyword, int i, int i1);
}
