package com.template.service;


import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.template.common.GlobalValues;


/**
 * <pre>
* kr.co.milvus.milvussecurity.service 
*    |_ SlackService.java
 * </pre>
 * 
 * @date : 2016. 10. 12. 오후 5:51:35
 * @version :
 * @author : husson
 */
@Service
public class SlackService {

	private static final Logger logger = LoggerFactory.getLogger(SlackService.class);

	@Async
	public void sendToSlackInOut(String who, String when, String what) {
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			Gson gson = new Gson();
			Map<String, String> map = new HashMap<>();
			map.put("icon_emoji", ":bird:");
			map.put("channel", GlobalValues.SLACK_INOUT_REPORT_CHANNEL);
			map.put("username", "MilvusSecurity");
			map.put("text", who + "님 " + what + " " + when);

			StringEntity entity = new StringEntity("payload=" + gson.toJson(map), "UTF-8");

			HttpPost httpPost = new HttpPost(GlobalValues.SLACK_HOOK_URL);
			httpPost.addHeader("content-type", "application/x-www-form-urlencoded");
			httpPost.setEntity(entity);
			httpClient.execute(httpPost);
		} catch (Exception e) {
			logger.error("sendToSlackInOut", e);
		}
	}
    

    @Async
    public void sendToSlackError(Map<String, Object> body) {
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            Gson gson = new Gson();
            Map<String, String> map = new HashMap<>();
            map.put("icon_emoji", ":bird:");
            map.put("channel", GlobalValues.SLACK_ERROR_REPORT_CHANNEL);
            map.put("username", "MilvusSecurity");
            map.put("text", "Error Status:{" + body.get("status").toString() + "} Error:{" + body.get("error").toString() + "} Path:{" + body.get("path").toString() + "}");

            StringEntity entity = new StringEntity("payload=" + gson.toJson(map), "UTF-8");

            HttpPost httpPost = new HttpPost(GlobalValues.SLACK_HOOK_URL);
            httpPost.addHeader("content-type", "application/x-www-form-urlencoded");
            httpPost.setEntity(entity);
            httpClient.execute(httpPost);
        } catch (Exception e) {
            logger.error("sendToSlackError", e);
        }
    }
}
