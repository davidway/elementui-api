package com.blockchain.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.blockchain.DTO.AssetDTO;
import com.blockchain.DTO.TransInfoDTO;
import com.blockchain.DTO.UserInfoDTO;
import com.blockchain.DTO.UserKeyDTO;
import com.blockchain.VO.AccountQueryFormVO;
import com.blockchain.VO.AssetTransQueryFormVO;
import com.blockchain.VO.KeyInfoVO;
import com.blockchain.VO.UserFormVO;
import com.blockchain.exception.ServiceException;
import com.tencent.trustsql.sdk.exception.TrustSDKException;

/**
 * 操作用户消息的service
 * 
 * @author lupf
 * 
 */
public interface UserService {

	public UserKeyDTO generatePairKey(UserKeyDTO userKeyModel) throws TrustSDKException, UnsupportedEncodingException;

	public UserInfoDTO addUserHasBaseAndHostAccount(UserFormVO userFormVO) throws UnsupportedEncodingException, TrustSDKException, Exception;

	List<AssetDTO> accountQuery(AccountQueryFormVO assetForm) throws TrustSDKException, Exception;

	List<TransInfoDTO> transQuery(AssetTransQueryFormVO assetForm) throws ServiceException, TrustSDKException, Exception;

	String getSrcAssetListBySrcAccount(String srcAccount, String content) throws TrustSDKException, Exception;

	public UserInfoDTO addUserHasBaseAccount(UserFormVO userFormVO) throws ServiceException, TrustSDKException, UnsupportedEncodingException, Exception;

	public UserInfoDTO addUserHostAccount(UserFormVO userFormVO)  throws ServiceException, TrustSDKException, UnsupportedEncodingException, Exception;

	public void checkPairKey(KeyInfoVO keyInfo) throws TrustSDKException, ServiceException;

}