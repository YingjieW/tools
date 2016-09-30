<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: YJ
  Date: 16/9/28
  Time: 下午5:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Image Display</title>
    <link rel="stylesheet" type="text/css" href="/tools/css/jquery-foxibox-0.2.css" />
    <script type="text/javascript" src="/tools/js/jquery-1.3.2.min.js"></script>
    <script type="text/javascript" src="/tools/js/jquery-foxibox-0.2.min.js"></script>
    <style type="text/css">
        .imgDisplay {height: 80%;}
        tr td {border: none;}
        .topBottom {width: 50%;}
        #updown{position:fixed; top:35%; right:-5%;}
    </style>
    <script type="text/javascript">
        $(document).ready(function(){
            $('a[rel]').foxibox();
        });
    </script>
</head>
<body>
<h2 id="top" align="middle">${name}</h2>
<p align="right">
    <a href="#bottom" target="_self">直达底部</a>&nbsp;&nbsp;&nbsp;
    <a href="#bottom" target="_self">直达底部</a>
</p>
<hr>
<table width="20%" border="0" align="center" cellpadding="10" cellspacing="0">
    <c:forEach items="${childDirectoryList}" var="childDirectory" varStatus="var">
        <tr>
            <td align="left">
                <a type="text" target="_blank"
                   style="cursor: pointer; text-decoration: underline;"
                   href="/tools/display/images?childDirectory=${childDirectory}&parent=${parent}">${childDirectory}</a>
            </td>
        </tr>
    </c:forEach>
</table>
<c:if test="${childDirectoryList!=null && fn:length(childDirectoryList)>0}"><hr></c:if>
<c:forEach items="${imgDatas}" var="imgData" varStatus="var">
    <br><br>
    <p align="middle">
        <a type="text" rel="[gall1]" style="cursor: pointer;" href="${imgData}">
            <img class="imgDisplay" src="${imgData}">
        </a>
    </p>
    <br><br>
    <hr>
</c:forEach>
<p id="bottom" align="right">
    <a href="#top" target="_self">返回顶部</a> &nbsp;&nbsp;&nbsp;
    <a href="javascript:scroll(0,0)">返回顶部</a>
</p>
<div id="updown">
    <a href="#top" target="_self" >
        <img class="topBottom" src="/tools/images/up.jpeg">
    </a>
    <a href="#bottom" target="_self" >
        <img class="topBottom" src="/tools/images/down.png">
    </a>
</div>
</body>
</html>
