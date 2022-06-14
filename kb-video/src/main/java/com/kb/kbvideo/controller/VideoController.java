package com.kb.kbvideo.controller;

import com.kb.kbvideo.utils.OSSUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yk
 * @version 1.0
 * @date 2022/6/12 8:21
 */
@RestController
public class VideoController {

    @RequestMapping("/getVideo")
    public String downloadVideo(){
        OSSUtil ossUtil = new OSSUtil();
        String objectName="trailer.mp4";
        String pathName = "C:\\Users\\杨奎\\Desktop\\test.mp4";
        ossUtil.downloadVideo(objectName,pathName);
        return "hello";
    }

}
