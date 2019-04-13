package com.hfut.bs.common.storage;

/**
 * 定义文件类型及其对应魔数
 * 用魔数来防范文件上传攻击的原理非常简单，读取上传文件的前28个字节，转为十六进制，与魔数对比，就可以判断此文件是否为允许的文件类型
 */
public enum FileType {
	JPEG("FFD8FF"),

	PNG("89504E47"),

	GIF("47494638");
	
	private String value = "";

	private FileType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}