package com.kb.video.utils;

import com.aliyun.oss.*;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Random;

public class OSSUtil {

    /**
     * log日志
     */
    public static final Logger logger = LoggerFactory.getLogger(OSSUtil.class);

<<<<<<< HEAD:kb-video/src/main/java/com/kb/video/utils/OSSUtil.java
    private String endpoint= "oss-cn-hangzhou.aliyuncs.com";
    private String accessKeyId="LTAI5t7Mihhmt8wXkkstJTN4";
    private String accessKeySecret="SglFevBI5Gw9fllKP9oBBqL2CFv5yR";
    private String bucketName="yangkuitest";
    private String FOLDER=null;
=======
    // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
    String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
    // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
    String accessKeyId = "LTAI5tSG4hHA2fvMd5Epty36";
    String accessKeySecret = "VFDHPHopnEPl535MRDTMbvYLG5eYDA";
    // 填写Bucket名称，例如examplebucket。
    String bucketName = "yangkuitest";
    // 创建OSSClient实例。
    OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
>>>>>>> f5353f63d8eddcb2cd3b2177a7a738cfd791286c:kb-video/src/main/java/com/kb/kbvideo/utils/OSSUtil.java



    //上传视频
    public String uploadVideo(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String name = random.nextInt(10000)+ System.currentTimeMillis() + substring;
        try {
            InputStream stream=file.getInputStream();
            String filename=System.currentTimeMillis()+file.getOriginalFilename();
//            OSSClient client=new OSSClient(endpoint,accessKeyId,accessKeySecret);
            PutObjectResult result=ossClient.putObject(bucketName,"Video/"+name,stream);
            ossClient.shutdown();
            return "Video/"+name;
        } catch (Exception e) {
            throw new IOException("视频上传失败");
        }
    }
    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public String getUrl(String key) {
        // 设置URL过期时间为10年 3600l* 1000*24*365*10
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        /*        url = "https://" + bucketName + ".oss-cn-beijing.aliyuncs.com/" + bucketName+"/"+ key;*/
//        if (url != null) {
//
//            String host ="https://"+url.getHost()+url.getPath();
//
//            return host;
//        }


        return  url.toString();
    }
    //从OSS下载文件
    public void downloadVideo(String objectName,String pathName){
//        填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称
//        String objectName = "testfolder/exampleobject.txt";
//        String pathName = "D:\\localpath\\examplefile.txt";
        try {
            // 下载Object到本地文件，并保存到指定的本地路径中。如果指定的本地文件存在会覆盖，不存在则新建。
            // 如果未指定本地路径，则下载后的文件默认保存到示例程序所属项目对应本地路径中。
            ossClient.getObject(new GetObjectRequest(bucketName, objectName),new File(pathName));
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    public InputStream bufferedImageToInputStream(BufferedImage image){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
            InputStream input = new ByteArrayInputStream(os.toByteArray());
            return input;
        } catch (IOException e) {
            logger.error("提示:",e);
        }
        return null;
    }

    public static BufferedImage toBufferedImage(MultipartFile file) {
        BufferedImage srcImage = null;
        try {
            FileInputStream in = (FileInputStream) file.getInputStream();
            srcImage = javax.imageio.ImageIO.read(in);
        } catch (IOException e) {

        }
        return srcImage;
    }
}

