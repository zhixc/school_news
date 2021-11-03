package org.zxc.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zxc.common.lang.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * 文件上传下载控制层
 */
@RestController
@RequestMapping("/files")
public class FileController {

    // 获取当前后端项目的服务器端口号
    @Value("${server.port}")
    private String port;

    private static final String ip = "http://localhost";

    /**
     * 文件上传
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public Result upload(@RequestPart("file") MultipartFile file) throws IOException {

        // 判断文件是否为空
        if (!file.isEmpty()){
            // 进入这里说明文件不为空
            // 将文件保存到服务器，先获取文件原来的名字
            String originalFilename = file.getOriginalFilename();

            /**
             * 调用 hutool 工具类，定义文件的唯一标识
             */
            String flag = IdUtil.fastSimpleUUID();

            /**
             * System.getProperty("user.dir")获取当前项目的路径
             * 由于我这个项目的后端服务器项目是在school_news/springboot/下
             * 所以后面要拼接上 /springboot/src/main/resources/files
             * 接着调用Springboot提供的 transferTo() 方法将文件写入
             */
            String rootFilePath = System.getProperty("user.dir")
                    + "/springboot/src/main/resources/files/"
                    + flag + "_" // 补上生成的唯一标识
                    + originalFilename;
            File newFile = new File(rootFilePath);
            file.transferTo(newFile);
            // 返回结果 url
            return Result.success(ip + ":" + port + "/files/" + flag);
        }
        return Result.fail("文件上传失败！");
    }

    /**
     * 文件下载
     * @param flag
     * @param response
     */
    @GetMapping("/{flag}")
    public void getFile(@PathVariable String flag, HttpServletResponse response) {
        OutputStream os; // 新建一个输出流对象
        // 定义文件获取的根路径
        String basePath = System.getProperty("user.dir") + "/springboot/src/main/resources/files/";
        // 调用 hutool 工具类获取所有文件名
        List<String> fileNames = FileUtil.listFileNames(basePath);
        // 根据 flag 查找文件
        String fileName = fileNames.stream()
                .filter(name -> name.contains(flag))
                .findAny().orElse("");

        try{
            if (StrUtil.isNotEmpty(fileName)){

                response.addHeader("Content-Disposition", "attachment;filename="
                        + URLEncoder.encode(fileName, "UTF-8"));

                response.setContentType("application/octet-stream");
                // 调用 hutool 工具类, 通过文件路径读取文件
                byte[] bytes = FileUtil.readBytes(basePath + fileName);
                // 通过输出流输出文件
                os  = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        }catch (Exception e) {
            System.out.println("文件下载失败！");
        }
    }
}
