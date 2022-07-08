package com.kb.video.controller;

import com.aliyun.oss.model.OSSObjectSummary;
import com.kb.common.base.BaseResponse;
import com.kb.common.exception.InfoException;
import com.kb.video.utils.OSSUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 获取指定key的url
     * @param key
     * @return
     */
    @GetMapping ("/url/{key}")
    public BaseResponse getUrl(@PathVariable("key") String key){
        OSSUtil ossUtil = new OSSUtil();
        System.out.println(key);
        return BaseResponse.success(ossUtil.getUrl(key));
    }

    /**
     * 列举bucketname下所有的key(name)
     * @return
     */
    @GetMapping("/key")
    public BaseResponse getKey(){
        OSSUtil ossUtil = new OSSUtil();
        List<OSSObjectSummary> sums = ossUtil.getUrl();
        List<String> collect = sums.stream().map(item -> item.getKey()).collect(Collectors.toList());
        for (OSSObjectSummary s : sums) {
            System.out.println("\t" + s.getKey());
        }
        return BaseResponse.success(collect,collect.size());
    }
    @GetMapping("/test")
    public void testException(){
        throw new InfoException("test");
    }




}
