<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, minimal-ui">
<title>信雅达被扫发起页面</title>
</head>
<style>
*{
	margin: 0;
	padding: 0;
}
body{
	font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
}
#submit{
    background: url('./btn_icon02.png') no-repeat #325a98 32% 50%;
    background-size: 10%;
    position: relative;
    display: block;
    margin-left: auto;
    margin-right: auto;
    padding-left: 14px;
    padding-right: 14px;
    box-sizing: border-box;
    font-size: 18px;
    text-align: center;
    text-decoration: none;
    color: #FFFFFF;
    line-height: 2.33333333;
    border-radius: 5px;
    -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
    overflow: hidden;
	top: 160px;
	width: 85%;
	border: none;
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
.inputBox{
	height: 130px;
	background-color: #325a98;
    position: relative;
}
.inputBox .input {
    position: absolute;
    bottom: 0.5rem;
    right: 1rem;
    display: inline-block;
    font-size: 50px;
    width: 80%;
    color: white;
    padding-left: 30px;
    background: url('./rmb.png') no-repeat 0% 64%;
    outline: none;
    border: none;
}
#submit:active{
	background-color: #425c9a;
}
</style>
<body style="background: #eee;">
	<div class="vux-header" style="background: rgb(50, 90, 152);">
		<h1 class="vux-header-title">商户：${requestScope.mchntCd}</h1>
	</div>
	<div class="inputBox">
		<form id="form1" style="height:130px;display:none" action="aliPrecreate.action" method="post">
	        <!--<div id="font" style="margin-left:2%;margin-bottom: 20px;color:#f00">信雅达</div><br/>-->
	        <input autofocus="true" class="input"  type="number" required step="0.01"  name="totalFee"; id="totalFee"/>
	        <input placeholder="支付类型标记" style="height:35px;margin-bottom: 40px;"  type="hidden" id="payFlag" name="payFlag" />	
			<input type="submit" value="支付" id="submit" />
		</form>
		<form id="form2" style="height:130px;display:none" action="code.action" method="post">
	        <!--<div id="font" style="margin-left:2%;margin-bottom: 40px;color:#f00">商户：信雅达科技有限公司</div><br/>-->
	        <input class="input" autofocus="true" type="number" required step="0.01"  name="totalFee"; id="totalFee"/>
	        <input placeholder="支付类型标记"  style="height:45px;margin-bottom: 40px;"  type="hidden" id="payFlag2" name="payFlag" />	
			<div >
				<input type="submit" value="支付" id="submit" />
			</div>
		</form>
	</div>
	<!-- 识别支付宝还是微信支付 -->
	<script type="text/javascript">
		var ua = navigator.userAgent;
// 		alert(ua);
		if(ua.indexOf("Alipay") > 0){
			document.getElementById('payFlag').value="0";
			document.getElementById('form1').style.display='';
		}
		if(ua.indexOf("MicroMessenger") > 0){
			document.getElementById('payFlag').value="1";
			document.getElementById('form2').style.display='';
		}

</script>
</body>
</html>