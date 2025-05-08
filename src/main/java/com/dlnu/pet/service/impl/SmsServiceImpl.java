package com.dlnu.pet.service.impl;

import com.dlnu.pet.service.SmsService;
import com.dlnu.pet.util.HttpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService {

    @Value("${sms.host}")
    private String host;
    
    @Value("${sms.path}")
    private String path;

    @Value("${sms.appcode}")
    private String appcode;
    
    @Override
    public void sendCode(String phone, String code, String type) throws Exception {
        //try {            
             String method = "POST";
            String appcode = this.appcode;
	        Map<String, String> headers = new HashMap<String, String>();
	   //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	        headers.put("Authorization", "APPCODE " + appcode);
	        Map<String, String> querys = new HashMap<String, String>();
	        querys.put("mobile", phone);
	        querys.put("param", "**code**:" + code + ",**minute**:5");
	        querys.put("smsSignId", "2e65b1bb3d054466b82f0c9d125465e2");
	        querys.put("templateId", "908e94ccf08b4476ba6c876d13f084ad");
	        Map<String, String> bodys = new HashMap<String, String>();

	    try {
	    	HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            String result = EntityUtils.toString(response.getEntity());
            log.info("Send SMS response: {}", result);
	    } catch (Exception e) {
            log.error("发送短信验证码失败", e);
            throw new Exception("发送短信验证码失败");
	    }
    }
} 