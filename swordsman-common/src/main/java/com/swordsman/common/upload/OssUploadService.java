package com.swordsman.common.upload;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSS;
import com.swordsman.common.web.ApiResult;
import com.swordsman.common.web.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.UUID;

/**
 * @Author DuChao
 * @Date 2019-10-21 13:10
 * 阿里 OSS 文件上传下载服务
 */
@Service
public class OssUploadService {

    @Autowired
    private OSS ossClient;

    @Autowired
    private OssConfig ossConfig;

    /**
     * 上传文件重载
     */
    public ApiResult upload(File file){

        String filePath = getFilePath(file.getName());
        // 上传到阿里云
        try {
            // 目录结构：images/2018/12/29/xxxx.jpg
            ossClient.putObject(ossConfig.getBucketName(), filePath, new
                    ByteArrayInputStream(FileUtil.readBytes(file)));
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult("文件上传失败，请联系管理员!");
        }

        return new ApiResult(ossConfig.getUrlPrefix() + filePath);
    }

    /**
     * 上传文件
     */
    public ApiResult upload(MultipartFile uploadFile) {

        // 文件新路径
        String fileName = uploadFile.getOriginalFilename();
        String filePath = getFilePath(fileName);

        // 上传到阿里云
        try {
            // 目录结构：images/2018/12/29/xxxx.jpg
            ossClient.putObject(ossConfig.getBucketName(), filePath, new
                    ByteArrayInputStream(uploadFile.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult("文件上传失败，请联系管理员!");
        }

        return new ApiResult(ossConfig.getUrlPrefix() + filePath);
    }

    private String getFilePath(String sourceFileName) {

        String dir = "images/";
        String ext = sourceFileName.substring(sourceFileName.lastIndexOf("."));
        if (ext.equals(".mp3"))
            dir = "audios/";

        DateTime dateTime = new DateTime();
        return dir + dateTime.toString("yyyy")
                + "/" + dateTime.toString("MM")
                + "/" + dateTime.toString("dd")
                + "/" + System.currentTimeMillis() + RandomUtil.randomInt(100, 9999)
                + "." + ext;
    }

}
