package com.blockchain.dto;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.wordnik.swagger.annotations.ApiModel;

@ApiModel(value="TransInfoListDto交易信息联盟对象")
public class TransInfoListDto {
	@NotNull(message="不能为空")
	@Min(value=1,message="最小值不能小于1")
	Integer pageNo;
	@NotNull(message="不能为空")
	@Min(value=1,message="最小值不能小于1")
	Integer pageLimit;
	
	
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageLimit() {
		return pageLimit;
	}
	public void setPageLimit(Integer pageLimit) {
		this.pageLimit = pageLimit;
	}
	@Override
	public String toString() {
		return "TransInfoListDto [pageNo=" + pageNo + ", pageLimit=" + pageLimit + "]";
	}

	
	
}
