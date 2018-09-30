package com.blockchain.service.tencent.util;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blockchain.service.tencent.dto.AssetIssueDto;
import com.blockchain.service.tencent.dto.AssetSettleDto;
import com.blockchain.service.tencent.dto.AssetSettleFormDto;
import com.blockchain.service.tencent.dto.AssetSettleSubmitFormDto;
import com.blockchain.service.tencent.dto.AssetIssueSubmitFormDto;
import com.blockchain.service.tencent.dto.AssetTransferDto;
import com.blockchain.service.tencent.dto.AssetTransferFormDto;
import com.blockchain.service.tencent.dto.AssetTransferSubmitFormDto;

@Component
public class AssetPrepareUtil {

	public AssetIssueSubmitFormDto prepareAssetSubmitForm(String applyResultString) {
		JSONObject o = JSON.parseObject(applyResultString);
		AssetIssueSubmitFormDto assetIssueSubmitFormDto = new AssetIssueSubmitFormDto();
		String signStrList = o.getString("sign_str_list");
		assetIssueSubmitFormDto.setSignStr(signStrList);
		String transactionId = o.getString("transaction_id");
		assetIssueSubmitFormDto.setTransactionId(transactionId);
		String assetId = o.getString("asset_id");
		assetIssueSubmitFormDto.setAssetId(assetId);
		return assetIssueSubmitFormDto;
		
	}

	public AssetTransferSubmitFormDto perpareTransferSubmitForm(AssetTransferFormDto assetTransferFormDto, String applyResultString) {
		AssetTransferSubmitFormDto s = new AssetTransferSubmitFormDto();
		s.setUserPrivateKey(assetTransferFormDto.getUserPrivateKey());
		
		JSONObject o = JSON.parseObject(applyResultString);
		String transtionId = o.getString("transaction_id");
		String signList = o.getString("sign_str_list");
		String leftAssetId = o.getString("left_asset_id");
		String dstAsssetId = o.getString("dst_asset_id");
		
		s.setTransactionId(transtionId);
		s.setSrcAssetId(leftAssetId);
		s.setUserPrivateKey(assetTransferFormDto.getUserPrivateKey());
		s.setDstAssetId(dstAsssetId);
		s.setSignList(signList);
		return s;
	}

	public AssetSettleSubmitFormDto perpareTransferSubmitForm(AssetSettleFormDto assetSettleFormDto, String applyResultString) {
		AssetSettleSubmitFormDto s = new AssetSettleSubmitFormDto();
		s.setUserPrivateKey(assetSettleFormDto.getUserPrivateKey());
		
		JSONObject o = JSON.parseObject(applyResultString);
		String transtionId = o.getString("transaction_id");
		String signList = o.getString("sign_str_list");
		
		String userPrivateKey = assetSettleFormDto.getUserPrivateKey();
		s.setUserPrivateKey(userPrivateKey);
		s.setTransactionId(transtionId);
		s.setSignList(signList);
		return s;
	}

	public AssetTransferDto generateAssetTransferDTO(AssetTransferSubmitFormDto asseTransfertSubmitForm, String submitResultString) {
		AssetTransferDto assetTransferDto = new AssetTransferDto();
		
		JSONObject json = JSONObject.parseObject(submitResultString);
		String dstAssetId = json.getString("dst_asset_id");
		String dstAssetAmount = json.getString("dst_asset_amount	");
		String srcAssetId = json.getString("left_asset_id");
		String feeAssetId = json.getString("fee_asset_id");
		String leftAssetAmount = json.getString("left_asset_amount");
		String transHash = json.getString("trans_hash");
		String feeAssetAmount = json.getString("fee_asset_amount");

		String transactionId = asseTransfertSubmitForm.getTransactionId();
		assetTransferDto.setTransHash(transHash);
		assetTransferDto.setFeeAssetAmount(feeAssetAmount);
		assetTransferDto.setSrcAmount(leftAssetAmount);
		assetTransferDto.setDstAssetAmount(dstAssetAmount);
		assetTransferDto.setDstAssetId(dstAssetId);
		assetTransferDto.setFeeAssetId(feeAssetId);
		assetTransferDto.setSrcAssetId(srcAssetId);
		assetTransferDto.setTransactionId(transactionId);
		return assetTransferDto;
	}

	public AssetSettleDto generateAssetSettleDTO(AssetSettleSubmitFormDto assetSettleSubmitFormDto, String submitResultString) {
		AssetSettleDto assetSettleDto = new AssetSettleDto();
		
		String transactionId = assetSettleSubmitFormDto.getTransactionId();
		JSONObject json = JSONObject.parseObject(submitResultString);
		String leftAssetId = json.getString("left_asset_id");
		String transHash = json.getString("trans_hash");
		assetSettleDto.setTransHash(transHash);
		assetSettleDto.setSrcAssetId(leftAssetId);
		assetSettleDto.setTransactionId(transactionId);
		 return assetSettleDto;
	}

	public AssetIssueDto generateAssetIssueDto(AssetIssueSubmitFormDto assetIssueSubmitFormDto, String submitResultString) {
		AssetIssueDto assetIssueDto = new AssetIssueDto();
		
		String transactionId = assetIssueSubmitFormDto.getTransactionId();
		String assetId = JSON.parseObject(submitResultString).getString("asset_id");
		String transHash = JSON.parseObject(submitResultString).getString("trans_hash");
		
		
		assetIssueDto.setTransactionId(transactionId);
		assetIssueDto.setAssetId(assetId);
		assetIssueDto.setTransHash(transHash);
		
		return assetIssueDto;
	}

}