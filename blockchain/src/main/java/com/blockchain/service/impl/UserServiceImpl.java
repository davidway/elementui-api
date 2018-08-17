package com.blockchain.service.impl;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blockchain.DTO.AssetDTO;
import com.blockchain.DTO.TransInfoDTO;
import com.blockchain.DTO.UserInfoDTO;
import com.blockchain.DTO.UserKeyDTO;
import com.blockchain.VO.AccountQueryFormVO;
import com.blockchain.VO.AssetTransQueryFormVO;
import com.blockchain.VO.KeyInfoVO;
import com.blockchain.VO.UserFormVO;
import com.blockchain.exception.ServiceException;
import com.blockchain.exception.StatusCode;
import com.blockchain.service.UserService;
import com.blockchain.util.ResultUtil;
import com.blockchain.util.UserUtil;
import com.tencent.trustsql.sdk.TrustSDK;
import com.tencent.trustsql.sdk.bean.PairKey;
import com.tencent.trustsql.sdk.exception.TrustSDKException;
import com.tencent.trustsql.sdk.util.HttpClientUtil;
import com.tencent.trustsql.sdk.util.SimpleHttpClient;


@Service
public class UserServiceImpl implements UserService {
	private static Logger logger = Logger.getLogger(UserServiceImpl.class);



	@Override
	public UserKeyDTO generatePairKey(UserKeyDTO userKeyModel) throws TrustSDKException, UnsupportedEncodingException {

		String privateKey = "";
		String publicKey = "";
		String afterTrustSql = "";
		PairKey pairKey = null;

		pairKey = TrustSDK.generatePairKey(true);
		privateKey = pairKey.getPrivateKey();
		publicKey = pairKey.getPublicKey();
		afterTrustSql = TrustSDK.signString(privateKey, "Tencent TrustSQL".getBytes("UTF-8"), false);

		userKeyModel.setPrivateKey(privateKey);
		userKeyModel.setPublicKey(publicKey);
		userKeyModel.setAfterTrustKey(afterTrustSql);

		return userKeyModel;
	}

	@Override
	public UserInfoDTO addUserHasBaseAndHostAccount(UserFormVO userFormVO) throws ServiceException,UnsupportedEncodingException, TrustSDKException, Exception {
		//申请原始帐号
		UserInfoDTO userInfoDTO = generateUserInfo(userFormVO);
		String userRegistRequestString = UserUtil.generateUserRequest(userInfoDTO,userFormVO);
		SimpleHttpClient httpClient = new SimpleHttpClient();
		String userRegistResult = httpClient.post("https://baas.trustsql.qq.com/idm_v1.1/api/user_cert/register", userRegistRequestString);
		ResultUtil.checkResultIfSuccess("申请用户", userRegistResult);
		JSONObject userRegistRetData = JSON.parseObject(userRegistResult).getJSONObject("retdata");
	
		//注册原始帐号的账户
		String userBaseAccoutForm = UserUtil.generateuserAccoutForm(userInfoDTO,userRegistRetData, false);
		String userBaseAccountRegistResult = httpClient.post("https://baas.trustsql.qq.com/idm_v1.1/api/account_cert/register", userBaseAccoutForm);
		logger.debug("注册原始帐号的账户返回结果:"+userBaseAccountRegistResult);
		ResultUtil.checkResultIfSuccess("申请账户",userBaseAccountRegistResult);
		JSONObject userAccoutData = JSON.parseObject(userRegistResult).getJSONObject("retdata");
		
		String userBaseAccount = userAccoutData.getString("user_address");
		if ( StringUtils.isBlank(userBaseAccount)){
			userBaseAccount = userAccoutData.getString("account_address");
		}
		userInfoDTO.setBaseAccountAddress(userBaseAccount);
		
		//再设置一个新的账号作为代理账号
		 String userHostAccoutForm = UserUtil.generateuserAccoutForm(userInfoDTO,userRegistRetData, true);
		String userHostAccountFormResult = httpClient.post("https://baas.trustsql.qq.com/idm_v1.1/api/account_cert/register", userHostAccoutForm);
		ResultUtil.checkResultIfSuccess("申请代理账户",userHostAccountFormResult);
		logger.debug("注册申请代理账户返回结果:"+userHostAccountFormResult);
		JSONObject userHostAccoutData = JSON.parseObject(userHostAccountFormResult).getJSONObject("retdata");
		String userHostAccount = userHostAccoutData.getString("user_address");
	
		if ( StringUtils.isBlank(userHostAccount)){
			userHostAccount = userHostAccoutData.getString("account_address");
		}
		userInfoDTO.setHostWalletAccountAddress(userHostAccount);

		return userInfoDTO;
	}

	private UserInfoDTO generateUserInfo(UserFormVO userFormVO) {
		UserInfoDTO userInfoDTO = new UserInfoDTO();
		userInfoDTO.setId(userFormVO.getId());
		userInfoDTO.setName(userFormVO.getName());
		return userInfoDTO;
	}
	@Override
	public List<AssetDTO> accountQuery(AccountQueryFormVO assetFormVO) throws TrustSDKException, Exception {

		String accountQueryString = UserUtil.generateAccountQueryParam(assetFormVO);
		logger.debug("调用【资产查询前】" + accountQueryString);
		String url = "https://baas.trustsql.qq.com/cgi-bin/v1.0/dam_asset_account_query_v1.cgi";
		String accountQueryResult = HttpClientUtil.post(url, accountQueryString);
		logger.debug("调用【资产查询后】" + accountQueryResult);
		ResultUtil.checkResultIfSuccess("资产查询接口", accountQueryResult);

		JSONObject userRegistRetData = JSON.parseObject(accountQueryResult);
		JSONArray jsonArray = JSON.parseArray(userRegistRetData.getString("assets_list"));
		List<AssetDTO> assetList = new LinkedList<AssetDTO>();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject o = jsonArray.getJSONObject(i);
			AssetDTO assetDTO = new AssetDTO();
			if ( assetFormVO.getContent()!=null){
				JSONObject content = o.getJSONObject("content");
				 if (content .equals(assetFormVO.getContent())){
						assetDTO.setAmount(o.getLong("amount"));
						assetDTO.setAssetAccount(o.getString("asset_account"));
						assetDTO.setAssetId(o.getString("asset_id"));
						assetDTO.setAssetType(o.getInteger("asset_type"));
						assetDTO.setState(o.getInteger("state"));
						assetDTO.setContent(o.getJSONObject("content"));
						
						assetList.add(assetDTO);
				 }
			}else{
				assetDTO.setAmount(o.getLong("amount"));
				assetDTO.setAssetAccount(o.getString("asset_account"));
				assetDTO.setAssetId(o.getString("asset_id"));
				assetDTO.setAssetType(o.getInteger("asset_type"));
				assetDTO.setState(o.getInteger("state"));
				assetDTO.setContent(o.getJSONObject("content"));
				assetDTO.setState(o.getInteger("state"));
				assetList.add(assetDTO);
			}
			
			
		
		}
		// 从大金额到小金额排序
		Collections.sort(assetList, new Comparator<AssetDTO>() {
			public int compare(AssetDTO one, AssetDTO another) {

				return (int) -(one.getAmount() - another.getAmount());
			}
		});
		return assetList;
	}

	@Override
	public List<TransInfoDTO> transQuery(AssetTransQueryFormVO assetForm) throws ServiceException, TrustSDKException, Exception {

		String accountQueryString = UserUtil.generateTransQueryParam(assetForm);
		logger.debug("调用【交易查询前】" + accountQueryString);
		String url = "https://baas.trustsql.qq.com/cgi-bin/v1.0/dam_asset_trans_query_v1.cgi";
		String accountQueryResult = HttpClientUtil.post(url, accountQueryString);
		ResultUtil.checkResultIfSuccess("交易查询接口", accountQueryResult);
		logger.debug("调用【交易查询后】" + accountQueryResult);
		JSONObject userRegistRetData = JSON.parseObject(accountQueryResult);

		JSONArray jsonArray = JSON.parseArray(userRegistRetData.getString("trans_list"));
		List<TransInfoDTO> transInfoList = new LinkedList<TransInfoDTO>();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject o = jsonArray.getJSONObject(i);
			TransInfoDTO transInfoDTO = new TransInfoDTO();
			transInfoDTO.setAmount(o.getLong("amount"));
			transInfoDTO.setDstAccount(o.getString("dst_account"));
			transInfoDTO.setDstAssetId(o.getString("dst_asset_id"));
			transInfoDTO.setSrcAccount(o.getString("src_account"));
			transInfoDTO.setSrcAssetId(o.getString("src_asset_id"));
			transInfoDTO.setTransactionId(o.getString("transaction_id"));
			transInfoDTO.setTransState(o.getInteger("trans_state"));
			transInfoDTO.setTransTime(o.getString("trans_time"));
			transInfoDTO.setTransType(o.getInteger("trans_type"));
			transInfoDTO.setSignList(o.getString("sign_str_list"));
			transInfoList.add(transInfoDTO);
		}
		Collections.sort(transInfoList, new Comparator<TransInfoDTO>() {
			public int compare(TransInfoDTO one, TransInfoDTO another) {

				return (int) -(Timestamp.valueOf(one.getTransTime()).getTime() - Timestamp.valueOf(another.getTransTime()).getTime());
			}
		});
		return transInfoList;
	}
	
	@Override
	public String getSrcAssetListBySrcAccount(String srcAccount, String content) throws TrustSDKException, Exception {
		boolean first = true;
		AccountQueryFormVO accountQuery = new AccountQueryFormVO();
		accountQuery.setAssetAccount(srcAccount);
		accountQuery.setPageLimit(20);
		accountQuery.setPageNo(1);

		accountQuery.setState(0);

		JSONObject contentJson = JSON.parseObject(content);
		logger.debug("调用【accountQuery前】的参数信息" + JSON.toJSONString(accountQuery));
		List<AssetDTO> list = accountQuery(accountQuery);
		StringBuffer assetIdList = new StringBuffer("");
		logger.debug("调用【accountQuery】成功后返回的" + JSON.toJSONString(list));
		for (AssetDTO assetDTO : list) {
			if (assetDTO.getAssetType().equals(1) && assetDTO.getContent().equals(contentJson)) {
				if (first) {
					assetIdList.append(assetDTO.getAssetId());
					first = false;
				} else {
					assetIdList.append("," + assetDTO.getAssetId());

				}
			}

		}
		if (StringUtils.isBlank(assetIdList)) {
			//String s = new ErrorMessage(StatusCode.SERVICE_EXCEPTION, "资产查询", "该用户没有资产，可能都在待申请").toJsonString();
			throw new ServiceException().errorCode(StatusCode.SERVICE_EXCEPTION).errorMessage("该用户没有资产，可能都在待申请");
		}
		return assetIdList.toString();
	}

	@Override
	public UserInfoDTO addUserHasBaseAccount(UserFormVO userFormVO) throws ServiceException,UnsupportedEncodingException, TrustSDKException, Exception {

		//申请原始帐号
		UserInfoDTO userInfoDTO = generateUserInfo(userFormVO);
		String userRegistRequestString = UserUtil.generateUserRequest(userInfoDTO,userFormVO);
		SimpleHttpClient httpClient = new SimpleHttpClient();
		String userRegistResult = httpClient.post("https://baas.trustsql.qq.com/idm_v1.1/api/user_cert/register", userRegistRequestString);
		ResultUtil.checkResultIfSuccess("申请用户", userRegistResult);
		JSONObject userRegistRetData = JSON.parseObject(userRegistResult).getJSONObject("retdata");
	
		//注册原始帐号的账户
		String userBaseAccoutForm = UserUtil.generateuserAccoutForm(userInfoDTO,userRegistRetData, false);
		String userBaseAccountRegistResult = httpClient.post("https://baas.trustsql.qq.com/idm_v1.1/api/account_cert/register", userBaseAccoutForm);
		logger.debug("注册原始帐号的账户返回结果:"+userBaseAccountRegistResult);
		ResultUtil.checkResultIfSuccess("申请账户",userBaseAccountRegistResult);
		JSONObject userAccoutData = JSON.parseObject(userRegistResult).getJSONObject("retdata");
		
		String userBaseAccount = userAccoutData.getString("user_address");
		if ( StringUtils.isBlank(userBaseAccount)){
			userBaseAccount = userAccoutData.getString("account_address");
		}
		userInfoDTO.setBaseAccountAddress(userBaseAccount);
		
		return userInfoDTO;
	}

	@Override
	public UserInfoDTO addUserHostAccount(UserFormVO userFormVO) throws ServiceException, TrustSDKException, UnsupportedEncodingException, Exception {
		//再设置一个新的账号作为代理账号
		UserInfoDTO userInfoDTO = new UserInfoDTO();
		userInfoDTO.setId(userFormVO.getId());
		userInfoDTO.setName(userFormVO.getName());
		
		String userHostAccoutForm = UserUtil.generateuserAccoutFormOnlyHostAccount(userInfoDTO, userFormVO);
		SimpleHttpClient httpClient = new SimpleHttpClient();
		String userHostAccountFormResult = httpClient.post("https://baas.trustsql.qq.com/idm_v1.1/api/account_cert/register", userHostAccoutForm);
		ResultUtil.checkResultIfSuccess("申请代理账户",userHostAccountFormResult);
		logger.debug("注册申请代理账户返回结果:"+userHostAccountFormResult);
		JSONObject userHostAccoutData = JSON.parseObject(userHostAccountFormResult).getJSONObject("retdata");
		String userHostAccount = userHostAccoutData.getString("user_address");
	
		if ( StringUtils.isBlank(userHostAccount)){
			userHostAccount = userHostAccoutData.getString("account_address");
		}
		userInfoDTO.setHostWalletAccountAddress(userHostAccount);

		return userInfoDTO;
	}

	@Override
	public void checkPairKey(KeyInfoVO keyInfo) throws TrustSDKException, ServiceException {
		String privateKey = keyInfo.getPrivateKey().trim();
		String publicKey = keyInfo.getPublicKey().trim();
		boolean isPair = TrustSDK.checkPairKey(privateKey, publicKey);
		if ( isPair==false){
			throw new ServiceException().errorCode(StatusCode.PARAM_ERROR).errorMessage("公私钥匹配错误");
		}
	}
}