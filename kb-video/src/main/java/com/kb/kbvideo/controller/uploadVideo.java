package com.kb.kbvideo.controller;

import com.kb.kbvideo.utils.OSSUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
                String imgUrl = ossClient.getImgUrl(name);
                //修改视频Vr
//                Boolean b=hotelService.updatHotelById(hId,imgUrl,hVr);
                m.put("url", imgUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            m.put("error", "上传不可为空");
        }
        return m;
    }
}
