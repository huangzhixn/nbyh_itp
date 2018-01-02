package com.sunyard.itp.service.imp;


import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sunyard.itp.service.PayQrCodeService;


@Service
public class PayQrCodeServiceImp implements PayQrCodeService{

	@Override
	public void createQr(HttpServletResponse resp,String totalFee) throws IOException {
		String qrCode = "http://172.16.17.18:8083/nbyh_itp/toPrecreatePayDyn.action?totalFee="+totalFee;
		Map<EncodeHintType, Object>  hints=new HashMap<EncodeHintType, Object>();
        // 指定纠错等级  
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);  
        // 指定编码格式  
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  
        hints.put(EncodeHintType.MARGIN, 1);
        try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(qrCode,BarcodeFormat.QR_CODE, 200, 200, hints);
			resp.reset();
			OutputStream out = resp.getOutputStream();
			MatrixToImageWriter.writeToStream(bitMatrix, "png", out);//输出二维码
            out.flush();
            out.close();
            
        } catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
