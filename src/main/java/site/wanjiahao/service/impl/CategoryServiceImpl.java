package site.wanjiahao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.wanjiahao.mapper.CategoryMapper;
import site.wanjiahao.pojo.Category;
import site.wanjiahao.pojo.Page4Navigator;
import site.wanjiahao.service.CategoryService;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    // 查询所有
    @Override
    public List<Category> findAll() {
        // 倒序排列
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return categoryMapper.findAll(sort);
    }

    // 分页查询
    @Override
    public Page4Navigator<Category> findAll(int start, int size, int navigatePages) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable page = PageRequest.of(start, size, sort);
        Page<Category> pageFromJPA = categoryMapper.findAll(page);
        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    @Override
    public Page4Navigator<Category> findAll(int start, int size) {
        return findAll(start, size, 3);
    }

    @Override
    public Category save(Category category) {
        return categoryMapper.save(category);
    }
}
