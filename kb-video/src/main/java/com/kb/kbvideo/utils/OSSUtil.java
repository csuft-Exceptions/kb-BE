package com.kb.kbvideo.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
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
import java.util.Collections;
import java.util.Date;
import java.util.Random;

public class OSSUtil {

    /**
     * log日志
     */
    public static final Logger logger = LoggerFactory.getLogger(OSSUtil.class);

    private String endpoint= null;
    private String accessKeyId=null;
    private String accessKeySecret=null;
    private String bucketName=null;
    private String FOLDER=null;

    /**
     * 上传图片
     *
     * @param url
     * @throws
     */
    public void uploadImg2Oss(String url) throws IOException {
        File fileOnServer = new File(url);
        FileInputStream fin;
        try {
            fin = new FileInputStream(fileOnServer);
            String[] split = url.split("/");
            this.uploadFile2OSS(fin, split[split.length - 1]);
        } catch (FileNotFoundException e) {
            throw new IOException("图片上传失败");
        }
    }

    public String uploadImg2Oss(MultipartFile file) throws IOException {
       /* if (file.getSize() > 10 * 1024 * 1024) {
            throw new IOException("上传图片大小不能超过10M！");
        }*/

        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String name = random.nextInt(10000)+ System.currentTimeMillis() + substring;
        try {
            BufferedImage image = toBufferedImage(file);//修改图片大小
            BufferedImage bufferedImage= Thumbnails.of(image).size(700, 467).asBufferedImage();//修改图片大小
            /*   BufferedImage bufferedImage= Thumbnails.of(file.getInputStream()).size(700, 467).outputQuality(1).asBufferedImage();*/
            this.uploadFile2OSS(bufferedImageToInputStream(bufferedImage), name);//修改图片大小
            return name;
        } catch (Exception e) {
            throw new IOException("图片上传失败");
        }
    }
    //上传视频
    public String uploadVideo(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String name = random.nextInt(10000)+ System.currentTimeMillis() + substring;
        try {
            InputStream stream=file.getInputStream();
            String filename=System.currentTimeMillis()+file.getOriginalFilename();
            OSSClient client=new OSSClient(endpoint,accessKeyId,accessKeySecret);
            PutObjectResult result=client.putObject(bucketName,"Video/"+name,stream);
            client.shutdown();
            return "Video/"+name;
        } catch (Exception e) {
            throw new IOException("视频上传失败");
        }
    }
    /**
     * 获得图片路径
     *
     * @param fileUrl
     * @return
     */
    public String getImgUrl(String fileUrl) {
        if (!StringUtils.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            if(split[0].equals("Video")){
                return this.getUrl("Video/"+ split[split.length - 1]);
            }
            return this.getUrl(this.FOLDER + split[split.length - 1]);
        }
        return "" ;
    }

    /**
     * 上传到OSS服务器 如果同名文件会覆盖
     *
     * @param instream
     *            文件流
     * @param fileName
     *            文件名称 包括后缀名
     * @return 出错返回"" ,唯一MD5数字签名
     */
    public String uploadFile2OSS(InputStream instream, String fileName) {
        String ret = "";
        try {
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            // 上传文件
            PutObjectResult putResult = ossClient.putObject(bucketName, FOLDER + fileName, instream, objectMetadata);
            ret = putResult.getETag();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (instream !=null ) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     */
    public static String getcontentType(String filenameExtension) {
        if (filenameExtension.equalsIgnoreCase("bmp")) {
            return "image/bmp";
        }
        if (filenameExtension.equalsIgnoreCase("gif")) {
            return "image/gif";
        }
        if (filenameExtension.equalsIgnoreCase("jpeg") || filenameExtension.equalsIgnoreCase("jpg")
                || filenameExtension.equalsIgnoreCase("png")) {
            return "image/jpeg";
        }
        if (filenameExtension.equalsIgnoreCase("html")) {
            return "text/html";
        }
        if (filenameExtension.equalsIgnoreCase("txt")) {
            return "text/plain";
        }
        if (filenameExtension.equalsIgnoreCase("vsd")) {
            return "application/vnd.visio";
        }
        if (filenameExtension.equalsIgnoreCase("pptx") || filenameExtension.equalsIgnoreCase("ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (filenameExtension.equalsIgnoreCase("docx") || filenameExtension.equalsIgnoreCase("doc")) {
            return "application/msword";
        }
        if (filenameExtension.equalsIgnoreCase("xml")) {
            return "text/xml";
        }
        return "image/jpeg";
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
        if (url != null) {

            String host ="https://"+url.getHost()+url.getPath();

            return host;
        }


        return  "";
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

