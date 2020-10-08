package com.boxiaoyun.msg.service;

import com.boxiaoyun.msg.client.model.SmsMessage;

/**
 * @author woodev
 */
public interface SmsSender {

	/**
	 * 发送短信
	 * @param parameter
	 * @return
	 */
	Boolean send(SmsMessage parameter);
}
