package com.cxytiandi.elementui.utils;

import cn.hutool.core.bean.BeanUtil;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;

/**
 * 工具类的规范例子
 * 
 * 
 * @author 肖文杰
 * 
 */
public class ObjectUtil {

	@SneakyThrows
	public  static Object copyAttribute(Object source, Object dest) {
		// org.springframework.beans.BeanUtils.copyProperties(source, dest);
		//org.apache.commons.beanutils.BeanUtils.copyProperties(dest, source);
		BeanUtil.copyProperties(source,dest);
		return dest;
	}
}
