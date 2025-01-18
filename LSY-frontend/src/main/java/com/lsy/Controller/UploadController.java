package com.lsy.Controller;

import com.lsy.domain.ResponseResult;
import com.lsy.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

    @Autowired
    private UploadService uploadService;

    //上传头像到(中转服务器)七牛OSS
    @PostMapping("/upload")
    public ResponseResult uploadImg(MultipartFile img) throws Exception {
        return uploadService.uploadImg(img);
    }
}
