package site.wanjiahao.comparator;

import site.wanjiahao.pojo.Product;

import java.util.Comparator;

// 价格排序
public class ProductPriceComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return o2.getPromotePrice().compareTo(o1.getPromotePrice());
    }
}
