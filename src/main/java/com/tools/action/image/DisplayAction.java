package com.tools.action.image;

import com.tools.action.BaseAction;
import com.tools.util.FileUtils;
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
import java.net.URLDecoder;
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

    private final String ROOT_PATH = "/Users/YJ/Pictures";

    private final String[] IMG_TYPES = {"jpg", "jpeg", "png", "gif", "bmp"};

    private final String CHARSET_UTF8 = "UTF-8";

    private final String CHARSET_ISO = "ISO8859-1";

    private final String[] FILTER_NAMES = {"iPhoto 图库.migratedphotolibrary", "Photo Booth 图库", "照片 图库.photoslibrary"};

    /**
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("/home")
    public ModelAndView displayHome(HttpServletRequest request, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            File rootFile = FileUtils.createFile(ROOT_PATH);
            if(!rootFile.isDirectory()) {
                throw new RuntimeException("[" + ROOT_PATH + "] is not a directory.");
            }
            List<String> childFileNameList = getChildDirectoryList(rootFile);
            modelAndView.addObject("childDirectoryList", childFileNameList);
            modelAndView.setViewName("image/displayHome");
        } catch (Exception e) {
            logger.error("Exception in displayHome: ", e);
        }
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
        ModelAndView modelAndView = new ModelAndView();

        try {
            request.setCharacterEncoding(CHARSET_UTF8);
            String parent = null;
            if(StringUtils.isNotBlank(request.getParameter("parent"))) {
                parent = URLDecoder.decode(request.getParameter("parent"), CHARSET_UTF8);
            }
            String childDirectory = URLDecoder.decode(request.getParameter("childDirectory"), CHARSET_UTF8);
            logger.info("=====> parent:[" + parent + "], childDirectory:[" + childDirectory + "].");
            String tempPath = StringUtils.isBlank(parent) ? childDirectory : (parent + File.separator + childDirectory);
            String childPath = ROOT_PATH + File.separator + tempPath;
            logger.info("childPath: " + childPath);

            List<String> imgDatas = new ArrayList<String>();
            File childFile = new File(childPath);
            for(File file : childFile.listFiles()) {
                if(!file.isDirectory() && !file.getName().startsWith(DOT_MARKER) && isImg(getImgType(file.getAbsolutePath()))) {
                    imgDatas.add(getImgData(file.getAbsolutePath()));
                }
            }
            List<String> childDirectoryList = getChildDirectoryList(childFile);

            modelAndView.addObject("childDirectoryList", childDirectoryList);
            modelAndView.addObject("imgDatas", imgDatas);
            modelAndView.addObject("parent", tempPath);
            modelAndView.addObject("name", childDirectory);
        } catch (Exception e) {
            logger.error("Exception in displayImages: ", e);
        }

        modelAndView.setViewName("image/displayImages");
        return modelAndView;
    }

    private List<String> getChildDirectoryList(File parentFile) {
        if(parentFile == null) {
            return null;
        }
        List<String> childFileNameList = new ArrayList<String>();
        for(File childFile : parentFile.listFiles()) {
            if(!childFile.getName().startsWith(DOT_MARKER) && !inFilter(childFile.getName()) && childFile.isDirectory()) {
                childFileNameList.add(childFile.getName());
            }
        }
        return childFileNameList;
    }

    private boolean inFilter(String name) {
        for(String filter : FILTER_NAMES) {
            if(filter.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
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

    private boolean isImg(String type) {
        for (String imgType : IMG_TYPES) {
            if (imgType.equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }

    private String transcoding(String text) {
        if(StringUtils.isBlank(text)) {
            return text;
        }
        try {
            return new String(text.getBytes(CHARSET_ISO), CHARSET_UTF8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
