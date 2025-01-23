package com.lsy.service.Imple;

import com.google.gson.Gson;
import com.lsy.domain.ResponseResult;
import com.lsy.enums.BlogHttpCodeEnum;
import com.lsy.exception.SystemException;
import com.lsy.service.UploadService;

import com.lsy.utils.AuthGetUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;

@Service
@Data
@ConfigurationProperties(prefix = "oss")
@Component
public class UploadServiceImple implements UploadService {

    private String accessKey;
    private String secretKey;
    private String bucket;

    @Override
    public ResponseResult uploadImg(MultipartFile img,Integer type) throws Exception {

//        1.判断文件合法性
        validateFile(img);

//        2.合法后上传到七牛OSS
        String url = UpLoad(img,type);
        return ResponseResult.okResult(url);

    }

    //上传OSS方法
    private String UpLoad(MultipartFile img,Integer type) throws Exception {

        // 构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;

        UploadManager uploadManager = new UploadManager(cfg);

        // 根据传入图片所属类型（头像或者文章缩略图）动态生成文件名

//        String username = AuthGetUtils.getCurrentUsername();
        String username = (type == 1) ? "user" : (type == 2 ? "article" : null);

        String date = LocalDate.now().toString(); // 当前日期 (YYYY-MM-DD)
        String imageName = (type == 1) ? "images/%s/%s/avatar_%s.png" : (type == 2 ? "images/%s/%s/thumbnail_%s.png" : null);

        String key = String.format(imageName, date.substring(0, 4), date, username);

//        七牛云存储空间的外链基础域名
        String domain = "http://sq9k8nt9h.hd-bkt.clouddn.com//";

        try {
//            获取 MultipartFile 对象的输入流
            InputStream fileInputStream = img.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey); // 使用注入的配置
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(fileInputStream, key, upToken, null, null);
                // 解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println("File Key: " + putRet.key);
                System.out.println("File Hash: " + putRet.hash);
                return domain+key;
            } catch (QiniuException ex) {
                System.err.println("Error Response: " + ex.response);
                ex.printStackTrace();
            }
        } catch (Exception ex) {

        }
        return null;
    }


    // 文件合法性检查方法
    public static void validateFile(MultipartFile img) {
        // 1. 文件是否为空
        if (img == null || img.isEmpty()) {
            throw new SystemException(BlogHttpCodeEnum.NO_FILE);
        }

        // 2. 文件大小限制
        long maxFileSize = 5 * 1024 * 1024; // 5 MB
        if (img.getSize() > maxFileSize) {
            throw new SystemException(BlogHttpCodeEnum.NO_FILE.getCode(),"文件大小超过限制（最大为 5 MB）");
        }

//        // 3. 文件类型验证
//        String contentType = img.getContentType();
//        if (contentType == null || !contentType.matches("image/(jpeg|jpg|png|gif|jfif)")) {
//            throw new SystemException(BlogHttpCodeEnum.NO_FILE.getCode(),"文件类型不合法，仅支持 JPG, JPEG, PNG, GIF, jfif 格式");
//        }

        // 4. 文件扩展名验证
        String originalFilename = img.getOriginalFilename();
        if (originalFilename == null || !originalFilename.matches(".*\\.(jpg|jpeg|png|gif|jfif)$")) {
            throw new SystemException(BlogHttpCodeEnum.NO_FILE.getCode(),"文件扩展名不合法，仅支持 JPG, JPEG, PNG, GIF,jfif");
        }

        // 5. 文件内容安全性验证
        if (!isValidImage(img)) {
            throw new SystemException(BlogHttpCodeEnum.NO_FILE.getCode(),"文件内容不合法，请上传有效的图片文件");
        }

        // 6. 文件名合法性
        if (originalFilename != null && (originalFilename.contains("..") || originalFilename.contains("/") || originalFilename.contains("\\"))) {
            throw new SystemException(BlogHttpCodeEnum.NO_FILE.getCode(),"文件名非法，请重命名后再上传");
        }

    }

    // 验证文件头是否为合法图片类型
    private static boolean isValidImage(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            byte[] header = new byte[4];
            inputStream.read(header);

            String magicNumberHex = bytesToHex(header);
            // 检查常见图片文件的 Magic Number
            return magicNumberHex.startsWith("FFD8") || // JPG
                    magicNumberHex.startsWith("89504E47") || // PNG
                    magicNumberHex.startsWith("47494638"); // GIF
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}


