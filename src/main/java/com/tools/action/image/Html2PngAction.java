package com.tools.action.image;

import com.alibaba.fastjson.JSONObject;
import com.tools.action.BaseAction;
import com.tools.utils.FileUtils;
import org.fit.cssbox.demo.ImageRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/5 上午11:21
 */
@Controller
@RequestMapping("/html2png")
public class Html2PngAction extends BaseAction {

    private static final Logger logger = LoggerFactory.getLogger(Html2PngAction.class);

    /**
     * html转png
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("/home")
    public ModelAndView compress(HttpServletRequest request, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("image/html2png");
        return mav;
    }

    @RequestMapping("/convert")
    public void convert(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            // 获取网址
            String targetUrl = request.getParameter("targetUrl");

            // 创建png文件
            String realPath = request.getSession().getServletContext().getRealPath("/") + File.separator + "cssbox";
            String pngName = "html2png_" + System.currentTimeMillis();
            File file = FileUtils.createFile(realPath + File.separator + pngName + ".png");

            // 调用第三方组件CssBox,将html转化为png
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ImageRenderer imageRenderer = new ImageRenderer();
            boolean result = imageRenderer.renderURL(targetUrl, fileOutputStream, ImageRenderer.Type.PNG);
            if(!result) {
                throw new RuntimeException("Convert Failed, Please Try Again!");
            }

            // 组装参数并返回
            String pngData = "data:image/png;base64," + FileUtils.writeToBase64(new FileInputStream(file));
            String pngSize = String.valueOf(file.length()/1024);
            jsonObject.put("pngData", pngData);
            jsonObject.put("pngName", pngName);
            jsonObject.put("pngSize", pngSize);
            writeResponse(response, true, jsonObject.toJSONString());
        } catch (Throwable t) {
            logger.error("html2png error", t);
            String error = t.getMessage();
            jsonObject.put("error", error);
            writeResponse(response, false, jsonObject.toJSONString());
        }
    }
}
