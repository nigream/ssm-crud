package com.nigream.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nigream
 * @date 2021年3月26日 下午11:48:08
 * 
 */
public class Message {
	// 状态码100-成功 500-失败
	private int code;
	private String info;
	
	private Map<String,Object> dataMap = new HashMap<String,Object>();
	
	public static Message success() {
		Message message = new Message();
		message.setCode(100);
		message.setInfo("处理成功！");
		return message;
	}
	
	public static Message fail() {
		Message message = new Message();
		message.setCode(500);
		message.setInfo("处理失败！");
		return message;
	}
	 
	public Message add(String key,Object value) {
		this.getDataMap().put(key, value);
		return this;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}
	
	
	
	
	
}
