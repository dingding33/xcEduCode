package com.xuecheng.api.fdfs;

import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import io.swagger.annotations.Api;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "文件上传接口",description = "提供文件上传功能")
public interface FileSystemControllerAPi {

    /**
     * 上传文件
     * @param multipartFile 文件
     * @param filetag 文件标签
     * @param businesskey 业务id
     * @param metadata 元信息，json格式
     * @return
     */
    UploadFileResult upload(MultipartFile multipartFile,String filetag,String businesskey,String metadata);

}
