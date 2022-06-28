package com.kb.video.controller;

import com.kb.video.utils.OSSUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class uploadVideo {
    /**
     * 上传视频
     */
    @RequestMapping("/uploadVideo")
    @CrossOrigin(origins = "*", maxAge = 3600)//跨域
    public Map<String, Object> uploadVideo(@RequestParam("imgFile") MultipartFile imgFile) throws Exception {
        OSSUtil ossClient = new OSSUtil();
        Map<String, Object> m = new HashMap<>();
        if (imgFile.isEmpty()) {
            m.put("error", "上传文件不能为空");
        }
        String newsUrl = "";
        if (!((MultipartFile) imgFile).isEmpty()) {
            try {
                //将文件上传
                String name = ossClient.uploadVideo(imgFile);
                //获取文件的URl地址  以便前台  显示
                //修改视频Vr
//                Boolean b=hotelService.updatHotelById(hId,imgUrl,hVr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            m.put("error", "上传不可为空");
        }
        return m;
    }
    @RequestMapping("/getUrl/{key}")
    public String getUrl(@PathVariable("key") String key){
        OSSUtil ossUtil = new OSSUtil();
        System.out.println(key);
        return ossUtil.getUrl(key);
    }



}
