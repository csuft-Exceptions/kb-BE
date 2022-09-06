package com.kb.common.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import com.kb.common.exception.InfoException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author mawz
 * @version 1.0.0
 * @description
 * @date 2022/7/1 17:52
 */
@Component
@Slf4j
public class OSSUtils {


    private static volatile OSSClient ossClient;
    /**
     * 获取对象(单例)
     * 如果排对怎么办?根据用户id做标识
     * @return ossClient
     */
    public static OSSClient getOSSClient(String endpoint,String accessKeyId,String accessKeySecret){
        OSSClient temp=null;
        if(ossClient==null){
            synchronized (OSSUtils.class){
                if (ossClient==null){
                    temp=new OSSClient(endpoint, accessKeyId, accessKeySecret);
                    ossClient=temp;
                }
            }
        }
        return ossClient;
    }

    /**
     * 上传文件
     * @param ossClient
     * @param request
     */
    public static String upload2OSS(OSSClient ossClient, HttpServletRequest request,String bucketName,String folder) {
        StringBuilder sb=new StringBuilder();
        final List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        for(MultipartFile file:files){
            String fileName=upload(ossClient,file,true,bucketName,folder);
            if(sb.length()!=0){
                sb.append(",");
            }
            sb.append(fileName);
        }
        return sb.toString();
    }

    /**
     *  上传文件
     * @param ossClient
     * @param file
     * @id  是否需要生成id
     * @return
     */
    public static String upload(OSSClient ossClient, MultipartFile file, boolean id,String bucketName,String folder) {
        String fileName = null;
        try {
            if (file == null || StringUtils.isBlank(file.getOriginalFilename())) {
                throw new InfoException("文件为空");
            }
          if(id) {
              fileName = UUID.randomUUID()+ "-" + file.getOriginalFilename();
          }else{
              fileName= file.getOriginalFilename();
          }
            InputStream inputStream=new ByteArrayInputStream(file.getBytes());
            // 创建上传Object的Metadata
            ObjectMetadata metadata = getObjectMetadata(inputStream,fileName);
            // 上传文件 (上传文件流的形式)
            ossClient.putObject(bucketName, folder + fileName, inputStream, metadata);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
        }
        return fileName;
    }

    /**
     * 获取文件
     * @param ossClient
     * @param response
     * @param fileName
     */
    public static void getFile(OSSClient ossClient, HttpServletResponse response,String fileName,String bucketName,String folder){
        String name=fileName.substring(fileName.lastIndexOf('-')+1);
        try {
            response.setContentType("application/octet-stream");
            name = URLEncoder.encode(name,"UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + name);
            OSSObject ossObject=ossClient.getObject(bucketName,folder+fileName);
            AssertUtil.assertNull(ossObject,"文件未找到");
            InputStream in=ossObject.getObjectContent();
            final ServletOutputStream outputStream = response.getOutputStream();
            IOUtils.copy(in, outputStream);
            outputStream.flush();
            outputStream.close();
        }catch (IOException e){
            log.error("获取文件：{} 时发生错误",fileName,e);
            throw new InfoException("下载文件时出错");
        }

    }
    /**
     * 分片
     * @param bucketName
     * @param file
     * @param filename
     * @return
     * @throws Exception
     */
    public static String uploadPartFile(String bucketName, File file, String filename, OSSClient ossClient) throws Exception{
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, filename);
        // 初始化分片
        InitiateMultipartUploadResult upresult = ossClient.initiateMultipartUpload(request);
        // 返回uploadId，它是分片上传事件的唯一标识。您可以根据该uploadId发起相关的操作，例如取消分片上传、查询分片上传等。
        String uploadId = upresult.getUploadId();
        // partETags是PartETag的集合。PartETag由分片的ETag和分片号组成。
        List<PartETag> partETags =  new ArrayList<PartETag>();
        // 每个分片的大小，用于计算文件有多少个分片。单位为字节。1mb
        final long partSize = 1 * 1024 * 1024L;


        long fileLength = file.length();
        int partCount = (int) (fileLength / partSize);
        if (fileLength % partSize != 0) {
            partCount++;
        }
        // 遍历分片上传。
        for (int i = 0; i < partCount; i++) {
            long startPos = i * partSize;
            long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;
            InputStream instream = null;
            instream = new FileInputStream(file);
            // 跳过已经上传的分片。
            instream.skip(startPos);
            UploadPartRequest uploadPartRequest = new UploadPartRequest();
            uploadPartRequest.setBucketName(bucketName);
            uploadPartRequest.setKey(filename);
            uploadPartRequest.setUploadId(uploadId);
            uploadPartRequest.setInputStream(instream);
            // 设置分片大小。除了最后一个分片没有大小限制，其他的分片最小为100 KB。
            uploadPartRequest.setPartSize(curPartSize);
            // 设置分片号。每一个上传的分片都有一个分片号，取值范围是1~10000，如果超出此范围，OSS将返回InvalidArgument错误码。
            uploadPartRequest.setPartNumber(i + 1);
            // 每个分片不需要按顺序上传，甚至可以在不同客户端上传，OSS会按照分片号排序组成完整的文件。
            UploadPartResult uploadPartResult = ossClient.uploadPart(uploadPartRequest);
            // 每次上传分片之后，OSS的返回结果包含PartETag。PartETag将被保存在partETags中。
            partETags.add(uploadPartResult.getPartETag());
        }
        CompleteMultipartUploadRequest completeMultipartUploadRequest =
                new CompleteMultipartUploadRequest(bucketName,filename, uploadId, partETags);
        // 如果需要在完成分片上传的同时设置文件访问权限，请参考以下示例代码。
        // completeMultipartUploadRequest.setObjectACL(CannedAccessControlList.Private);
        // 指定是否列举当前UploadId已上传的所有Part。如果通过服务端List分片数据来合并完整文件时，以上CompleteMultipartUploadRequest中的partETags可为null。
        // Map<String, String> headers = new HashMap<String, String>();
        // 如果指定了x-oss-complete-all:yes，则OSS会列举当前UploadId已上传的所有Part，然后按照PartNumber的序号排序并执行CompleteMultipartUpload操作。
        // 如果指定了x-oss-complete-all:yes，则不允许继续指定body，否则报错。
        // headers.put("x-oss-complete-all","yes");
        // completeMultipartUploadRequest.setHeaders(headers);

        // 完成分片上传。
        CompleteMultipartUploadResult completeMultipartUploadResult = ossClient.completeMultipartUpload(completeMultipartUploadRequest);
        return filename;
    }

    public static InputStream getFileDefect(OSSClient ossClient, HttpServletResponse response,String bucketName,String folder,String fileName){
        try {
            OSSObject ossObject=ossClient.getObject(bucketName,folder+fileName);
            AssertUtil.assertNull(ossObject,"文件未找到");
            InputStream in=ossObject.getObjectContent();
            final ServletOutputStream outputStream = response.getOutputStream();
            InputStream inputStream=in;
            IOUtils.copy(in, outputStream);
            outputStream.flush();
            outputStream.close();
            return inputStream;
        }catch (IOException e){
            log.error("获取文件：{} 时发生错误",fileName,e);
            throw new InfoException("下载文件时出错");
        }
    }

    /**
     * 添加文件的元信息
     */
    private static ObjectMetadata getObjectMetadata(InputStream inputStream, String fileName) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        // 上传的文件的长度
        metadata.setContentLength(inputStream.available());
        // 指定该Object被下载时的网页的缓存行为
        metadata.setCacheControl("no-cache");
        // 指定该Object下设置Header
        metadata.setHeader("Pragma", "no-cache");
        // 指定该Object被下载时的内容编码格式
        metadata.setContentEncoding("utf-8");
        // 文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
        metadata.setContentType(getContentType(fileName));
        // 指定该Object被下载时的名称（如何显示附加的文件，打开或下载，及文件名称）
        metadata.setContentDisposition(fileName);
        return metadata;
    }

    /**
     * 判断文件的类型
     */
    public static String getContentType(String fileName) {
        // 文件的后缀名
        String fileExtension =fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        switch (fileExtension){
            case (".bmp"):
                return "image/bmp";
            case (".gif"):
                return "image/gif";
            case (".jpeg"):
            case (".jpg"):
                return "image/jpeg";
            case (".png"):
                return "image/png";
            case (".html"):
                return "text/html";
            case (".txt"):
                return "text/plain";
            case (".vsd"):
                return "application/vnd.visio";
            case (".ppt"):
                return "application/vnd.ms-powerpoint";
            case (".doc"):
                return "application/msword";
            case (".xml"):
                return "text/xml";
            default:
                return "";
        }
    }

    /**
     * 创建模拟文件夹
     */
    public static String createFolder(OSSClient ossClient,String folder,String bucketName) {
        // 文件夹名
        final String keySuffixWithSlash = folder;
        // 判断文件夹是否存在，不存在则创建
        if (!ossClient.doesObjectExist(bucketName, keySuffixWithSlash)) {
            // 创建文件夹
            ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
            log.info("创建文件夹成功");
            // 得到文件夹名
            OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);
            String fileDir = object.getKey();
            return fileDir;
        }
        return keySuffixWithSlash;
    }

    /**
     * 创建存储空间
     */
    public static String createBucketName(OSSClient ossClient, String bucketName) {
        // 存储空间
        final String bucketNames = bucketName;
        if (!ossClient.doesBucketExist(bucketName)) {
            // 创建存储空间
            Bucket bucket = ossClient.createBucket(bucketName);
            log.info("创建存储空间成功");
            return bucket.getName();
        }
        return bucketNames;
    }
}
