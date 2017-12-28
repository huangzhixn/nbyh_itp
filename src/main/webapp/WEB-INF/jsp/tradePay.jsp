 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, minimal-ui">
<title>信雅达主扫发起页面</title>
</head>
<style>
body{
	font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
}
form{
	padding: 20px 50px;
}
#totalFee,#authCode{
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
    background-color: #fff;
    background-image: none;
    border-radius: 4px;
    border: 1px solid #bfcbd9;
    box-sizing: border-box;
    color: #1f2d3d;
    display: inline-block;
    font-size: inherit;
    height: 45px;
    line-height: 1;
    outline: none;
    padding: 3px 10px;
    transition: border-color .2s cubic-bezier(.645,.045,.355,1);
    width: 100%;
}
#submit{
	display: inline-block;
    line-height: 1;
    white-space: nowrap;
    cursor: pointer;
    background: #fff;
    border: 1px solid #bfcbd9;
    border-color: #c4c4c4;
    color: #1f2d3d;
    -webkit-appearance: none;
    text-align: center;
    box-sizing: border-box;
    outline: none;
    margin: 0;
    -moz-user-select: none;
    -webkit-user-select: none;
    -ms-user-select: none;
    padding: 10px 15px;
    font-size: 14px;
    border-radius: 4px;
    color: #fff;
    background-color: #ff4949;
    border-color: #ff4949;
    width: 90%;
}
</style>
<body>
	<form style="text-align: center;"  action="tradePay.action" method="post">
		<div id="font" style="margin-bottom: 20px;color:#f00">信雅达</div><br/>

        <input placeholder="买家支付授权码" type="text"  name="authCode" id="authCode" /><br /><br />

        <input placeholder="支付金额" type="number"  name="totalFee" id="totalFee" /><br /><br /><br />
		<div align="center">
			<input id="submit" type="submit" value="结算"  />
		</div>
	</form>
	
</body>
</html>