package site.wanjiahao.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import site.wanjiahao.pojo.Product;
import site.wanjiahao.pojo.Property;
import site.wanjiahao.pojo.PropertyValue;

import java.util.List;

public interface PropertyValueMapper extends JpaRepository<PropertyValue, Integer>{

    // 查询某个产品下的属性值
    List<PropertyValue> findByProductOrderByIdDesc(Product product);

    // 查询某个商品属性下的属性值
    PropertyValue getByPropertyAndProduct(Property property, Product product);
}
