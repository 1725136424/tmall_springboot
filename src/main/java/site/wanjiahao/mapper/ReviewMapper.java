package site.wanjiahao.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import site.wanjiahao.pojo.Product;
import site.wanjiahao.pojo.Review;

import java.util.List;

public interface ReviewMapper extends JpaRepository<Review, Integer> {

    // 根据产品id查询评论数量
    List<Review> findByProductOrderByIdDesc(Product product);

    // 计算当前产品下的评论数量
    int countByProduct(Product product);

}
