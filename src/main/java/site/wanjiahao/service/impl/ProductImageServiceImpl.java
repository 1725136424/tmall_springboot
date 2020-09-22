package site.wanjiahao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.wanjiahao.mapper.ProductImageMapper;
import site.wanjiahao.pojo.OrderItem;
import site.wanjiahao.pojo.Product;
import site.wanjiahao.pojo.ProductImage;
import site.wanjiahao.service.ProductImageService;

import java.util.List;

@Service
@Transactional
public class ProductImageServiceImpl implements ProductImageService {

    public static final String TYPE_SINGLE = "type_single";

    public static final String TYPE_DETAIL = "type_detail";

    @Autowired
    private ProductImageMapper productImageMapper;


    @Override
    public List<ProductImage> listSingleProductImage(Product product) {
        return productImageMapper.findByProductAndTypeOrderByIdDesc(product, TYPE_SINGLE);
    }

    @Override
    public List<ProductImage> listDetailProductImage(Product product) {
        return productImageMapper.findByProductAndTypeOrderByIdDesc(product, TYPE_DETAIL);
    }

    @Override
    public ProductImage findOne(int id) {
        return productImageMapper.findById(id).get();
    }

    @Override
    public ProductImage save(ProductImage productImage) {
        return productImageMapper.save(productImage);
    }

    @Override
    public void delete(int id) {
        productImageMapper.deleteById(id);
    }

    @Override
    public ProductImage update(ProductImage productImage) {
        return productImageMapper.save(productImage);
    }

    @Override
    public void setFirstProductImages(Product product) {
        List<ProductImage> singleImages = listSingleProductImage(product);
        if(!singleImages.isEmpty()) {
            product.setFirstProductImage(singleImages.get(0));
        } else {
            product.setFirstProductImage(new ProductImage()); //这样做是考虑到产品还没有来得及设置图片，但是在订单后台管理里查看订单项的对应产品图片。
        }

    }

    @Override
    public void setFirstProductImages(List<Product> products) {
        for (Product product : products) {
            setFirstProductImages(product);
        }
    }

    @Override
    public void setFirstProductImagesOnOrderItems(List<OrderItem> ois) {
        for (OrderItem orderItem : ois) {
            setFirstProductImages(orderItem.getProduct());
        }
    }
}
