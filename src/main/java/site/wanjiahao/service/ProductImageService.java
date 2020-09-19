package site.wanjiahao.service;

import site.wanjiahao.pojo.Product;
import site.wanjiahao.pojo.ProductImage;

import java.util.List;

public interface ProductImageService {

    String TYPE_SINGLE = "type_single";

    String TYPE_DETAIL = "type_detail";

    List<ProductImage> listSingleProductImage(Product product);

    List<ProductImage> listDetailProductImage(Product product);

    ProductImage findOne(int id);

    ProductImage save(ProductImage productImage);

    void delete(int id);

    ProductImage update(ProductImage productImage);

    void setFirstProductImages(Product product);

    void setFirstProductImages(List<Product> products);
}
