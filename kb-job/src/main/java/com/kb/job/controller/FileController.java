package com.kb.job.controller;

import com.aliyun.oss.OSSClient;
import com.kb.common.base.BaseResponse;
import com.kb.common.utils.OSSUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-28 - 17:10
 */
@RestController
public class FileController {

    @Value("${aliyun.accessKeyId}")
    private  String accessKeyId;

    @Value("${aliyun.accessKeySecret}")
    private  String accessKeySecret;

    @Value("${aliyun.oss.endpoint}")
    private  String endpoint;

    @Value("${aliyun.oss.bucketName}")
    private  String bucketName;

    @Value("${aliyun.oss.folder}")
    private  String folder;

    @PostMapping("/upload")
    public BaseResponse upload(MultipartFile file){
        OSSClient ossClient= OSSUtils.getOSSClient(endpoint,accessKeyId,accessKeySecret);
        String fileName=OSSUtils.upload(ossClient,file,true,bucketName,folder);
        return BaseResponse.success(fileName);
    }

}
