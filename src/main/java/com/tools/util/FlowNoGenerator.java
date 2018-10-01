/**
 * Copyright: Copyright (c)2013
 * Company: yeepay
 */
package com.tools.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * <pre>
 * 业务流水号生成器-30位定长
 * (性能:1s大概生成50万个流水号)
 * <pre>
 */
public class FlowNoGenerator {

	static String material = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	static String material2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	static int length = material.length();
	static int randomLenth = 7;

	/**
	 * <pre>
	 * 生成30位定长的流水号
	 * 
	 * @param systemCode 系统编号-2位
	 * @param bizCode 业务编号-3位
	 * @return
	 * </pre>
	 */
	public static String generate(String systemCode, String bizCode) {
		if (systemCode == null || systemCode.trim().length() != 2) {
			throw new IllegalArgumentException("systemCode长度为2个字母");
		}
		if (bizCode == null || bizCode.trim().length() != 3) {
			throw new IllegalArgumentException("bizCode长度为3个字母");
		}
		//SimpleDateFormat非线程安全
		return systemCode + bizCode
				+ new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
				+ getRandomStr(length);
	}
	
	public static String generateForMarketingCoupon(String bizCode) {
		if (bizCode == null || bizCode.trim().length() != 3) {
			throw new IllegalArgumentException("bizCode长度为3个字母");
		}
		//SimpleDateFormat非线程安全
		return  bizCode
//				+ System.nanoTime()
				+ new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
				+ getRandomStr(material2.length(),8,material2);
	}

	/**
	 * 生成协议编号
	 * @param bizCode XY0:小额免密, XY1:协议扣款, XY2:订阅支付
	 * @return
	 */
	public static String generateForAgreementNo(String bizCode){
		return generateForMarketingCoupon(bizCode);
	}

	public static void main(String[] args) {
		long s = System.nanoTime();
		Set<String> nos = new HashSet<String>();
		int count = 0;
		long total = 10000000;
		for(int i=0;i<total;i++){
			String no = FlowNoGenerator.generateForMarketingCoupon("ABC");
			System.out.println(no);
			if(!nos.contains(no)){
				nos.add(no);
			}else{
				count++;
//				System.out.println(no);
			}
		}
		System.out.println(count);
		System.out.println(count*1.0/total);
		long e = System.nanoTime();
		System.out.println((e-s));
	}

	/**
	 * 生成30位定长的流水号
	 * 
	 * @param systemCode
	 *            系统编号-长度2位
	 * @return
	 */
	public static String generate(String systemCode) {
		return generate(systemCode, "000");
	}

	private static String getRandomStr(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < randomLenth; i++) {
			sb.append(material.charAt(new Random().nextInt(length - 1)));
		}
		return sb.toString();
	}
	
	private static String getRandomStr(int length, int randomLenth, String material) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < randomLenth; i++) {
			sb.append(material.charAt(new Random().nextInt(length - 1)));
		}
		return sb.toString();
	}


}
