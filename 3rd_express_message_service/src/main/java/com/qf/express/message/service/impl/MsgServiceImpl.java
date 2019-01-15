package com.qf.express.message.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.qf.express.message.service.MsgService;
import com.qf.express.utils.StringUtils;

import io.goeasy.GoEasy;
import io.goeasy.publish.GoEasyError;
import io.goeasy.publish.PublishListener;

@Service
public class MsgServiceImpl implements MsgService{
	
	 //产品名称:云通信短信API产品,开发者无需替换
	/*@Value("${product}")  
    private String product;
    //产品域名,开发者无需替换
	@Value("${domain}") 
    private String domain;
    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
	@Value("${accessKeyId}") 
    private String accessKeyId;
	@Value("${accessKeySecret}") 
    private String accessKeySecret ;
	@Value("${profile}") 
    private String profileym ;
	@Value("${signname}") 
    private String signname ;*/
	
	 //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "LTAIGWCykcsqwOhI";
    static final String accessKeySecret = "RbSowCvfKxhfNPOWZuKt5wvCzAhwKx";




	@Override
	public String sendCheckSms(String phone, String template) throws Exception {
		//可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("cs快递");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(template);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        String code = StringUtils.getCheckCode();
        request.setTemplateParam("{\"code\":\""+code+"\"}");
        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return code;
	}




	@Override
	public void sendQjCms(String phone, String name, String store) throws Exception {
		//可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("cs快递");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_150172949");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //String code = StringUtils.getCheckCode();
        request.setTemplateParam("{\"name\":\""+name+"\", \"store\":\""+store+"\"}");
        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
	}
/*	public static void main(String[] args) throws Exception {
		MsgServiceImpl s = new MsgServiceImpl();
		s.sendQjCms("18579564698", "lyx", "长沙");
	}*/




	@Override
	public void sendTs(String channel, String message) {
		GoEasy goEasy = new 
				GoEasy("rest-hangzhou.goeasy.io",
						"BC-9377df7468b44f27b37dd0723f0c7b70");
		goEasy.publish(channel, message, new PublishListener() {																													
			@Override																												
			public void onSuccess() {																												
				System.out.print("消息发布成功 ");																											
			}																												
																															
			@Override																												
			public void onFailed(GoEasyError error) {																												
				System.out.print("消息发布失败 ,  错误编码：" + error.getCode());																											
			}																												
		});	
	}

}
