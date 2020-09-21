package site.wanjiahao.controller.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.wanjiahao.pojo.Product;
import site.wanjiahao.pojo.PropertyValue;
import site.wanjiahao.pojo.RESTFULResult;
import site.wanjiahao.service.ProductService;
import site.wanjiahao.service.PropertyValueService;

import java.util.List;

@RestController
public class PropertyValueController {

    @Autowired
    private PropertyValueService propertyValueService;

    @Autowired
    private ProductService productService;

    @Autowired
    private RESTFULResult restfulResult;

    @GetMapping("/products/{pid}/propertyValues")
    public List<PropertyValue> findAll(@PathVariable("pid") int pid) {
        Product product = productService.findOne(pid);
        propertyValueService.init(product);
        return propertyValueService.list(product);
    }

    @PutMapping("/propertyValues")
    public RESTFULResult update(@RequestBody PropertyValue propertyValue) {
        try {
            propertyValueService.update(propertyValue);
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
