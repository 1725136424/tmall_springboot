package site.wanjiahao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.wanjiahao.mapper.ProductMapper;
import site.wanjiahao.pojo.Category;
import site.wanjiahao.pojo.Page4Navigator;
import site.wanjiahao.pojo.Product;
import site.wanjiahao.service.CategoryService;
import site.wanjiahao.service.ProductService;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryService categoryService;

    @Override
    public Page4Navigator<Product> findAll(int cid, int start, int size, int navigateNums) {
        // 获取分类
        Category category = categoryService.findOne(cid);
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(start, size, sort);
        Page<Product> productPage = productMapper.findByCategory(category, pageable);
        return new Page4Navigator<>(productPage, navigateNums);
    }


    @Override
    public Page4Navigator<Product> findAll(int cid, int start, int size) {
        return findAll(cid, start, size, 5);
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productMapper.findByCategory(category);
    }

    @Override
    public Product findOne(int id) {
        return productMapper.findById(id).get();
    }

    @Override
    public Product save(Product product) {
        return productMapper.save(product);
    }

    @Override
    public void delete(int id) {
        productMapper.deleteById(id);
    }

    @Override
    public Product update(Product product) {
        return productMapper.save(product);
    }
}
