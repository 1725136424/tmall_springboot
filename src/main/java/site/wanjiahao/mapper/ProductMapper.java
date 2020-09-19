package site.wanjiahao.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import site.wanjiahao.pojo.Category;
import site.wanjiahao.pojo.Product;

public interface ProductMapper extends JpaRepository<Product, Integer> {

    Page<Product> findByCategory(Category category, Pageable pageable);

}
