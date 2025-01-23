package com.lsy.Controller;

import com.lsy.annotation.SystempLog;
import com.lsy.domain.ResponseResult;
import com.lsy.service.UploadService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(tags = "上传图片", description = "上传图片相关接口")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    //上传头像到(中转服务器)七牛OSS
    @PostMapping("/upload")
    @SystempLog(businessName = "上传头像到七牛OSS")
    public ResponseResult uploadImg(MultipartFile img) throws Exception {
        return uploadService.uploadImg(img,1);
    }
}
