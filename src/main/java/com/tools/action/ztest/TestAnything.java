package com.tools.action.ztest;

import com.tools.ztest.facade.RmiMockTester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/4/29 下午2:23
 */
@Controller
@RequestMapping("/ztest")
public class TestAnything extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(TestAnything.class);

    @Autowired
    RmiMockTester rmiMockTester;

    @RequestMapping("/home")
    public ModelAndView compress(HttpServletRequest request, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("ztest/home");

        // test RmiMockInterceptor
        int i = rmiMockTester.getInt();
        logger.info("###   getInt(): {}", i);

        return mav;
    }

    @RequestMapping("/requestParam")
    public void requestParamTest(HttpServletRequest request, HttpServletResponse response, @RequestParam("param") String param) {
        logger.info("#######   params: " +  param);
        try {
            PrintWriter printWriter = response.getWriter();
            printWriter.write("param : " + param);
        } catch (Throwable t) {
            logger.error("requestParamTest unknown error.", t);
        }
    }
}
