package site.wanjiahao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.wanjiahao.mapper.PropertyValueMapper;
import site.wanjiahao.pojo.Product;
import site.wanjiahao.pojo.Property;
import site.wanjiahao.pojo.PropertyValue;
import site.wanjiahao.service.PropertyService;
import site.wanjiahao.service.PropertyValueService;
import site.wanjiahao.utils.SpringContextUtil;

import java.util.List;

@Service
@Transactional
@CacheConfig(cacheNames = "propertyValue")
public class PropertyValueServiceImpl implements PropertyValueService {


    @Autowired
    private PropertyValueMapper propertyValueMapper;

    @Autowired
    private PropertyService propertyService;

    @CacheEvict(allEntries = true)
    @Override
    public PropertyValue update(PropertyValue propertyValue) {
        return propertyValueMapper.save(propertyValue);
    }

    @Override
    public void init(Product product) {
        PropertyService propertyService = SpringContextUtil.getBean(PropertyService.class);
        PropertyValueService propertyValueService =
                SpringContextUtil.getBean(PropertyValueService.class);
        List<Property> properties = propertyService.findByCategory(product.getCategory());
        for (Property property : properties) {
            PropertyValue propertyValue =
                    propertyValueService.getByPropertyAndProduct(product, property);
            if (null == propertyValue) {
                propertyValue = new PropertyValue();
                propertyValue.setProduct(product);
                propertyValue.setProperty(property);
                propertyValueService.save(propertyValue);
            }
        }
    }

    @Cacheable(key = "'propertyValue-pid-' + #p0.id + '-ptid-' + #p1.id")
    @Override
    public PropertyValue getByPropertyAndProduct(Product product, Property property) {
        return propertyValueMapper.getByPropertyAndProduct(property, product);
    }

    @Cacheable(key = "'propertyValue-all'")
    @Override
    public List<PropertyValue> list(Product product) {
        return propertyValueMapper.findByProductOrderByIdDesc(product);
    }

    @CacheEvict(allEntries = true)
    @Override
    public PropertyValue save(PropertyValue propertyValue) {
        return propertyValueMapper.save(propertyValue);
    }
}
