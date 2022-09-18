package com.fukwang.handler;


import com.fukwang.config.ConfigProps;
import com.fukwang.dto.DataDto;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
public class FileHandler {
    @Resource
    private ConfigProps configProps;

    @PostMapping("/doUpload")
    public DataDto<Integer> postFile(@RequestParam(value="load",required=true) MultipartFile load) throws IOException {
        FileUtils.copyToFile(load.getInputStream(),
                new File(configProps.getName() + load.getOriginalFilename()));
        DataDto dto = new DataDto();
        dto.success("文件上传成功",load.getOriginalFilename());
        return dto;
    }

    @GetMapping("/showImg")
    public void showImg(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String imgPath = req.getParameter("imgPath");
        BufferedImage image = ImageIO.read(new FileInputStream("/data1/img/" + imgPath));
        ServletOutputStream out = resp.getOutputStream();
        ImageIO.write(image, "png", out);
    }


}
