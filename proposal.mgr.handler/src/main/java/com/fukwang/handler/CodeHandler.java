package com.fukwang.handler;

import com.fukwang.util.CodePicture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.RenderedImage;
import java.io.IOException;

@RestController
public class CodeHandler {
    @GetMapping("/code")
    public void code(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // TODO Auto-generated method stub
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        //        获取图片及验证码
        Object[] code = CodePicture.createImage();
        //        把验证码的值存入session
        req.getSession().setAttribute("code",code[0]);
        //        将验证码图片返回给浏览器
        ImageIO.write((RenderedImage) code[1],"jpg",resp.getOutputStream());
    }
}
