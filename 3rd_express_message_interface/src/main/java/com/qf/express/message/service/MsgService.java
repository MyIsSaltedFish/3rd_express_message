package com.qf.express.message.service;

import javax.jws.WebService;


@WebService
public interface MsgService {
	
	// 发短信验证码
	public String sendCheckSms(String phone, String template) throws Exception;
	// 发普通短信
	public void sendQjCms(String phone,String name,String store) 
			throws Exception;
	// 发推送（参数1：频道 2：消息）
	public void sendTs(String channel,String message);
	

}
