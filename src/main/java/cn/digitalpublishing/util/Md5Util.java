package cn.digitalpublishing.util;

import java.security.MessageDigest;

public class Md5Util {

	private static final String ENCRY_MODEL = "MD5";
	
	public static String md5(String str) {
		return encrypt(ENCRY_MODEL, str);
	}

	public static String encrypt(String algorithm, String str) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance(algorithm);
			md.update(str.getBytes("UTF-8"));
			StringBuffer sb = new StringBuffer(32);
			byte[] bytes = md.digest();
			for (int i = 0; i < bytes.length; ++i) {
				int b = bytes[i] & 0xFF;
				if (b < 16)
					sb.append('0');

				sb.append(Integer.toHexString(b));
			}
			return sb.toString();
		} catch (Exception e) {
		}
		return "";
	}

	
}
