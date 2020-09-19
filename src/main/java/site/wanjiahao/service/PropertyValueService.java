package site.wanjiahao.service;

import site.wanjiahao.pojo.Product;
import site.wanjiahao.pojo.Property;
import site.wanjiahao.pojo.PropertyValue;

import java.util.List;

public interface PropertyValueService {

    PropertyValue update(PropertyValue propertyValue);

    void init(Product product);

    PropertyValue getByPropertyAndProduct(Product product, Property property);

    List<PropertyValue> list(Product product);
}
