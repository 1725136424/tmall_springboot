package site.wanjiahao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.wanjiahao.pojo.Category;
import site.wanjiahao.pojo.Page4Navigator;
import site.wanjiahao.pojo.RESTFulResult;
import site.wanjiahao.service.CategoryService;
import site.wanjiahao.utils.UploadImageUtil;
import javax.servlet.http.HttpServletRequest;
import java.io.File;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // RESTFul响应结果对象
    @Autowired
    private RESTFulResult restFulResult;

    @GetMapping("/categories")
    public Page4Navigator<Category> findAll(@RequestParam(name = "start", defaultValue = "0") int start,
                                            @RequestParam(name = "size", defaultValue = "5") int size) {

        // 逻辑判断
        start = start < 0? 0: start;
        return categoryService.findAll(start, size);
    }

    @PostMapping("/categories")
    public RESTFulResult save(Category category,
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
    public RESTFulResult delete(@PathVariable("id") Integer id) {
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

}
