package site.wanjiahao.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
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
import org.thymeleaf.spring5.context.SpringContextUtils;
import org.thymeleaf.spring5.util.SpringContentTypeUtils;
import site.wanjiahao.mapper.CategoryMapper;
import site.wanjiahao.pojo.Category;
import site.wanjiahao.pojo.Page4Navigator;
import site.wanjiahao.pojo.Product;
import site.wanjiahao.service.CategoryService;
import site.wanjiahao.utils.SpringContextUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional
@Slf4j
@CacheConfig(cacheNames = "categories")
// redis管理categories缓存
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @PersistenceContext
    private EntityManager entityManager;

    // 查询所有
    @Cacheable(key = "'categories-all'")
    @Override
    public List<Category> findAll() {
        // 倒序排列
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return categoryMapper.findAll(sort);
    }

    // 分页查询
    @Cacheable(key = "'categories-page-' + #p0 + '-' + #p1")
    @Override
    public Page4Navigator<Category> findAll(int start, int size, int navigatePages) {
        log.info("===jap获取===");
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable page = PageRequest.of(start, size, sort);
        Page<Category> pageFromJPA = categoryMapper.findAll(page);
        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    @Override
    public Page4Navigator<Category> findAll(int start, int size) {
        // 不能直接调用
        CategoryService categoryService = SpringContextUtil.getBean(CategoryService.class);
        return categoryService.findAll(start, size, 3);
    }

    @CacheEvict(allEntries = true)
    @Override
    public Category save(Category category) {
        return categoryMapper.save(category);
    }

    @CacheEvict(allEntries = true)
    @Override
    public void delete(Integer id) {
        categoryMapper.deleteById(id);
    }

    @Cacheable(key = "'categories-one-' + #p0")
    @Override
    public Category findOne(Integer id) {
        return categoryMapper.findById(id).get();
    }

    @CacheEvict(allEntries = true)
    @Override
    public Category update(Category category) {
        return categoryMapper.save(category);
    }

    @Override
    public void removeCategoryFromProduct(List<Category> cs) {
        for (Category category : cs) {
            removeCategoryFromProduct(category);
        }
    }

    @Override
    public void removeCategoryFromProduct(Category category) {
        // 获取session
        Session session = entityManager.unwrap(Session.class);
        List<Product> products = category.getProducts();
        if (null != products) {
            for (Product product : products) {
                product.setCategory(null);
                // 持久 --> 游离
                session.evict(product);
            }
        }
        List<List<Product>> productsByRow = category.getProductsByRow();
        if (null != productsByRow) {
            for (List<Product> ps : productsByRow) {
                for (Product p : ps) {
                    p.setCategory(null);
                    // 持久 --> 游离
                    session.evict(p);
                }
            }
        }
    }
}
