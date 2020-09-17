package site.wanjiahao.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import site.wanjiahao.pojo.Category;
import site.wanjiahao.pojo.Property;

public interface PropertyMapper extends JpaRepository<Property, Integer> {

    // jpa自定义查询方法
    Page<Property> findByCategory(Category category, Pageable pageable);

}
