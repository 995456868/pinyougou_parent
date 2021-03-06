package com.pinyougou.manager.controller;

import com.pinyougou.common.FastDFSClient;
import com.pinyougou.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVER_URL;

    @RequestMapping("upload")
    public Result upload(MultipartFile file){
        try {
            //获取文件全名
            String filename = file.getOriginalFilename();
            String extName = filename.substring(filename.lastIndexOf(".") + 1);
            FastDFSClient client = new FastDFSClient("classpath:config/tracker_server.conf");
            String path = client.uploadFile(file.getBytes(), extName);
            String url = FILE_SERVER_URL+path;
            return new Result(true,url);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"上传失败");
        }

    }
}
