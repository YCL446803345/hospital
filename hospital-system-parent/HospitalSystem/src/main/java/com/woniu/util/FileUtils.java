package com.woniu.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * 文件上传工具类
 */
public class FileUtils {

    private static String accessKeyId = "LTAI5tPK9kcoV5Fujbyhyq3D";
    private static String accessKeySecret = "uTJDLy0Dc55e9nHr0yshk9cRn0eSB0";
    private static String bucketName = "woniu-30";

    public static String uploadFile(MultipartFile file){
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";

        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //生成目标位置
        String objectName = "pic/"+ UUID.randomUUID().toString().replace("-","")+suffix;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            InputStream inputStream = file.getInputStream();
            // 创建PutObject请求。
            ossClient.putObject(bucketName, objectName, inputStream);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (Exception ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return "https://"+bucketName+".oss-cn-beijing.aliyuncs.com/"+objectName;
    }
}
