package site.wanjiahao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.wanjiahao.mapper.PropertyValueMapper;
import site.wanjiahao.pojo.Product;
import site.wanjiahao.pojo.Property;
import site.wanjiahao.pojo.PropertyValue;
import site.wanjiahao.service.PropertyService;
import site.wanjiahao.service.PropertyValueService;

import java.util.List;

@Service
@Transactional
public class PropertyValueServiceImpl implements PropertyValueService {


    @Autowired
    private PropertyValueMapper propertyValueMapper;

    @Autowired
    private PropertyService propertyService;

    @Override
    public PropertyValue update(PropertyValue propertyValue) {
        return propertyValueMapper.save(propertyValue);
    }

    @Override
    public void init(Product product) {
        List<Property> properties = propertyService.findByCategory(product.getCategory());
        for (Property property : properties) {
            PropertyValue propertyValue = getByPropertyAndProduct(product, property);
            if (null == propertyValue) {
                propertyValue = new PropertyValue();
                propertyValue.setProduct(product);
                propertyValue.setProperty(property);
                propertyValueMapper.save(propertyValue);
            }
        }
    }

    @Override
    public PropertyValue getByPropertyAndProduct(Product product, Property property) {
        return propertyValueMapper.getByPropertyAndProduct(property, product);
    }

    @Override
    public List<PropertyValue> list(Product product) {
        return propertyValueMapper.findByProductOrderByIdDesc(product);
    }
}
