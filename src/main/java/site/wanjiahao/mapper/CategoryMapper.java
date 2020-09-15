package site.wanjiahao.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import site.wanjiahao.pojo.Category;

public interface CategoryMapper extends JpaRepository<Category, Integer> {
}
