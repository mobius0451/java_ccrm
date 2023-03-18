package com.ccrm.controller.system;

import com.ccrm.domain.ResponseResult;
import com.ccrm.domain.model.LoginUser;
import com.ccrm.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @CreateTime: 2022-11-27 21:22
 * @Description: 通用请求处理
 */
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${ccrm.filePath}")
    private String filePath;

    /**
     * 文件下载
     * @param fileName
     * @param response
     */
    @GetMapping("/download")
    public void fileDownload(String fileName, HttpServletResponse response) {
        try {
            String pathName = filePath + fileName;
            //通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(pathName);
            //通过输出流将文件写回浏览器，在浏览器展示
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while (( len = fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            //关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            log.info("文件下载失败！");
            e.printStackTrace();
        }
    }

    /**
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public ResponseResult upload(MultipartFile file){   //file是一个临时文件，本次请求结束后会被删除
        if (!file.isEmpty()) {
            LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            //判断目录是否存在，不存在则新建
            String userPath = filePath + loginUser.getUsername() + "/";
            File dir = new File(userPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            try {
                //获取原文件后缀
                String originalFilename = file.getOriginalFilename();
                String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                //使用UUID生成随机字符串作为文件名，防止重名覆盖
                String fileName = UUID.randomUUID().toString() + suffix;
                //转存文件
                file.transferTo(new File(userPath + fileName));
                return ResponseResult.success(fileName);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseResult.error("文件上传失败！");
            }
        }
        return ResponseResult.error("文件上传异常，请联系管理员");
    }

    /**
     * 通用上传请求（多个）
     */
    @PostMapping("/uploads")
    public ResponseResult uploadFiles(List<MultipartFile> files) {
        if (StringUtils.isNotEmpty(files)) {
            LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            //判断目录是否存在，不存在则新建
            String userPath = filePath + loginUser.getUsername() + "/";
            File dir = new File(userPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            try {
                ArrayList<String> fileNames = new ArrayList<>();
                for (MultipartFile file : files) {
                    //获取原文件后缀
                    String originalFilename = file.getOriginalFilename();
                    String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                    //使用UUID生成随机字符串作为文件名，防止重名覆盖
                    String fileName = UUID.randomUUID().toString() + suffix;
                    //转存文件
                    file.transferTo(new File(userPath + fileName));
                    fileNames.add(fileName);
                }
                return ResponseResult.success(fileNames);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.error("文件上传失败！");
            }
        }
        return ResponseResult.error("文件上传异常，请联系管理员");
    }

}
