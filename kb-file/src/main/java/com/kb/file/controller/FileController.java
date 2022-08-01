package com.kb.file.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.kb.common.base.BaseResponse;
import com.kb.common.exception.InfoException;
import com.kb.common.utils.OSSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaInfo;
import ws.schild.jave.MultimediaObject;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-28 - 17:10
 */
@RestController
@Slf4j
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

    @PostMapping("/uploadVideo")
    public BaseResponse uploadVideo(MultipartFile file){
        OSSClient ossClient= OSSUtils.getOSSClient(endpoint,accessKeyId,accessKeySecret);
        String fileName=OSSUtils.upload(ossClient,file,true,bucketName,folder);
        File file1=multipartFileToFile(file);
        MultimediaObject instance = new MultimediaObject(file1);
        try {
            MultimediaInfo result = instance.getInfo();
            long time = result.getDuration();
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
            String hms = formatter.format(time);
            log.info(hms);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("fileName",fileName);
            jsonObject.put("time",hms);
            return BaseResponse.success(jsonObject);
        } catch (EncoderException e) {
            log.error("获取时长异常",e);
            throw new InfoException("获取时长异常",e);
        }

    }
    public static File multipartFileToFile(MultipartFile multiFile) {
        // 获取文件名
        String fileName = multiFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 若需要防止生成的临时文件重复,可以在文件名后添加随机码

        try {
            File file = File.createTempFile(fileName, prefix);
            multiFile.transferTo(file);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/getFile")
    public BaseResponse getFile(@RequestParam("filename") String fileName, HttpServletResponse response) {
        OSSClient ossClient= OSSUtils.getOSSClient(endpoint,accessKeyId,accessKeySecret);
        OSSUtils.getFile(ossClient,response,fileName,bucketName,folder);
        return BaseResponse.success("获取文件成功");
    }
}
