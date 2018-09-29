package com.blockchain.service.ethereum.vo;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(description = "转账使用实体类", value = "转账返回值json")
public class EthAssetTransferVo {
	@ApiModelProperty(value = "交易流水号", dataType = "String")
	String transactionId;
	@ApiModelProperty(value = "申请转账账户后的id列表", dataType = "String")
	String srcAssetId;
	@ApiModelProperty(value = "目标账户申请转账后的id列表", dataType = "String")
	String dstAssetId;
	@ApiModelProperty(value = "手续费资产ID", dataType = "String")
	private String feeAssetId;
	@ApiModelProperty(value = "手续费资产ID", dataType = "String")
	private String dstAssetAmount;
	@ApiModelProperty(value = "区块链交易Fhash值", dataType = "String")
	private String transHash;
	@ApiModelProperty(value = "如果资产有剩余，则返回找零后的资产份额", dataType = "String")
	private String srcAmount;
	@ApiModelProperty(value = "手续费资产份额", dataType = "String")
	private String feeAssetAmount;
	
	private String smartContractAddress;
	private String gasUsed;
	
	private String tokenBalance;
	private String nonce;

	private String ethBalance;

	
	public String getSmartContractAddress() {
		return smartContractAddress;
	}

	public void setSmartContractAddress(String smartContractAddress) {
		this.smartContractAddress = smartContractAddress;
	}

	public String getGasUsed() {
		return gasUsed;
	}

	public void setGasUsed(String gasUsed) {
		this.gasUsed = gasUsed;
	}

	public String getTokenBalance() {
		return tokenBalance;
	}

	public void setTokenBalance(String tokenBalance) {
		this.tokenBalance = tokenBalance;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getEthBalance() {
		return ethBalance;
	}

	public void setEthBalance(String ethBalance) {
		this.ethBalance = ethBalance;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getSrcAssetId() {
		return srcAssetId;
	}

	public void setSrcAssetId(String srcAssetId) {
		this.srcAssetId = srcAssetId;
	}

	public String getDstAssetId() {
		return dstAssetId;
	}

	public void setDstAssetId(String dstAssetId) {
		this.dstAssetId = dstAssetId;
	}

	public String getFeeAssetId() {
		return feeAssetId;
	}

	public void setFeeAssetId(String feeAssetId) {
		this.feeAssetId = feeAssetId;
	}

	public String getDstAssetAmount() {
		return dstAssetAmount;
	}

	public void setDstAssetAmount(String dstAssetAmount) {
		this.dstAssetAmount = dstAssetAmount;
	}

	public String getTransHash() {
		return transHash;
	}

	public void setTransHash(String transHash) {
		this.transHash = transHash;
	}

	
	public String getSrcAmount() {
		return srcAmount;
	}

	public void setSrcAmount(String srcAmount) {
		this.srcAmount = srcAmount;
	}

	public String getFeeAssetAmount() {
		return feeAssetAmount;
	}

	public void setFeeAssetAmount(String feeAssetAmount) {
		this.feeAssetAmount = feeAssetAmount;
	}

	@Override
	public String toString() {
		return "AssetTransfer [transactionId=" + transactionId + ", srcAssetId=" + srcAssetId + ", dstAssetId=" + dstAssetId + ", feeAssetId=" + feeAssetId + ", dstAssetAmount=" + dstAssetAmount
				+ ", transHash=" + transHash + ", leftAssetAmount=" + srcAmount + ", feeAssetAmount=" + feeAssetAmount + "]";
	}

	

}
