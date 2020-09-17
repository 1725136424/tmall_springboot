package site.wanjiahao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.wanjiahao.pojo.Page4Navigator;
import site.wanjiahao.pojo.Property;
import site.wanjiahao.pojo.RESTFULResult;
import site.wanjiahao.service.PropertyService;

@RestController
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private RESTFULResult restfulResult;

    @GetMapping("/categories/{cid}/properties")
    public Page4Navigator<Property> list(@PathVariable("cid") int cid,
                                         @RequestParam(name = "start", defaultValue = "0") int start,
                                         @RequestParam(name = "size", defaultValue = "5") int size) {
        start = start < 0? 0: start;
        return propertyService.list(cid, start, size);
    }

    @GetMapping("/properties/{id}")
    public Property findOne(@PathVariable("id") int id) {
        return propertyService.findOne(id);
    }

    // 请求体中传入的json字符串，需要使用@RequestBody来接受，封装为实体数据
    @PostMapping("/properties")
    public RESTFULResult save(@RequestBody Property property) {
        try {
            propertyService.save(property);
            restfulResult.setSuccess(true);
            restfulResult.setMessage("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            restfulResult.setSuccess(false);
            restfulResult.setMessage("保存失败");
        }
        return restfulResult;
    }

    @DeleteMapping("/properties/{id}")
    public RESTFULResult delete(@PathVariable("id") int id) {
        try {
            propertyService.delete(id);
            restfulResult.setSuccess(true);
            restfulResult.setMessage("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            restfulResult.setSuccess(false);
            restfulResult.setMessage("删除失败");
        }
        return restfulResult;
    }

    @PutMapping("/properties")
    public RESTFULResult update(@RequestBody Property property) {
        try {
            propertyService.update(property);
            restfulResult.setSuccess(true);
            restfulResult.setMessage("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            restfulResult.setSuccess(false);
            restfulResult.setMessage("修改失败");
        }
        return restfulResult;
    }

}
