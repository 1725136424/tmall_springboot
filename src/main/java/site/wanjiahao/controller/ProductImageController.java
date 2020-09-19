package site.wanjiahao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.wanjiahao.pojo.Page4Navigator;
import site.wanjiahao.pojo.Product;
import site.wanjiahao.pojo.ProductImage;
import site.wanjiahao.pojo.RESTFULResult;
import site.wanjiahao.service.ProductImageService;
import site.wanjiahao.service.ProductService;
import site.wanjiahao.utils.ImageUtil;
import site.wanjiahao.utils.UploadImageUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
public class ProductImageController {

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private ProductService productService;

    @Autowired
    private RESTFULResult restfulResult;


    @GetMapping("/products/{pid}/productImages")
    public List<ProductImage> list(@PathVariable("pid") int pid, String type) {
        Product product = productService.findOne(pid);
        if (ProductImageService.TYPE_SINGLE.equals(type)) {
            return productImageService.listSingleProductImage(product);
        } else if (ProductImageService.TYPE_DETAIL.equals(type)) {
            return productImageService.listDetailProductImage(product);
        }
        return null;
    }

    @PostMapping("/productImages")
    public RESTFULResult add(int pid,
                             String type,
                             MultipartFile image) {
        try {
            Product product = productService.findOne(pid);
            ProductImage productImage = new ProductImage();
            productImage.setType(type);
            productImage.setProduct(product);
            ProductImage resProductImage = productImageService.save(productImage);
            // 保存图片
            String folder = "static/img";
            if (ProductImageService.TYPE_SINGLE.equals(type)) {
                folder += "/productSingle";
            }else if (ProductImageService.TYPE_DETAIL.equals(type)) {
                folder += "/productDetail";
            }
            UploadImageUtil.uploadImageAndChange2Jpg(resProductImage.getId(), folder, image);
            restfulResult.setMessage("保存成功");
            restfulResult.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            restfulResult.setMessage("保存失败");
            restfulResult.setSuccess(false);
        }
        return restfulResult;
    }

    @DeleteMapping("/productImages/{id}")
    public RESTFULResult delete(@PathVariable("id") int id) {
        try {
            ProductImage productImage = productImageService.findOne(id);
            productImageService.delete(id);
            // 删除图片
            String folder = "static/img";
            if (ProductImageService.TYPE_SINGLE.equals(productImage.getType())) {
                folder += "/productSingle";
            } else if (ProductImageService.TYPE_DETAIL.equals(productImage.getType())) {
                folder += "/productDetail";
            }
            folder += "/" + productImage.getId() + ".jpg";
            ImageUtil.delete(folder);
            restfulResult.setMessage("删除成功");
            restfulResult.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            restfulResult.setMessage("删除失败");
            restfulResult.setSuccess(false);
        }
        return restfulResult;
    }

}

