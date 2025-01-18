package com.lsy.service;

import com.lsy.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
     ResponseResult uploadImg(MultipartFile img) throws Exception;
}
