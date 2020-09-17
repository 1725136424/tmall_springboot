package site.wanjiahao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.wanjiahao.pojo.Page4Navigator;
import site.wanjiahao.pojo.Product;
import site.wanjiahao.pojo.RESTFULResult;
import site.wanjiahao.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private RESTFULResult restfulResult;


    @GetMapping("/categories/{cid}/products")
    public Page4Navigator<Product> list(@PathVariable("cid") int cid,
                                        @RequestParam(value = "start", defaultValue = "0") int start,
                                        @RequestParam(value = "size", defaultValue = "5") int size)  {
        start = start<0?0:start;

        return productService.findAll(cid, start, size);
    }

    @GetMapping("/products/{id}")
    public Product get(@PathVariable("id") int id) {
        return productService.findOne(id);
    }

    @PostMapping("/products")
    public Object add(@RequestBody Product product) {
        try {
            product.setCreateDate(new Date());
            productService.save(product);
            restfulResult.setMessage("保存成功");
            restfulResult.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            restfulResult.setMessage("保存失败");
            restfulResult.setSuccess(false);
        }
        return restfulResult;
    }

    @DeleteMapping("/products/{id}")
    public RESTFULResult delete(@PathVariable("id") int id, HttpServletRequest request)  throws Exception {
        try {
            productService.delete(id);
            restfulResult.setMessage("删除成功");
            restfulResult.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            restfulResult.setMessage("删除失败");
            restfulResult.setSuccess(false);
        }
        return restfulResult;
    }

    @PutMapping("/products")
    public RESTFULResult update(@RequestBody Product product) throws Exception {
        try {
            productService.update(product);
            restfulResult.setMessage("更新成功");
            restfulResult.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            restfulResult.setMessage("更新失败");
            restfulResult.setSuccess(false);
        }
        return restfulResult;
    }
}

