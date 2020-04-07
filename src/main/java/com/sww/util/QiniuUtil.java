package com.sww.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Optional;

/**
 * @author sww
 */
@SuppressWarnings("SpellCheckingInspection")
@Component
@ConfigurationProperties(prefix = "qiniu")
@Data
public class QiniuUtil {
    private QiniuUtil() {}

    private String accessKey;
    private String secretKey;
    private String bucket;
    private String path;

    private static UploadManager uploadManager =
            new UploadManager(new Configuration(Region.region0()));

    public String uploadPicture(InputStream byteInputStream) {
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        DefaultPutRet putRet = null;
        try {
            Response response = uploadManager.put(byteInputStream, null, upToken,null, null);
            //解析上传成功的结果
            putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);

        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        if (putRet == null) {
            return "";
        }
        return putRet.key;
    }
}
