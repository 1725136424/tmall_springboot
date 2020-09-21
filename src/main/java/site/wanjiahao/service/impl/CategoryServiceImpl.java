package site.wanjiahao.service.impl;

import org.hibernate.Session;
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
import site.wanjiahao.pojo.Product;
import site.wanjiahao.service.CategoryService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @PersistenceContext
    private EntityManager entityManager;

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

    @Override
    public void delete(Integer id) {
        categoryMapper.deleteById(id);
    }

    @Override
    public Category findOne(Integer id) {
        return categoryMapper.findById(id).get();
    }

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
