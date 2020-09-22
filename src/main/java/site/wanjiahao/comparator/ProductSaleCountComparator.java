package site.wanjiahao.comparator;

import site.wanjiahao.pojo.Product;

import java.util.Comparator;

// 价格排序
public class ProductSaleCountComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return o2.getSaleCount() - o1.getSaleCount();
    }
}
