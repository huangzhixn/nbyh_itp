<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>信雅达支付宝支付</title>
</head>

<body>
	<input type="hidden" id="url" name="url" value="${requestScope.qrcode}"/>

<!-- 跳转到支付宝的支付url -->
	<script type="text/javascript">
		var a=document.getElementById("url").value;  
 		location.href=a;
// 		alert("${pageContext.request.contextPath }/toPay.action");
//  		window.location="${pageContext.request.contextPath }/toPay.action";
// 		location.href="http://172.16.17.18:8083/nbyh_itp/toPay.action";
	</script>
</body>

</html>