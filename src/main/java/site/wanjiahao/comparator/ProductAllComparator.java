package site.wanjiahao.comparator;

import site.wanjiahao.pojo.Product;

import java.util.Comparator;

// 综合排序
public class ProductAllComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return o2.getReviewCount() * o2.getSaleCount() - o1.getReviewCount() * o1.getSaleCount();
    }
}
