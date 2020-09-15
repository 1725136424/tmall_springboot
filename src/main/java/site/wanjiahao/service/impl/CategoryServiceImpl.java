package site.wanjiahao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import site.wanjiahao.mapper.CategoryMapper;
import site.wanjiahao.pojo.Category;
import site.wanjiahao.service.CategoryService;

import java.util.List;

@Service
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


}
