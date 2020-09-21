package site.wanjiahao.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import site.wanjiahao.pojo.Category;
import site.wanjiahao.pojo.Product;

import java.util.List;

public interface ProductMapper extends JpaRepository<Product, Integer> {

    // 分页查询某个分类下的商品
    Page<Product> findByCategory(Category category, Pageable pageable);

    // 查询某个分类下的商品
    List<Product> findByCategoryOrderByIdDesc(Category category);
}
