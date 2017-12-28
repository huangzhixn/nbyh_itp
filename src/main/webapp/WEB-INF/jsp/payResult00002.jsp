<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, minimal-ui">
<title>主扫通知页面</title>
</head>
<style>
*{
	margin: 0;
	padding: 0;
}
body{
	font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
}
.vux-header {
    position: relative;
    padding: 10px 0;
    box-sizing: border-box;
    background-color: #35495e;
}
.vux-header-title-area, .vux-header .vux-header-title {
    margin: 0 88px;
    height: 40px;
    width: auto;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
.vux-header .vux-header-title {
    line-height: 40px;
    text-align: center;
    font-size: 18px;
    font-weight: 400;
    color: #fff;
}
</style>
<body style="background: #eee;">
	<!--您的交易 ：${requestScope.resultCode }-->
	<div class="vux-header" style="background: rgb(50, 90, 152);">
		<h1 class="vux-header-title">收款结果</h1>
	</div>
	<div class="box" style="text-align: center;margin-top: 50px;">
		<img style="width: 100px;height: 100px;" src="./pay_icon01.png" alt="银联收款调用失败!" />
		<p style="font-size:18px;margin-top: 20px;">银联收款失败，验证签名失败！</p>
	</div>
</body>
</html>