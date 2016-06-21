package com.tools.action.image;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tools.action.BaseAction;
import com.tools.utils.CheckUtils;
import com.tools.utils.ExpressionUtils;
import com.tools.utils.FileUtils;
import com.tools.utils.ImageUtils;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

;

/**
 * 
 * @author：yingjie.wang
 * @since：2016年2月21日 下午11:12:09 
 * @version:
 */
@Controller
@RequestMapping("/image")
public class CompressAction extends BaseAction{

    private static final Logger logger = LoggerFactory.getLogger(CompressAction.class);

    @Autowired
    Test test;
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 跳转至图片压缩页面
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("/compress")
    public ModelAndView compress(HttpServletRequest request, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("image/compress");
        test.printLog();
        test.testStatic();
        logger.info("###   testString: " + test.testString());
        logger.info("###   appName : " + applicationContext.getApplicationName());
        try {
            String expression = "JsonBase->List:[\"test1\",\"key2\",\"key3\"]";
            List<String> list = (List) ExpressionUtils.parseJsonBaseExpression(expression);
            logger.info("###   list: " + JSON.toJSONString(list));
        } catch (Throwable t) {
            logger.error("!!! expression parse exception.");
        }
        return mav;
    }
    
    /**
     * 图片压缩处理
     * @param request
     * @param response
     */
    @RequestMapping("doCompress")
    public void doCompress(HttpServletRequest request, HttpServletResponse response) {
        
        response.setContentType("text/html;charset=utf-8");
        
        // 用于判断是普通表单，还是带文件上传的表单。
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if(!isMultipart) {
            writeResponse(response, false, "未上传图片");
            return;
        }
        
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("targetSize", request.getParameter("targetSize"));
        paramMap.put("scale", request.getParameter("scale"));
        paramMap.put("name", request.getParameter("name"));

        logger.info("#####   targetSize = " + paramMap.get("targetSize"));
        logger.info("#####   scale = " + paramMap.get("scale"));
        logger.info("#####   name = " + paramMap.get("name"));
        
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
        MultipartFile multipartFile = multipartRequest.getFile("imageToUpload");
        
        JSONObject jsonObject = new JSONObject();
        String imageData = null;
        try {
             int targetSize = 512;
             if(!CheckUtils.isEmpty(paramMap.get("targetSize"))) {
                 targetSize = Integer.parseInt((String)paramMap.get("targetSize"));
             };
            
            float scale = 0.5f;
            if(!CheckUtils.isEmpty(paramMap.get("scale"))) {
                scale = Float.parseFloat((String)paramMap.get("scale"));
            };
            
            String realPath = request.getSession().getServletContext().getRealPath("/") + File.separator + "saveImages";
            File sourceImg = buildFile(realPath, (String)paramMap.get("name"));
            FileUtils.writeByBytes(sourceImg, multipartFile.getBytes());
            if(sourceImg == null || sourceImg.length() == 0) {
                throw new RuntimeException("sourceImg must not be null");
            }

            logger.info("#####   store_path = " + sourceImg.getAbsolutePath());
            
            if(sourceImg.length()/1024 <= targetSize) {
                writeResponse(response, false, "无需压缩 ^_^");
                return;
            }
            
            File targetImg = createTargetImg(sourceImg);
            if(targetImg == null) {
                throw new RuntimeException("sourceImg must not be null");
            }
            
            ImageUtils.compress(sourceImg, targetImg, targetSize, scale);
            String targetName = targetImg.getName();
            String fileType = targetName.substring(targetName.lastIndexOf(".")+1, targetName.length());
            imageData = "data:image/" + fileType + ";base64," + FileUtils.writeToBase64(new FileInputStream(targetImg));
            jsonObject.put("imageData", imageData);
            jsonObject.put("newName", targetImg.getName());
            jsonObject.put("newSize", targetImg.length()/1024);
            // 释放资源
            paramMap = null;
            targetImg = null;
            System.gc();
            // 返回jsp页面
            writeResponse(response, true, jsonObject.toJSONString());
        } catch (Throwable t) {
            logger.error("系统异常/(ㄒoㄒ)/~~~", t);
            String errorMsg = "";
            if (t instanceof java.lang.OutOfMemoryError) {
                errorMsg = "内存不足，压缩失败，请调整tomcat的内存大小。";
            } else {
                errorMsg = "系统异常，图片压缩失败！";
            }
            logger.info("#####   errorMsg: " + errorMsg);
            logger.info("#####   error: System Error, Please try later again.");
            //writeResponse(response, false, errorMsg);
            jsonObject.put("error", errorMsg);
            try {
                writeResponse(response, false, jsonObject.toJSONString());
            } catch(Throwable th) {
                logger.error("这也能挂。。。", th);
            }
            return;
        }
    }
    
    /**
     * 创建文件
     * @param path
     * @param name
     * @return
     * @throws IOException
     */
    private File buildFile(String path,String name) throws IOException{
        String filePath = path + File.separator + "saveImages" + File.separator + name;
        File file = new File(filePath);
        if(file.exists()) {
            file.delete();
        }
        file.getParentFile().mkdirs();
        file.createNewFile();
        return file;
    }
    
    /**
     * 创建目标文件
     * @param source
     * @return
     * @throws IOException
     */
    private File createTargetImg(File source) throws IOException{
        String sourceName = source.getName();
        String targetName = sourceName.substring(0, sourceName.lastIndexOf(".")) + "_Compressed." + 
                              sourceName.substring(sourceName.lastIndexOf(".")+1, sourceName.length()).toLowerCase();
        String targetPath = source.getParentFile().getAbsolutePath() + File.separator + targetName;
        File target = new File(targetPath);
        if(target.exists()) {
            target.delete();
        }
        target.getParentFile().mkdirs();
        target.createNewFile();
        return target;
    }
}
