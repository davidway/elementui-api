package com.blockchain.service;

import com.blockchain.DTO.ConfigPropertiesFormDTO;
import com.blockchain.exception.ServiceException;
import com.tencent.trustsql.sdk.exception.TrustSDKException;

public interface ConfigPropertiesService {

	void add(ConfigPropertiesFormDTO configPropertiesFormDTO) throws TrustSDKException, ServiceException;

	ConfigPropertiesFormDTO get();

}