package site.wanjiahao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.wanjiahao.pojo.Category;
import site.wanjiahao.pojo.Page4Navigator;
import site.wanjiahao.pojo.RESTFULResult;
import site.wanjiahao.service.CategoryService;
import site.wanjiahao.utils.UploadImageUtil;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // RESTFul响应结果对象
    @Autowired
    private RESTFULResult restFulResult;

    @GetMapping("/categories")
    public Page4Navigator<Category> findAll(@RequestParam(name = "start", defaultValue = "0") int start,
                                            @RequestParam(name = "size", defaultValue = "5") int size) {

        // 逻辑判断
        start = start < 0? 0: start;
        return categoryService.findAll(start, size);
    }

    @PostMapping("/categories")
    public RESTFULResult save(Category category,
                              HttpServletRequest request,
                              MultipartFile image) {
        try {
            // JPA根据传入实体类的id来调用更新或者保存方法
            Category savedCategory = categoryService.save(category);
            // 上传图片
            File uploadPath = UploadImageUtil.uploadImageAndChange2Jpg(savedCategory, request, image);
            restFulResult.setSuccess(true);
            restFulResult.setMessage("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            restFulResult.setSuccess(false);
            restFulResult.setMessage("保存失败");
        }
        return restFulResult;
    }

    @DeleteMapping("/categories/{id}")
    public RESTFULResult delete(@PathVariable("id") int id) {
        try {
            categoryService.delete(id);
            restFulResult.setSuccess(true);
            restFulResult.setMessage("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            restFulResult.setSuccess(false);
            restFulResult.setMessage("删除失败");
        }
        return restFulResult;
    }

    @GetMapping("/categories/{id}")
    public Category findOne(@PathVariable("id") int id) {
        return categoryService.findOne(id);
    }

    @PutMapping("/categories/{id}")
    public RESTFULResult update(Category category,
                                MultipartFile image,
                                HttpServletRequest request) {
        try {
            // 更新实体
            Category updateCategory = categoryService.update(category);
            if (image != null) {
                // 更新图片
                UploadImageUtil.uploadImageAndChange2Jpg(updateCategory, request, image);
            }
            restFulResult.setSuccess(true);
            restFulResult.setMessage("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            restFulResult.setSuccess(false);
            restFulResult.setMessage("修改失败");
        }
        return restFulResult;
    }

}
