<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <style type="text/css">
        /*.imgDisplay {width: 62%; height: 62%;}*/
        .imgDisplay {width: 80%; height: 80%;}
    </style>
    <script type="text/javascript">
        function openTheImage(imgData) {
            alert("window.location.href ");
            window.location.href = imgData;
        }
    </script>
</head>
<body>
<h2 align="middle">${name}</h2>
<hr>
<c:forEach items="${imgDatas}" var="imgData" varStatus="var">
    <br><br>
    <p align="middle">
        <a type="text" target="_blank"
           style="cursor: pointer; text-decoration: underline;"
           href="${imgData}">
            <img class="imgDisplay" src="${imgData}">
        </a>
    </p>
    <br><br>
    <hr>
</c:forEach>
</body>
</html>
