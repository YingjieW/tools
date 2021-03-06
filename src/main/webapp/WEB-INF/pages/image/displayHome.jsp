<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: YJ
  Date: 16/9/29
  Time: 下午3:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Diplay Home</title>
    <style type="text/css">
        tr td {
            border: none;
        }
        tr.tableCaption {
            padding-bottom: 10px;
        }
    </style>
    <script type="text/javascript">
        function jump(childDirectory) {
//            window.location.href = encodeURI("/tools/display/images?childDirectory=" + childDirectory);
            window.open(encodeURI(encodeURI("/tools/display/images?childDirectory=" + childDirectory)));
        }
    </script>
</head>
<body>
<br>
<table width="20%" border="0" align="center">
    <tr class="tableCaption">
        <td align="left">
            <strong>Display Home</strong>
        </td>
    </tr>
</table>
<hr>
<table width="20%" border="0" align="center" cellpadding="10" cellspacing="0">
    <c:forEach items="${childDirectoryList}" var="childDirectory" varStatus="var">
        <tr>
            <td align="left">
                <a type="text" target="_blank"
                   style="cursor: pointer; text-decoration: underline; color: #00F;"
                    onclick="jump('${childDirectory}')">${childDirectory}</a>
            </td>
        </tr>
    </c:forEach>
</table>
<hr>
</body>
</html>
