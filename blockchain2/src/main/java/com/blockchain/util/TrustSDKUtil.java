package com.blockchain.util;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blockchain.exception.ServiceException;
import com.blockchain.exception.ErrorMessage;
import com.blockchain.exception.StatusCode;
import com.tencent.trustsql.sdk.TrustSDK;
import com.tencent.trustsql.sdk.exception.TrustSDKException;

public class TrustSDKUtil {

	public static void checkPrivateKeyAccountIsMatch(String userPrivateKey,
			String ownerAccount) throws ServiceException, TrustSDKException {
			userPrivateKey = userPrivateKey.trim();
			ownerAccount = ownerAccount.trim();
		String generateAccountAddress = TrustSDK
				.generateAddrByPrvkey(userPrivateKey);
		if (ownerAccount.equals(generateAccountAddress) == false) {
			ErrorMessage exception = new ErrorMessage();
			exception.setRetCd(StatusCode.PARAM_ERROR);
			exception.setRetPos("兑换接口");
			exception.setMsgDes("私钥校验地址失败");

			throw new ServiceException(exception.toJsonString());
		}
	}

	public static void checkPariKeyMatch(String createUserPublicKey,
			String createUserPrivateKey) throws TrustSDKException,
			ServiceException {

		if (StringUtils.isNotBlank(createUserPublicKey)
				&& StringUtils.isNotBlank(createUserPrivateKey)) {
			createUserPublicKey = createUserPublicKey.trim();
			createUserPrivateKey = createUserPrivateKey.trim();
			if (TrustSDK
					.checkPairKey(createUserPrivateKey, createUserPublicKey) == false) {
				ErrorMessage exception = new ErrorMessage();
				exception.setRetCd(StatusCode.PARAM_ERROR);
				exception.setRetPos("配置接口");
				exception.setMsgDes("公私钥配对失败");

				throw new ServiceException(exception.toJsonString());
			}
		}

	}

}
