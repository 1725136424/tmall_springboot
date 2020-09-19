package site.wanjiahao.service;

import site.wanjiahao.pojo.Category;
import site.wanjiahao.pojo.Page4Navigator;
import site.wanjiahao.pojo.Product;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    /**
     * 分页查询
     * @param start 开始页
     * @param size 多少条记录
     * @param navigatePages 分页显示数据
     * @return
     */
    Page4Navigator<Category> findAll(int start, int size, int navigatePages);

    Page4Navigator<Category> findAll(int start, int size);

    Category save(Category category);

    void delete(Integer id);

    Category findOne(Integer id);

    Category update(Category category);
}
