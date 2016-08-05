<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>HTML转PNG</title>
    <%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
    <style type="text/css">
        tr td {border: none;}
        tr.tableCaption {padding-bottom: 10px;}
        .fontS {font-size: 14px;}
    </style>
    <script type="text/javascript">
        $(document).ready(function(){
        })

        var ctxPath = "/tools";

        /**
         * html - png
         */
        function convertAndDownload() {
            var targetUrl = $("#targetUrl").val();
            var actionUrl = ctxPath + "/html2png/convert";
            $.ajax({
                url : actionUrl,
                type : "POST",
                async : false,
                cache : false,
                dataType : "json",
                data : {
                    targetUrl : targetUrl
                },
                success : function(data) {
                    if(data.success) {
                        var message = JSON.parse(data.message);
                        $("#pngData").attr('src', message.pngData);
                        $("#pngName").html(message.pngName + ".png");
                        $("#pngSize").html(message.pngSize + " KB");
                        downloadImage();
                    } else {
                        var message = JSON.parse(data.message);
                        alert(message.error);
                    }
                },
                error : function(data) {
                    alert("Unknown Error, Please Try Again!")
                }
            });
        }

        /**
         * 下载图片
         */
        function downloadImage() {
            var fileName = $("#pngName").val();
            var imgData = $("#pngData").attr("src");
            downloadURI(imgData, fileName);
        }

        /**
         * 下载uri数据
         */
        function downloadURI(uri, name) {
            var link = document.createElement("a");
            link.download = name;
            link.href = uri;
            link.click();
        }
    </script>
</head>
<body>
<br>
<table width="80%" border="0" align="center">
    <tr class="tableCaption">
        <td align="center">
            <strong>将html页面转化为png格式图片</strong>
        </td>
    </tr>
</table>
<hr>
<table width="80%" border="0" align="center" cellpadding="10" cellspacing="0">
    <tr>
        <td align="left">
            <span class="fontS">输入网址: </span>&nbsp;
            <input class="fontS" id="targetUrl" size="80" value="http://kuka.im/2016/06/25/%E7%94%A8java%E5%B0%86html%E8%BD%AC%E5%8C%96%E4%B8%BA%E5%9B%BE%E7%89%87/"/>
        </td>
    </tr>
    <tr>
        <td align="left">
            <input type="button" id="doCompress" value="转换并下载" onclick="convertAndDownload()"/>
            &nbsp;&nbsp;
            <span class="fontS">PngSize:</span> <span id="pngSize" class="fontS">- KB</span>
            &nbsp;&nbsp;
            <span class="fontS">PngName:</span> <span id="pngName" class="fontS">-</span>
        </td>
    </tr>
</table>
<hr>
<table width="80%" border="0" align="center" cellpadding="10" cellspacing="0">
    <tr>
        <td align="center">
            <img id="pngData" alt="">
        </td>
    </tr>
</table>
<hr>
</body>
</html>
