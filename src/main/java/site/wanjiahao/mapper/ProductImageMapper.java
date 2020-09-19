package site.wanjiahao.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import site.wanjiahao.pojo.Product;
import site.wanjiahao.pojo.ProductImage;

import java.util.List;

public interface ProductImageMapper extends JpaRepository<ProductImage, Integer> {

    // 根据产品和类型查询所有的图片，并且按照降序排列
    List<ProductImage> findByProductAndTypeOrderByIdDesc(Product product, String type);
}
