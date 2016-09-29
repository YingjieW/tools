package com.tools.action.image;

import com.alibaba.fastjson.JSON;
import com.tools.action.BaseAction;
import com.tools.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Descripe: 展示本地图片
 *
 * @author yingjie.wang
 * @since 16/9/28 下午5:21
 */
@Controller
@RequestMapping("/display")
public class DisplayAction extends BaseAction {

    private static final Logger logger = LoggerFactory.getLogger(DisplayAction.class);

    private final String IMG_DATA_PREFIX = "data:image/";

    private final String IMG_DATA_INFIX = ";base64,";

    private final String DOT_MARKER = ".";

    private final String PARENT_FILE_PATH = "/Users/YJ/Pictures/看大图";

    /**
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("/home")
    public ModelAndView displayHome(HttpServletRequest request, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        List<String> childFileNameList = new ArrayList<String>();
        try {
            File parentFile = FileUtils.createFile(PARENT_FILE_PATH);
            if(!parentFile.isDirectory()) {
                throw new RuntimeException("[" + PARENT_FILE_PATH + "] is not a directory.");
            }
            File[] childFiles = parentFile.listFiles();
            for(File file : childFiles) {
                if(!file.getName().startsWith(DOT_MARKER)) {
                    childFileNameList.add(file.getName());
                }
            }
            modelAndView.addObject("childFileNameList", childFileNameList);
        } catch (Exception e) {
            logger.error("Exception in displayHome: ", e);
        }
        modelAndView.setViewName("image/displayHome");
        return modelAndView;
    }

    /**
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("/images")
    public ModelAndView displayImages(HttpServletRequest request, HttpSession session) {
        logger.info("displayImages: " + JSON.toJSONString(getReqeustParams(request)));
        ModelAndView mav = new ModelAndView();
        String childFileName = request.getParameter("childFileName");
        String childFilePath = PARENT_FILE_PATH + File.separator + childFileName;
        List<String> imgDatas = new ArrayList<String>();
        try {
            File childFile = new File(childFilePath);
            for(File file : childFile.listFiles()) {
                if(!file.getName().startsWith(DOT_MARKER)) {
                    imgDatas.add(getImgData(file.getAbsolutePath()));
                }
            }
            mav.addObject("imgDatas", imgDatas);
            mav.addObject("name", childFileName);
        } catch (Exception e) {
            logger.error("Exception in displayImages: ", e);
        }
        mav.setViewName("image/displayImages");
        return mav;
    }

    private String getImgData(String url) {
        if(StringUtils.isBlank(url)) {
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        String imgType = getImgType(url);
        try {
            buffer.append(IMG_DATA_PREFIX).append(imgType).append(IMG_DATA_INFIX)
                    .append(FileUtils.writeToBase64(new FileInputStream(new File(url))));
            return buffer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getImgType(String url) {
        if(StringUtils.isBlank(url)) {
            return null;
        }
        if(url.lastIndexOf(DOT_MARKER) > 0) {
            return url.substring(url.lastIndexOf(".")+1);
        } else {
            throw new RuntimeException("Can not get image type from url:[" + url + "]");
        }
    }
}
