<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Tools</title>
	<style type="text/css">
		tr td {
			border: none;
		}
		tr.tableCaption {
			padding-bottom: 10px;
		}
    </style>
</head>
<body>
	<br>
	<table width="20%" border="0" align="center">
		<tr class="tableCaption">
			<td align="left">
				<strong>Tools - Yingjie</strong>
			</td>
		</tr>
	</table>
	<hr>
	<table width="20%" border="0" align="center" cellpadding="10" cellspacing="0">
		<tr>
			<td align="left">
				<a href="/tools/ztest/requestParam" style="text-decoration:none;">1. RequestParam Test</a>
			</td>
		</tr>
	</table>
	<img title="点击刷新" alt="点击刷新" id="kaptchaImage" onclick="javascript:changeImage();"
		 src="/tools/captcha/show_image" height="33" width="80" style="border: #cccccc solid 1px;"/>
	<hr>

	<script>
        function changeImage() {
//            $("#kaptchaImage").attr("src" ,'/tools/captcha/show_image?n=' + Math.floor(Math.random()*100));
            var kaptchaImage = document.getElementById("kaptchaImage");
            kaptchaImage.src = "/tools/captcha/show_image?n=" + Math.floor(Math.random()*100);
        }
	</script>
</body>
</html>
