package site.wanjiahao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
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

@Service
@Transactional
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyMapper propertyMapper;

    @Autowired
    private CategoryService categoryService;

    // CRUD接口

    @Override
    public Property save(Property property) {
        return propertyMapper.save(property);
    }

    @Override
    public void delete(int id) {
        propertyMapper.deleteById(id);
    }

    @Override
    public Property update(Property property) {
        return propertyMapper.save(property);
    }

    @Override
    public Property findOne(int id) {
        return propertyMapper.findById(id).get();
    }

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
        return list(cid, start, size, 5);
    }
}
