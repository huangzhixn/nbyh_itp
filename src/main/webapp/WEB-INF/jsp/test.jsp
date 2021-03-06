﻿<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<title> JS 生成二维码 </title>
<script src="/WEB-INF/js/qrcode.js"></script>
<script type="text/javascript">
window.onload = function(){

    // 二维码对象
    var qrcode;

    // 默认设置
    var content;
    var size;

    // 设置点击事件
    document.getElementById("send").onclick =function(){
        
        // 获取内容
        content = document.getElementById("content").value;
        content = content.replace(/(^\s*)|(\s*$)/g, "");

        // 获取尺寸
        size = document.getElementById("size").value;

        // 检查内容
        if(content==''){
            alert('请输入内容！');
            return false;
        }

        // 检查尺寸
        if(!/^[0-9]*[1-9][0-9]*$/.test(size)){
            alert('请输入正整数');
            return false;
        }

        if(size<100 || size>500){
            alert('尺寸范围在100～500');
            return false;
        }

        // 清除上一次的二维码
        if(qrcode){
            qrcode.clear();
        }

        // 创建二维码
        qrcode = new QRCode(document.getElementById("qrcode"), {
            width : size,//设置宽高
            height : size
        });

        qrcode.makeCode(document.getElementById("content").value);
    }

}
</script>
</head>

<body>
    <p>内容：<input type="text" id="content" value="http://weibo.com/fdipzone" /></p>
    <p>尺寸：<input type="text" id="size" value="150"></p>
    <p><button id="send">生成二维码</button></p>
    <div id="qrcode"></div>
</body>
</html>