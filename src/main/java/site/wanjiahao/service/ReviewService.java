package site.wanjiahao.service;

import site.wanjiahao.pojo.Product;
import site.wanjiahao.pojo.Review;

import java.util.List;

public interface ReviewService {

    Review save(Review review);

    List<Review> findByProduct(Product product);

    int findCount(Product product);

    List<Review> findAll(Product product);
}
