package com.zw.framework.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.UUID;

import javax.xml.bind.DatatypeConverter;

/**
 * 生成AppId与AppKey
 * 
 * @author zhangws
 *
 */
public class UniqueCodeUtils {

	private static final Integer SALT_LENGTH = 12;


	public static void main(String[] args) {
		String appId = getUuid();
		System.out.println("-------- " + appId);
		String appKey = getAppKey();
		System.out.println("-------- " + appKey);
		System.out.println("-------- " + getAppKey());

	}

	/**
	 * 
	 * @return
	 */
	public static String getUuid() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		// 去掉"-"符号
		String temp = str.substring(0, 8) + str.substring(9, 13)
				+ str.substring(14, 18) + str.substring(19, 23)
				+ str.substring(24);
		return temp;
	}

	public static String getAppKey() {
		String value = String.valueOf(System.currentTimeMillis());
		return DatatypeConverter.printBase64Binary(value.substring(value.length()-8).getBytes());
	}

	/**
	 * 验证口令是否合法
	 * 
	 * @param password
	 * @param passwordInDb
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static boolean validPassword(String password, String passwordInDb)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		// 将16进制字符串格式口令转换成字节数组
		byte[] pwdInDb = StringUtil.hexStringToByte(passwordInDb);
		// 声明盐变量
		byte[] salt = new byte[SALT_LENGTH];
		// 将盐从数据库中保存的口令字节数组中提取出来
		System.arraycopy(pwdInDb, 0, salt, 0, SALT_LENGTH);
		// 创建消息摘要对象
		MessageDigest md = MessageDigest.getInstance("MD5");
		// 将盐数据传入消息摘要对象
		md.update(salt);
		// 将口令的数据传给消息摘要对象
		md.update(password.getBytes("UTF-8"));
		// 生成输入口令的消息摘要
		byte[] digest = md.digest();
		// 声明一个保存数据库中口令消息摘要的变量
		byte[] digestInDb = new byte[pwdInDb.length - SALT_LENGTH];
		// 取得数据库中口令的消息摘要
		System.arraycopy(pwdInDb, SALT_LENGTH, digestInDb, 0, digestInDb.length);
		// 比较根据输入口令生成的消息摘要和数据库中消息摘要是否相同
		if (Arrays.equals(digest, digestInDb)) {
			// 口令正确返回口令匹配消息
			return true;
		} else {
			// 口令不正确返回口令不匹配消息
			return false;
		}
	}

	/**
	 * 获得加密后的16进制形式口令
	 * 
	 * @param password
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String getEncryptedPwd(String password) {
		// 声明加密后的口令数组变量
		byte[] pwd = null;
		// 随机数生成器
		SecureRandom random = new SecureRandom();
		// 声明盐数组变量
		byte[] salt = new byte[SALT_LENGTH];
		// 将随机数放入盐变量中
		random.nextBytes(salt);

		// 声明消息摘要对象
		MessageDigest md = null;
		byte[] digest = null;
		// 创建消息摘要
		try {
			md = MessageDigest.getInstance("MD5");
			// 将盐数据传入消息摘要对象
			md.update(salt);
			// 将口令的数据传给消息摘要对象
			md.update(password.getBytes("UTF-8"));
			// 获得消息摘要的字节数组
			digest = md.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			if (digest == null) {
				return "";
			}
		}

		// 因为要在口令的字节数组中存放盐，所以加上盐的字节长度
		pwd = new byte[digest.length + SALT_LENGTH];
		// 将盐的字节拷贝到生成的加密口令字节数组的前12个字节，以便在验证口令时取出盐
		System.arraycopy(salt, 0, pwd, 0, SALT_LENGTH);
		// 将消息摘要拷贝到加密口令字节数组从第13个字节开始的字节
		System.arraycopy(digest, 0, pwd, SALT_LENGTH, digest.length);
		// 将字节数组格式加密后的口令转化为16进制字符串格式的口令
		return StringUtil.byteToHexString(pwd);
	}



}
