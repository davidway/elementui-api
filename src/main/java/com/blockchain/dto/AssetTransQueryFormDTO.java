package com.blockchain.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;


@ApiModel(value="交易查询表单")
public class AssetTransQueryFormDTO {
	@ApiModelProperty(value="转入账户",required=false)
	private String dstAccount;
	@ApiModelProperty(value="转出账户",required=false)
	private String srcAccount;
	@ApiModelProperty(value="平台唯一标识一次交易的ID",required=false)
	private String transactionId;
	@ApiModelProperty(value="月份不传默认查当月，传了查指定月份，使用transaction_id可以不传,格式为YYYYMM",required=true)
	@Pattern(regexp = "^\\d{4}\\d{2}$",message="月份必须匹配YYYYMM")
	private String month;
	@NotNull
	@Max(value=20,message="页码limit最大为20")
	@Min(value=1,message="页码必须大于1")
	@ApiModelProperty(value="页码limit，最小为1，最大为20",required=true)
	private Integer pageLimit;
	@ApiModelProperty(value="页码,最小值不能小于1",required=true)
	@Min(value=1,message="最小值不能小于1")
	private Integer pageNo;
	

	
	public String getSrcAccount() {
		return srcAccount;
	}
	public void setSrcAccount(String srcAccount) {
		this.srcAccount = srcAccount;
	}
	public String getDstAccount() {
		return dstAccount;
	}
	public void setDstAccount(String dstAccount) {
		this.dstAccount = dstAccount;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Integer getPageLimit() {
		return pageLimit;
	}
	public void setPageLimit(Integer pageLimit) {
		this.pageLimit = pageLimit;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	@Override
	public String toString() {
		return "AssetTransQueryForm [dstAccount=" + dstAccount + ", srcAccount=" + srcAccount + ", transactionId=" + transactionId + ", month=" + month + ", pageLimit=" + pageLimit + ", pageNo="
				+ pageNo + "]";
	}
	
}