package site.wanjiahao.service.impl;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.wanjiahao.es.ProductESMapper;
import site.wanjiahao.mapper.ProductMapper;
import site.wanjiahao.pojo.Category;
import site.wanjiahao.pojo.Page4Navigator;
import site.wanjiahao.pojo.Product;
import site.wanjiahao.service.*;
import site.wanjiahao.utils.SpringContextUtil;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@CacheConfig(cacheNames = "products")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ProductESMapper productESMapper;

    @Cacheable(key = "'products-cid-' + #p0 + '-page-' + #p1 + '-' + #p2")
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
        ProductService productService = SpringContextUtil.getBean(ProductService.class);
        return productService.findAll(cid, start, size, 5);
    }

    @Cacheable(key = "'products-cid-' + #p0.id")
    @Override
    public List<Product> findByCategory(Category category) {
        return productMapper.findByCategoryOrderByIdDesc(category);
    }

    @Cacheable(key = "'products-one-' + #p0")
    @Override
    public Product findOne(int id) {
        return productMapper.findById(id).get();
    }

    @CacheEvict(allEntries = true)
    @Override
    public Product save(Product product) {
        productESMapper.save(product);
        return productMapper.save(product);
    }

    @CacheEvict(allEntries = true)
    @Override
    public void delete(int id) {
        productESMapper.deleteById(id);
        productMapper.deleteById(id);
    }

    @CacheEvict(allEntries = true)
    @Override
    public Product update(Product product) {
        productESMapper.save(product);
        return productMapper.save(product);
    }

    @Override
    public void fill(List<Category> categories) {
        ProductService productService = SpringContextUtil.getBean(ProductService.class);
        for (Category category : categories) {
            productService.fill(category);
        }
    }

    @Override
    public void fill(Category category) {
        ProductService productService = SpringContextUtil.getBean(ProductService.class);
        List<Product> products = productService.findByCategory(category);
        productImageService.setFirstProductImages(products);
        category.setProducts(products);
    }

    @Override
    public void fillByRow(List<Category> categories) {
        int productNumberEachRow = 8;
        for (Category category : categories) {
            List<Product> products = category.getProducts();
            List<List<Product>> productsByRow = new ArrayList<>();
            for (int i = 0; i < products.size(); i += productNumberEachRow) {
                int size = i + productNumberEachRow;
                size = Math.min(size, products.size());
                List<Product> productsOfEachRow = products.subList(i, size);
                productsByRow.add(productsOfEachRow);
            }
            category.setProductsByRow(productsByRow);
        }
    }

    @Override
    public void setSaleAndReviewNumber(Product product) {
        OrderItemService orderItemService = SpringContextUtil.getBean(OrderItemService.class);
        ReviewService reviewService = SpringContextUtil.getBean(ReviewService.class);
        // 设置销售数量
        int saleCount = orderItemService.findSaleCount(product);
        product.setSaleCount(saleCount);
        // 设置评价数量
        int reviewCount = reviewService.findCount(product);
        product.setReviewCount(reviewCount);

    }

    @Override
    public void setSaleAndReviewNumber(List<Product> products) {
        ProductService productService = SpringContextUtil.getBean(ProductService.class);
        for (Product product : products) {
            productService.setSaleAndReviewNumber(product);
        }
    }

    @Override
    public List<Product> search(String keyword, int start, int size) {
        // 初始化es数据库
        initDatabase2ES();
        // 使用elasticSearch查询
        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("name", keyword);
        Sort sort  = Sort.by(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(start, size,sort);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(queryBuilder).build();
        Page<Product> page = productESMapper.search(searchQuery);
        return page.getContent();
    }

    private void initDatabase2ES() {
        Pageable pageable  = PageRequest.of(0, 5);
        Page<Product> productESMapperAll = productESMapper.findAll(pageable);
        if (productESMapperAll.getContent().isEmpty()) {
            List<Product> products = productMapper.findAll();
            for (Product product : products) {
                productESMapper.save(product);
            }
        }
    }
}
