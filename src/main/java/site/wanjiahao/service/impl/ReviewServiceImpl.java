package site.wanjiahao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.wanjiahao.mapper.ReviewMapper;
import site.wanjiahao.pojo.Product;
import site.wanjiahao.pojo.Review;
import site.wanjiahao.service.ReviewService;

import java.util.List;

@Service
@Transactional
@CacheConfig(cacheNames = "reviews")
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public Review save(Review review) {
        return reviewMapper.save(review);
    }

    @Cacheable(key = "'reviews-pid-' + #p0.id")
    @Override
    public List<Review> findByProduct(Product product) {
        return reviewMapper.findByProductOrderByIdDesc(product);
    }

    @Cacheable(key = "'reviews-count-pid-' + #p0.id")
    @Override
    public int findCount(Product product) {
        return reviewMapper.countByProduct(product);
    }

    @Cacheable(key = "'reviews-all-pid-' + #p0.id")
    @Override
    public List<Review> findAll(Product product) {
        return reviewMapper.findAll();
    }
}
