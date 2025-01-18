package com.lsy.Oss;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Value;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;


import javax.security.auth.message.AuthException;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

@SpringBootTest
@Component
@ConfigurationProperties(prefix = "oss")
public class Test01 {

    //...生成上传凭证，然后准备上传
    private String accessKey;
    private String secretKey;
    private String bucket;

//    为上述凭证属性生成getter,setter方法
// Getters and Setters
    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    @Test
    public void testUpLoad() throws Exception {


//构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
//...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);

//默认不指定key的情况下，以文件内容的hash值作为文件名
        // 动态生成文件名
        String userId = "user123"; // 示例用户ID，可从上下文动态获取
        String date = java.time.LocalDate.now().toString(); // 获取当前日期 (YYYY-MM-DD)
        String key = String.format("images/%s/%s/avatar_%s.png", date.substring(0, 4), date, userId);

// 示例生成的 key: images/2024/01/18/avatar_user123.png


        try {
            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
            InputStream fileInputStream=new FileInputStream("C:\\Users\\acer\\Desktop\\th.jfif");
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(fileInputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                ex.printStackTrace();
                if (ex.response != null) {
                    System.err.println(ex.response);

                    try {
                        String body = ex.response.toString();
                        System.err.println(body);
                    } catch (Exception ignored) {
                    }
                }
            }
        } catch (UnsupportedEncodingException ex) {
            //ignore
        }

    }

}
