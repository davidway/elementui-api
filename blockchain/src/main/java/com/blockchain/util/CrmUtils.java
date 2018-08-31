package com.blockchain.util;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.blockchain.DTO.CrmServiceDTO;
import com.blockchain.exception.ServiceException;
import com.blockchain.exception.StatusCode;

public class CrmUtils {
	static Logger logger = Logger.getLogger(CrmUtils.class);


	public static  void checkAuth() throws ServiceException {
		CrmServiceDTO crmServiceDto = CrmServiceDTO.getSingleton();
		crmServiceDto.check();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
		        .getRequest();

		String ip = MyHttpUtils.getIp(request);
		String serverId = crmServiceDto.getServerId();
		String serverCode = crmServiceDto.getServerCode();
		String configPath = crmServiceDto.getCrmBaseUrls();
		String[] ipsPath = configPath == null ? null : configPath.split(";");

		boolean result = false;
		if (ipsPath != null && ipsPath[0].intern() != "") {
			result = checkData(ip, serverId, serverCode, ipsPath);
			if (result == false) {
				throw new ServiceException().errorCode(StatusCode.AUTHORITY_ERROR).errorMessage(StatusCode.AUTHORITY_ERROR_MESSAGE);
			}
		} else {
			throw new ServiceException().errorCode(StatusCode.PARAM_ERROR).errorMessage(StatusCode.PARAM_ERROR_MESSAGE);
		}

	}

	private static boolean checkData(String ip, String serverId, String serverCode, String[] ipsPath) {
		for (int i = 0; i < ipsPath.length; i++) {
			String ipPath = ipsPath[i];
			String path = ipPath + "/code?type=1&codeid=" + serverId + "&codestr=" + serverCode;

			if (ip != null && !ip.isEmpty()) {
				path = ipPath + "/code?type=2&codeid=" + serverId + "&codestr=" + serverCode + "&ip=" + ip;
			}
			try {
				String jsonData = getJsonString(path);
				JSONObject jobj = JSONObject.parseObject(jsonData);
				int result = jobj.getInteger("result");
				int data = jobj.getInteger("data");

				if (result == 1 && data == 1) {
					return true;
				}
			} catch (SocketTimeoutException e) {
				logger.error(e);
				continue;
			} catch (Exception e) {
				logger.error(e);
				continue;
			}
		}
		return false;
	}

	public static String getJsonString(String urlPath) throws Exception {
		URL url = new URL(urlPath);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		// 设置超时时间为3毫秒，避免程序僵死而不能继续往下执行
		connection.setConnectTimeout(3000); // 连接主机的超时时间
		connection.setReadTimeout(3000); // 从主机读取数据的超时时间
		connection.connect();
		InputStream inputStream;
		inputStream = connection.getInputStream();
		Reader reader = new InputStreamReader(inputStream, "UTF-8");
		BufferedReader bufferedReader = new BufferedReader(reader);
		String str = null;
		StringBuffer sb = new StringBuffer();
		while ((str = bufferedReader.readLine()) != null) {
			sb.append(str);
		}
		reader.close();
		connection.disconnect();
		return sb.toString();

	}

}