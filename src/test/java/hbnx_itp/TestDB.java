/**
 * 
 */
package hbnx_itp;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author:huam.zhou
 * @date:2017年7月17日 下午4:39:32 
 */
public class TestDB {
//	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void testDb(){
		System.out.println("dd");
//		logger.info("dd");
	/*	AlipayClient alipayClient = new DefaultAlipayClient(PayConst.OPEN_API_DOMAIN,
				PayConst.APP_ID,
				PayConst.PRIVATE_KEY,
				"json","UTF-8",
				PayConst.ALIPAY_PUBLIC_KEY,
				PayConst.SIGN_TYPE);
		AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
		request.setBizContent("{" +
		"\"out_trade_no\":\"20150320010101005\"," +
		"\"total_amount\":88.88," +
		"\"subject\":\"Iphone6 16G\"" +
		
		"}" 
	
		);
		AlipayTradePrecreateResponse response = null;
		try {
			response = alipayClient.execute(request);
		} catch (AlipayApiException e) {
			System.out.println("支付异常");
			e.printStackTrace();
		}
		System.err.println(response.getQrCode());
		System.err.println(response);
		if(response.isSuccess()){
		System.out.println("调用成功");
		} else {
		System.out.println("调用失败");
		}
		*/
		
	}

}
