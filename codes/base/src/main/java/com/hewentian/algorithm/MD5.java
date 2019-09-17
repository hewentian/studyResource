/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hewentian.algorithm;

import java.security.MessageDigest;

import com.hewentian.util.StringUtils;

/**
 * 
 * <p>
 * <b>MD5</b> 是MD5工具类
 * </p>
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2017年8月23日 下午1:57:05
 * @since JDK 1.8
 * 
 */
public class MD5 {
	/**
	 * 产生MD5信息摘要
	 * 
	 * @since 2015年10月21日
	 * 
	 * @param bytes
	 * @return
	 */
	public static byte[] genByte(byte[] bytes) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(bytes);
			byte[] digests = md.digest();

			return digests;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 产生MD5信息摘要，默认将以UTF-8编码传入参数
	 * 
	 * @since 2015年10月21日
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] genByte(String str) {
		try {
			return genByte(str.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 产生MD5信息摘要
	 * 
	 * @since 2015年10月21日
	 * 
	 * @param bytes
	 *            不能为空
	 * @return
	 */
	public static String genStr(byte[] bytes) {
		try {
			byte[] digests = genByte(bytes);

			return StringUtils.byte2hex(digests);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 产生MD5信息摘要，默认将以UTF-8编码传入参数
	 * 
	 * @since 2015年10月21日
	 * 
	 * @param str
	 *            不能为空
	 * @return
	 */
	public static String genStr(String str) {
		try {
			return genStr(str.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}