<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>图片压缩</title>
	<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
	<style type="text/css">
		tr {border: none;}
		td {border:	none;}
		tr.tableCaption {padding-bottom: 2px;}
		input {font-size: 12px;}
		.fontS {font-size: 14px;}
		.show_img{width: 250px; height: 150px; background-color: #AAA; margin:5px 0px 10px 0px;}
		.show_img img{width: 250px; height: 150px;}
		.arrow {width: 100px; heght: 70px;}
	</style>
	<script type="text/javascript">
		$(document).ready(function(){
		})

		var ctxPath = "/tools";

		/**
		 * 压缩图片
		 */
		function compressImage() {
			var name = $("#imageToUpload").val();
			if(!name){
				alert("请从本地选择图片！");
				return;
			}
			if(!checkTargetSize() || !checkScale()) {
				return;
			}
			var size = getSize("imageToUpload");
			var targetSize = $("#targetSize").val();
			if(size <= targetSize) {
				alert("无需压缩 ^_^");
				return;
			}
			var suffix = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
			if (suffix != "jpg" && suffix != "jpeg" && suffix != "png") {
				alert("请上传jpg或jpeg或png图片");
				return
			}
			uploadImage();
		}

		/**
		 * 上传图片到后台, 后台实现图片压缩
		 */
		function uploadImage() {
			var targetSize = $("#targetSize").val();
			var scale = $("#scale").val()/100;
			var url = ctxPath + "/compress/doCompress";

			//获取文件名
			var fullName = $("#imageToUpload").val();
			var name = fullName.substring(fullName.lastIndexOf("fakepath") + 9);

			$.ajaxFileUpload({
				url: url,
				secureuri:false,
				fileElementId:'imageToUpload',
				type: 'post',
				dataType: 'json',
				data:{targetSize : targetSize,
					scale : scale,
					name : name
				},
				success: function (data, status) {
					if(data.success) {
						var message = JSON.parse(data.message);
						$("#imageCompressed").attr('src', message.imageData);
						$("#newName").val(message.newName);
						$("#newSize").html(message.newSize + " KB");
					} else {
						var message = JSON.parse(data.message);
						alert(message.error);
					}
				},
				error: function (data, status, e) {
					alert(e);
				}
			});
		}

		/**
		 * 从 file 域获取 本地图片 url
		 */
		function getFileUrl(sourceId) {
			var url;
			if (navigator.userAgent.indexOf("MSIE")>=1) { // IE
				url = document.getElementById(sourceId).value;
			} else if(navigator.userAgent.indexOf("Firefox")>0) { // Firefox
				url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
			} else if(navigator.userAgent.indexOf("Chrome")>0) { // Chrome
				url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
			}
			return url;
		}

		/**
		 * 将本地图片 显示到浏览器上
		 */
		function preImg(sourceId, targetId) {
			$("#imageCompressed").attr('src', '');
			$("#newSize").html("- KB");
			var url = getFileUrl(sourceId);
			var imgPre = document.getElementById(targetId);
			imgPre.src = url;
			var fileSize = getSize(sourceId);
			$("#oldSize").html(fileSize + " KB");
		}

		/**
		 * 计算图片的大小
		 */
		function getSize(target) {
			if(!target) {
				return;
			}
			var fileSize = 0;
			if (window.attachEvent) {
				alert("window - test");
				$("#"+target).select();
				var filePath = document.selection.createRange().text;
				try {
					var fso = new ActiveXObject("Scripting.FileSystemObject");
				} catch (e) {
					alert('请将浏览器安全级别调低，或换用其它非IE内核浏览器上传');
				}
				fileSize = fso.GetFile(filePath).size;
			} else {
				fileSize = $("#"+target)[0].files[0].size;
			}
			var size = fileSize / 1024;
			//取整，有小数则向上加1
			return Math.ceil(size);
		}

		/**
		 * 下载图片
		 */
		function downloadImage() {
			var fileName = $("#newName").val();
			var imgData = $("#imageCompressed").attr("src");
			//var content = imgData.substring(imgData.indexOf(",")+1);
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

		/**
		 * 校验targetSize: 正整数
		 */
		function checkTargetSize() {
			var targetSize = $("#targetSize").val();
			if(!targetSize) {
				alert("请输入目标大小！");
				return false;
			}
			var regex = /^[1-9]*[1-9][0-9]*$/;
			if(regex.test(targetSize)) {
				return true;
			} else {
				alert("必须为正整数！");
				return false;
			}
		}

		/**
		 * 校验scale：小于100的正整数
		 */
		function checkScale() {
			var scale = $("#scale").val();
			if(!scale) {
				alert("Please enter %, or % will be set to 50%.");
				return true;
			}
			var regex = /^[1-9][0-9]?$/;
			if(regex.test(scale)) {
				return true;
			} else {
				alert(scale + "%: must be less than 100%.")
				return false;
			}
		}

		/**
		 * 文件下载 - 有点问题 - 暂未使用该方法
		 */
		/* 		function downloadFile(fileName, content){
		 var aLink = document.createElement('a');
		 var blob = new Blob([content]);
		 var evt = document.createEvent("HTMLEvents");
		 evt.initEvent("click", false, false);
		 aLink.download = fileName;
		 aLink.href = URL.createObjectURL(blob);
		 aLink.dispatchEvent(evt);
		 } */
	</script>
</head>
<body>
<table border="0" align="center">
	<tr class="tableCaption">
		<td align="left">
			<h3>图 片 压 缩</h3>
		</td>
	</tr>
</table>
<hr>
<table border="0" align="center" cellpadding="20">
	<tr>
		<td class="upload_title" colspan="1" align="center" style="font-size:20px;">Source</td>
		<td></td>
		<td class="upload_title" colspan="1" align="center" style="font-size:20px;">Target</td>
	</tr>
	<tr>
		<td>
			<input type="file" name="imageToUpload" id="imageToUpload" onchange="preImg(this.id,'imgPre');" />
			<br><br>
			<div class="show_img">
				<img id="imgPre" src="" style="display: block;" />
			</div>
			<br>
			<span class="fontS">Size:</span>
			<span id="oldSize" class="fontS"></span>
			<!-- <br><br>
            <span class="fontS">NewS:</span>
            <input class="fontS" value="512" id="targetSize" size="5" onblur="checkTargetSize()"/>
            <span class="fontS"> KB</span>
            <br><br>
            <span class="fontS">Scal:</span>
            <input class="fontS" value="0.5" id="scale" size="5" onblur="checkScale()"/> -->
			<br><br>
			<input type="button" id="doCompress" value=" 压 缩 图 片 " onclick="compressImage()"/>
		</td>
		<td>
			<img class="arrow" src="/tools/images/arrow01.png" />
			<br>
			&nbsp;&nbsp;<input class="fontS" value="512" id="targetSize" size="4" onblur="checkTargetSize()"/>
			<span class="fontS"> KB</span>
			<br><br>
			&nbsp;&nbsp;<input class="fontS" value="50" id="scale" size="4" onblur="checkScale()"/>
			<span class="fontS"> %</span>
		</td>
		<td>
			<span class="fontS">&nbsp;</span> <span id="path" class="fontS"></span>
			<input type="hidden" id="newName" name="newName" />
			<br><br>
			<div class="show_img">
				<img id="imageCompressed" src="" style="display: block;" />
			</div>
			<br>
			<span class="fontS">Size:</span> <span id="newSize" class="fontS">- KB</span>
			<br><br>
			<input type="button" onclick="downloadImage()" value=" 下 载 图 片 " />
		</td>
	</tr>
</table>
<br><br><br>
<hr>
</body>
</html>
