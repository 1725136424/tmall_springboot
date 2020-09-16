package site.wanjiahao.utils;

import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import site.wanjiahao.pojo.Category;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UploadImageUtil {

    /**
     *
     * @param category 实体类
     * @param request request
     * @param file 长传文件流
     * @return 上传路径
     * @throws IOException
     */
    public static File uploadImageAndChange2Jpg(Category category,
                                                  HttpServletRequest request,
                                                  MultipartFile file) throws IOException {
        // 获取上传路径
        String realPath = ResourceUtils.getURL("classpath:").getPath() + "static/img/category";
        File filePath = new File(realPath);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        String imageName = category.getId() + ".jpg";
        File imagePath = new File(realPath, imageName);
        file.transferTo(imagePath);

        // 改变图片的文件类型为jpg
        BufferedImage changeImage = ImageUtil.change2jpg(imagePath);
        ImageIO.write(changeImage, "jpg", imagePath);
        return imagePath;
    }
}
