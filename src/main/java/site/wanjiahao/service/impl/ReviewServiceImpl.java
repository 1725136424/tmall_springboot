package site.wanjiahao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.wanjiahao.mapper.ReviewMapper;
import site.wanjiahao.pojo.Product;
import site.wanjiahao.pojo.Review;
import site.wanjiahao.service.ReviewService;

import java.util.List;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public Review save(Review review) {
        return reviewMapper.save(review);
    }

    @Override
    public List<Review> findByProduct(Product product) {
        return reviewMapper.findByProductOrderByIdDesc(product);
    }

    @Override
    public int findCount(Product product) {
        return reviewMapper.countByProduct(product);
    }

    @Override
    public List<Review> findAll(Product product) {
        return reviewMapper.findAll();
    }
}
