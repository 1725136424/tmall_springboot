package site.wanjiahao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.wanjiahao.mapper.PropertyMapper;
import site.wanjiahao.pojo.Category;
import site.wanjiahao.pojo.Page4Navigator;
import site.wanjiahao.pojo.Property;
import site.wanjiahao.service.CategoryService;
import site.wanjiahao.service.PropertyService;
import site.wanjiahao.utils.SpringContextUtil;

import java.util.List;

@Service
@Transactional
@CacheConfig(cacheNames = "properties")
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyMapper propertyMapper;

    @Autowired
    private CategoryService categoryService;

    // CRUD接口
    @CacheEvict(allEntries = true)
    @Override
    public Property save(Property property) {
        return propertyMapper.save(property);
    }

    @CacheEvict(allEntries = true)
    @Override
    public void delete(int id) {
        propertyMapper.deleteById(id);
    }

    @CacheEvict(allEntries = true)
    @Override
    public Property update(Property property) {
        return propertyMapper.save(property);
    }

    @Cacheable(key = "'properties-one-' + #p0")
    @Override
    public Property findOne(int id) {
        return propertyMapper.findById(id).get();
    }

    @Cacheable(key = "'properties-cid-' + #p0.id")
    @Override
    public List<Property> findByCategory(Category category) {
        return propertyMapper.findByCategory(category);
    }

    @Cacheable(key = "'properties-cid-' + #p0 + '-page-' + #p1 + '-' + #p2")
    @Override
    public Page4Navigator<Property> list(int cid, int start, int size, int navigatePages) {
        Category category = categoryService.findOne(cid);
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(start, size, sort);
        Page<Property> pageFromJPA = propertyMapper.findByCategory(category, pageable);
        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    @Override
    public Page4Navigator<Property> list(int cid, int start, int size) {
        PropertyService propertyService = SpringContextUtil.getBean(PropertyService.class);
        return propertyService.list(cid, start, size, 5);
    }
}
