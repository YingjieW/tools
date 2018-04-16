<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.page import="org.springframework.web.context.support.ServletContextResource"/>
<jsp:directive.page import="org.springframework.core.io.Resource"/>
<jsp:directive.page import="org.springframework.web.util.WebUtils"/>
<%
    Resource res3 = new ServletContextResource(application,"/WEB-INF/classes/conf/file1.txt");
    ServletOutputStream output = response.getOutputStream();
    output.print(res3.getFilename()+"<br/>");
    output.print(WebUtils.getTempDir(application).getAbsolutePath());
%>
