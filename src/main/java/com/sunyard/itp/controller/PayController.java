package com.sunyard.itp.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.sunyard.itp.constant.CommonConst;
import com.sunyard.itp.constant.PayConst;
import com.sunyard.itp.entity.Message;
import com.sunyard.itp.entity.PrecreateParams;
import com.sunyard.itp.entity.TradePayParams;
import com.sunyard.itp.service.PrecreateService;
import com.sunyard.itp.service.RefundService;
import com.sunyard.itp.service.TradePayService;
import com.sunyard.itp.service.UnionpayService;
import com.sunyard.itp.service.imp.PrecreateServiceImp;
import com.sunyard.itp.utils.wxpay.HttpRequest;
import com.sunyard.itp.utils.wxpay.HttpUtil;
import com.sunyard.itp.utils.wxpay.PayCommonUtil;
import com.sunyard.itp.utils.wxpay.WXPay;
import com.sunyard.itp.utils.wxpay.WXPayConfigImpl;
import com.sunyard.itp.utils.wxpay.WXPayConstants.SignType;
import com.sunyard.itp.utils.wxpay.WXPayUtil;

@Controller
public class PayController {
	@Autowired
	private PrecreateService precreateService;
	@Autowired
	private TradePayService tradePayService;
	@Autowired
	RefundService refundService;
	@Autowired
	private UnionpayService unionpayService;
	
	private org.slf4j.Logger logger =  LoggerFactory.getLogger(PayController.class);
	/**
	 * 
	 * @Description: 跳转到错误页面
	 * @return   
	 * String  
	 * @throws
	 * @author 黄志鑫
	 * @date 2017年7月25日 上午11:07:38
	 */
	@RequestMapping("/toError")
	public String toError(){
		return "error";
	}
	
	/**
	 * 跳转到测试页面
	 * @return
	 */
	@RequestMapping("/toTest")
	public String toTest(HttpServletRequest request){
		logger.info(request.getServletPath());//   /toTest.action
		logger.info(request.getRemoteAddr());//     172.16.17.18
		logger.info(request.getContextPath());//  /hbnx_itp
		logger.info(request.getRequestURI());//   /hbnx_itp/toTest.action
		logger.info(request.getLocalName());//     windows10.microdone.cn
		logger.info(request.getPathInfo());//
		logger.info(request.getRemoteUser());//
		logger.info("测试request");
		return "test";
	}
	
	/**
	 * 跳转到被扫支付页面
	 * @return
	 */
	@RequestMapping("/toPay")
	public String toPay(){
		return "precreatePay";
	}
	
	/**
	 * 支付宝被扫下单
	 * 被扫预下单 生成二维码链接,重定向唤起支付sdk
	 * @throws Exception 
	 */
	@RequestMapping("/aliPrecreate")
	public String Precreate(HttpServletRequest request,HttpServletResponse response,PrecreateParams precreateParams,ModelMap modelMap) throws Exception{
		logger.info("页面传递过来的参数是："+precreateParams.toString());
		String url = precreateService.precreate(request,response,precreateParams);
		modelMap.addAttribute("qrcode",url);
		if(url.indexOf("alipay") != -1){
//			response.sendRedirect(url);
			return "alipay";	
		}
	else{
			return "error";
		}
	}
	
	/**
	 * 跳转到主扫支付界面
	 * @return
	 */
	@RequestMapping("/toTradePay")
	public String toTradePay(){
		
		return "tradePay";
	}
	/**
	 * 
	 * @Description: 主扫接口
	 * @param tradePayParams
	 * @param modelMap
	 * @return
	 * @throws Exception   
	 * String  
	 * @throws
	 * @author 黄志鑫
	 * @date 2017年7月27日 上午10:56:32
	 */
	@RequestMapping("/tradePay")
	@ResponseBody
	public String tradePay(TradePayParams tradePayParams,ModelMap modelMap) throws Exception{
		Message message;
		try {
			message = tradePayService.tradePay(tradePayParams);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return CommonConst.getResult(CommonConst.RET_FAIL, "系统错误");
		}
		return CommonConst.getResult(CommonConst.RET_SUCCESS, message);
//		modelMap.addAttribute("resultCode", res);
//		if(res.indexOf("成功") != -1){
//			return "payResult";
//		}else{
//			return "error";
//		}
//		if(res.equals("00")){
//			logger.info("支付宝支付成功");
//			return "payResult2";
//		}else if(res.equals("01")){
//			logger.info("正在输入密码验证支付");
//			return "payResult01";
//		}else if(res.equals("02")){
//			logger.info("状态未知");
//			return "payResult02";
//		}else if(res.equals("03")){
//			logger.info("调用支付宝失败");
//			return "payResult03";
//		}else if(res.equals("000")){
//			logger.info("微信支付成功");
//			return "payResult1";
//		}else if(res.equals("001")){
//			logger.info("微信支付，正在输入密码");
//			return "payResult001";
//		}else if(res.equals("002")){
//			logger.info("其他错误");
//			return "payResult002";
//		}else if(res.equals("003")){
//			logger.info("调用微信失败");
//			return "payResult003";
//		}else if(res.equals("00000")){
//			logger.info("调用银联成功");
//			return "payResult00000";
//		}else if(res.equals("00001")){
//			logger.info("其他应答码为失败请排查原因或做失败处理");
//			return "payResult00001";
//		}else if(res.equals("00002")){
//			logger.info("验证签名失败");
//			return "payResult00002";
//		}else if(res.equals("00003")){
//			logger.info("未获取到返回报文或返回http状态码非200");
//			return "payResult00003";
//		}
//		else{
//			logger.info("非法授权码");
//			return "payResult0000";
//		}
		
	}

	
	/**
	 * 微信被扫，采用微信公众号支付接口，现货区code在换取openID在预下单获取prepayid
	 * @Description: 获取页面的code
	 * @param request
	 * @param response
	 * @throws IOException   
	 * void  
	 * @throws
	 * @author 黄志鑫
	 * @date 2017年8月1日 下午9:31:01
	 */
	@RequestMapping("code")
	public void getCode(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//页面传递过来的参数
		String totalFee = request.getParameter("totalFee");
		String APPID = PayConst.WX_APP_ID;
	    String REDIRECT_URI = "http://pay.sydtech.com.cn/wxPay/wx/wxPrecreate.action?totalFee="+totalFee;
	    REDIRECT_URI = URLEncoder.encode(REDIRECT_URI, "UTF-8");
	    String getCodeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+APPID+"&redirect_uri="+REDIRECT_URI+"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
	    String code = request.getParameter("code");
	    logger.info(code);
	    if(code==null){
	       logger.info("code为空，跳转，url="+getCodeUrl);
	        //response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxcff758d1b3d6d2ce&redirect_uri=pay.sydtech.com.cn/wxPrecreate.action&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");
	       response.sendRedirect(getCodeUrl);
	 
	        logger.info("网页授权code为："+code);
	    }else{
	        logger.info("已获得code值");
	    
	        logger.info(code);
	    }
		
	}
	/**
	 * 
	 * @Description: 微信公众号支付接口 获取code后微信会自动带参数定向到该接口（被扫使用，需在商户平台设置支付授权目录）
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception   
	 * String  
	 * @throws
	 * @author 黄志鑫
	 * @date 2017年8月2日 上午12:06:11
	 */
	@RequestMapping("wxPay/wx/wxPrecreate")
	public String wxPrecreate(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception
	{		 
		logger.info("微信公众号支付预下单获取prepayid,将参数传到页面，唤起支付");
		String APPID = PayConst.WX_APP_ID;
	    String code = request.getParameter("code");
	    String totalFee = request.getParameter("totalFee");
	    Double totalFee1 = Double.parseDouble(totalFee) * 100;
	    
	    String s = String.valueOf(totalFee1);
	    String totalFee2 = s.substring(0,s.indexOf("."));
	    
//	    String totalFee2 = totalFee1.toString();
	    logger.info("--------code"+code);
	    //code换取token   openid
    	HttpRequest http = new HttpRequest();
        String secrest = PayConst.WX_SECRET;
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
        String result = HttpUtil.postData(url, "appid="+APPID+"&secret="+secrest+"&code="+code+"&grant_type=authorization_code");
        logger.info("access_token:"+result);
        JSONObject json = JSONObject.parseObject(result);
        String openid = json.getString("openid");
        logger.info("openid"+openid);
      //组装上传参数     统一下单
        String out_trade_no = "wxpay-pre" + System.currentTimeMillis()
	    + (long) (Math.random() * 10000000L);
		HashMap<String,String> data = new HashMap<String,String>();
		data.put("body", "wxgongzhonghaozhifu");
        data.put("out_trade_no", out_trade_no);
        data.put("fee_type", "CNY");
        logger.info("------------金额----------"+totalFee2);
        data.put("total_fee", totalFee2);
        data.put("spbill_create_ip",request.getLocalAddr());//发起交易客户端id
        data.put("notify_url", PayConst.WX_NOTIFY_URL);//异步回调通知
        data.put("trade_type", "JSAPI");
        data.put("openid", openid);
        //加载微信支付参数
		WXPayConfigImpl config =  WXPayConfigImpl.getInstance();
		WXPay wxpay = new WXPay(config);
        Map<String, String> r = wxpay.unifiedOrder(data);
        logger.info("微信预下单返回结果："+r.toString());
        if(r.get("result_code").equals("SUCCESS")){
        	 logger.info("微信预下单成功!");
        	 //组装传递到页面唤起支付参数
        	 String prepay_id =  r.get("prepay_id");
        	 logger.info("prepay_id:"+prepay_id);
        	 String timeStap = String.valueOf(System.currentTimeMillis() / 1000);//时间戳
     		 String nonceStr = WXPayUtil.generateUUID();  //随机字符串
     		 String appId = PayConst.WX_APP_ID;  //appid
     		 
     		 SortedMap<String, String> payMap = new TreeMap<String, String>();  
             payMap.put("appId", appId); 
             payMap.put("timeStamp", timeStap);
             payMap.put("nonceStr", nonceStr); 
             payMap.put("package", "prepay_id=" + prepay_id); 
             payMap.put("signType", "HMACSHA256"); 
             //获取签名     注意：微信新版sdk预下单默认签名是     HMACSHA256   这里也应该使用HMACSHA256  否则还在h5唤起支付时签名验证失败   
             //String paySign = PayCommonUtil.createSign("UTF-8", payMap, PayConst.WX_API_KEY);
             String paySign =  WXPayUtil.generateSignature(payMap,PayConst.WX_API_KEY, SignType.HMACSHA256 );
             logger.info(payMap.toString());
             //传递到页面
     		 modelMap.addAttribute("appId", appId);
     		 modelMap.addAttribute("timeStamp", timeStap);
     		 modelMap.addAttribute("nonceStr", nonceStr);
     		 modelMap.addAttribute("signType", "HMACSHA256");
     		 modelMap.addAttribute("packageName", "prepay_id=" + prepay_id);
     		 modelMap.addAttribute("paySign", paySign);
     		
     		 return "weixin";
        	        	 
             }else{
            	 logger.info("微信预下单失败");
            	 modelMap.addAttribute("qrcode", "微信预下单失败");
            	 return "error";
             }
	}
	@RequestMapping("payResult1")
	public String payResult(){
		return "payResult1";
	}
	@RequestMapping("toUnion")
	public String toUnion(){
		return "unionpay";
	}
	@RequestMapping("unionPrecreate")
	public String unionPrecreate(HttpServletRequest request,HttpServletResponse response,PrecreateParams precreateParams) throws IOException{
		System.out.println("++++++++++++++++++++++++");
		logger.info("--------------------页面传递过来的参数是："+precreateParams.toString());
        String totalFee = precreateParams.getTotalFee();
		unionpayService.precreate(request,response,totalFee);
		
		return "payResult1";
	}
	
}
