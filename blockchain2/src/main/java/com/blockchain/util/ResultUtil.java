package com.blockchain.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blockchain.exception.ServiceException;
import com.blockchain.exception.ErrorMessage;
import com.blockchain.exception.StatusCode;
import com.blockchain.exception.SubmitException;
import com.blockchain.exception.ThreadException;

public class ResultUtil {

	private static final Logger log = Logger.getLogger(ResultUtil.class);

	public static void checkResultIfSuccess(String pos, String resultString) throws ServiceException, ThreadException {
		if (StringUtils.isBlank(resultString)) {
			String s = new ErrorMessage(StatusCode.SYSTEM_UNKOWN_ERROR, pos, "调用SDK网络失败，请检查网络").toJsonString();
			throw new ServiceException(s);
		}
		JSONObject applyObject = JSONObject.parseObject(resultString);
		Integer retcode = applyObject.getInteger("retcode");
		
		
		if (retcode.equals(83590142)) {
			String s = new ErrorMessage(applyObject.getInteger("retcode"), "BAAS系统的" + pos, applyObject.getString("retmsg") + "").toJsonString();
			Integer errorCode = StatusCode.APPLY_THREAD_ERROR;
			throw new ThreadException(s,errorCode);
		} else if (retcode != 0 && retcode.equals(83590142)==false) {
			String s = new ErrorMessage(applyObject.getInteger("retcode"), "BAAS系统的" + pos, applyObject.getString("retmsg") + "").toJsonString();
			
			throw new ServiceException(s);
		} else {
			log.debug(pos + "成功！结果为" + applyObject.toJSONString());
		}
	}

	public static void checkSubmitResultIfSuccess(String pos, String submitParamString, String submitResultString) throws SubmitException, ServiceException, ThreadException {
		if (StringUtils.isBlank(submitResultString)) {
			String s = new ErrorMessage(StatusCode.SYSTEM_UNKOWN_ERROR, pos, "调用SDK网络失败，请检查网络").toJsonString();
			throw new ServiceException(s);
		}

		JSONObject submitResultObject = JSONObject.parseObject(submitResultString);

		Integer retcode = submitResultObject.getInteger("retcode");
		if (retcode.equals(83590142)) {
			String s = new ErrorMessage(submitResultObject.getInteger("retcode"), "BAAS系统的" + pos, submitResultObject.getString("retmsg") + "").toJsonString();
			JSONObject jsonObject = JSON.parseObject(submitParamString);
			
			Integer errorCode = StatusCode.SUBMIT_THREAD_ERROR;
			throw new ThreadException(s,errorCode,jsonObject);
		} else if (retcode != 0 && retcode.equals(83590142)==false) {
			String s = new ErrorMessage(submitResultObject.getInteger("retcode"), "BAAS系统的" + pos, submitResultObject.getString("retmsg") + "").toJsonString();
			JSONObject jsonObject = JSON.parseObject(submitParamString);

			throw new SubmitException(s, jsonObject);
		} else {
			log.debug(pos + "成功！结果为" + submitParamString);
		}
	}

}