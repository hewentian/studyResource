package com.hewentian.algorithm;

import org.apache.log4j.Logger;

import com.hewentian.util.StringUtils;

/**
 * <p>
 * <b>AESUtil</b> 是AES加、解密工具类
 * </p>
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2017年8月23日 下午1:59:42
 * @since JDK 1.8
 * 
 */
public class AESUtil {
	private static Logger log = Logger.getLogger(AESUtil.class);

	/** AES 加密密钥 */
	private static String encryptKey = "HWT!@#$%^&*(,HWT";
	private static byte[] key = null;
	private static AES aes = null;

	static {
		try {
			key = MD5.genByte(encryptKey.getBytes("UTF-8"));
			aes = new AES();
		} catch (Exception e) {
			log.error(e);
		}
	}

	public static byte[] encode(byte[] datas) {
		return aes.encode(datas, key);
	}

	public static byte[] decode(byte[] datas) {
		return aes.decode(datas, key);
	}

	/**
	 * 加密字符串
	 * 
	 * @date 2017年8月23日 下午3:02:50
	 * @param str
	 * @return
	 */
	public static String encodeStr2Hex(String str) {
		try {
			byte[] encode = encode(str.getBytes("UTF-8"));
			String encodeHexStr = StringUtils.byte2hex(encode);

			return encodeHexStr.trim();
		} catch (Exception e) {
			log.error(e);
		}

		return null;
	}

	/**
	 * 解密字符串
	 * 
	 * @date 2017年8月23日 下午3:03:01
	 * @param hex
	 * @return
	 */
	public static String decodeHex2Str(String hex) {
		try {
			byte[] encode = StringUtils.hex2byte(hex);
			byte[] decode = decode(encode);

			String str = new String(decode, "UTF-8");
			return str.trim();
		} catch (Exception e) {
			log.error(e);
		}

		return null;
	}

	public static void main(String[] args) throws Exception {
		String str = "7A0052007A004100400049006900300021006100";
		String hex = encodeStr2Hex(str);
		System.out.println(hex);

		String str2 = decodeHex2Str(hex);
		System.out.println(str.equals(str2));
	}
}
