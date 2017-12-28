<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, minimal-ui">
<title>信雅达微信支付</title>

</head>

<body>
<%-- 	<input type="hidden" id="url" name="url" value="${requestScope.qrcode}"/> --%>
<%-- 		<a href="${requestScope.qrcode}">点击支付</a> --%>
<!-- 跳转到微信的支付url -->
	<script type="text/javascript">
	function onBridgeReady(){
		var appId= '${requestScope.appId}';
		var timeStamp= '${requestScope.timeStamp}';
		var nonceStr= '${requestScope.nonceStr}';
		var packageName= '${requestScope.packageName}';
		var signType= '${requestScope.signType}';
		var paySign= '${requestScope.paySign}';
// 		alert("appid:"+appId);
// 		alert("时间戳:"+timeStamp);
// 		alert("perpayid:"+packageName);
// 		alert("sigeType:"+signType);
// 		alert("nonceStr:"+nonceStr);
// 		alert("paySign:"+paySign);
		
   WeixinJSBridge.invoke(  	 		 
                   'getBrandWCPayRequest', {        
                       "appId":appId,     //公众号名称，由商户传入       
                       "timeStamp":timeStamp,         //时间戳，自1970年以来的秒数       
                       "nonceStr":nonceStr, //随机串       
                       "package":packageName,       
                       "signType":signType,         //微信签名方式:       
                       "paySign":paySign    //微信签名   
                   },   
                   function(res){       
                       if(res.err_msg == "get_brand_wcpay_request:ok" ) {                             
                           alert("支付成功");  
                           window.location="${pageContext.request.contextPath }/toPay.action";
                       }else if("get_brand_wcpay_request:cancel"){
                    	   alert("已取消支付");  
                           window.location="${pageContext.request.contextPath }/toPay.action";
                       }               
                       else{
                    	   alert(res);
                    	   alert(res.err_msg);
                    	   alert("支付失败");  
                    	   window.location="${pageContext.request.contextPath }/toPay.action";
                       }
                   }  
   ); 
}
if (typeof WeixinJSBridge == "undefined"){
   if( document.addEventListener ){
       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
   }else if (document.attachEvent){
       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
   }
}else{
   onBridgeReady();
} 	
	</script>
</body>

</html>