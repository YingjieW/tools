package com.tools.action.ztest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Descripe: 多线程并发测试
 *
 * @author yingjie.wang
 * @date 16/4/14 上午10:14
 */
@Controller
@RequestMapping("/thread")
public class ThreadServletTest extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ThreadServletTest.class);

    private String message = "";

    @RequestMapping("/test")
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        message = request.getParameter("message");
        logger.info("#####   " + Thread.currentThread().getName() + " : message = " + message);
        PrintWriter printWriter = response.getWriter();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("*****   " + Thread.currentThread().getName() + " : message = " + message);
        printWriter.write(message);
    }
}
