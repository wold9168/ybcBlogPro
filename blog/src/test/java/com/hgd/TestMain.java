package com.hgd;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

public class TestMain {
    public static void main(String[] args) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region2());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
//...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = "J0P3vg4lhwPZDTmpM-FXku03PZvlh6mZOnYelRko";
        String secretKey = "-X8zq6E1S7aL3Zmz3BhHSzRJdqU755bQYJdf5_Gy";
        String bucket = "hgd-blog";
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "D:\\onedrive\\图片\\文档\\Tencent Files\\507086636\\nt_qq\\nt_data\\Emoji\\BaseEmojiSyastems\\EmojiSystermResource\\119\\apng\\119.png";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "testfile.png";

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            ex.printStackTrace();
            if (ex.response != null) {
                System.err.println(ex.response);

                try {
                    String body = ex.response.toString();
                    System.err.println(body);
                } catch (Exception ignored) {
                }
            }
        }

    }
}
