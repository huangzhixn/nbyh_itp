<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, minimal-ui">
<title></title>
</head>

	<!-- 识别支付宝还是微信支付 -->
	<script type="text/javascript">
		var totalFee = '${requestScope.totalFee}';
		var ua = navigator.userAgent;
// 		alert(ua);
		if(ua.indexOf("Alipay") > 0){
// 			document.getElementById('payFlag').value="0";
// 			document.getElementById('form1').style.display='';
			location.href="aliPrecreate.action?totalFee="+totalFee+"&payFlag=0"
		}
		if(ua.indexOf("MicroMessenger") > 0){
// 			document.getElementById('payFlag').value="1";
// 			document.getElementById('form2').style.display='';
			location.href="code.action?totalFee="+totalFee+"&payFlag=1"
		}

</script>
</body>
</html>