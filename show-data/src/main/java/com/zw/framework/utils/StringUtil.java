package com.zw.framework.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	
	public static final String EMPTY = "";

    private static final String HEX_NUMS_STR = "0123456789ABCDEF";
	
	/**
     * 判断字符串是否不为空
     * 
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
    	return (str == null || EMPTY.equals(str));
    }
    
    /**
     * 判断字符串是否不为空
     * 
     * @param str
     * @return
     */
    public static boolean isAnyEmpty(String... strs) {
    	if (strs == null || strs.length == 0) {
    		return true;
    	}
    	for (String str : strs) {
    		if ((str == null || EMPTY.equals(str))) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * 判断字符串是否不为空
     * 
     * @param str
     * @return
     */
    public static boolean isAllEmpty(String... strs) {
    	if (strs == null || strs.length == 0) {
    		return true;
    	}
    	for (String str : strs) {
    		if ((str != null && !EMPTY.equals(str))) {
    			return false;
    		}
    	}
    	return true;
    }

    /**
     * 判断字符串是否不为空
     * 
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        boolean isEmpty = false;
        if (str != null) {
            if (str instanceof String) {
                String ss = str.toString();
                if (!ss.trim().equals("")) {
                    isEmpty = true;
                }
            }
        }

        return isEmpty;
    }

    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String number) {
        boolean isNumber = false;
        if (isNotEmpty(number)) {
            isNumber = number.matches("^([1-9]\\d*)|(0)$");
        }
        return isNumber;
    }

    /**
     * 判断是否是汉语
     * 
     * @param hanyu
     * @return
     */
    public static boolean isHanYu(String hanyu) {
        boolean isHanYu = false;
        if (isNotEmpty(hanyu)) {
            isHanYu = hanyu.matches("^[\\u4e00-\\u9fa5]*$");
        }
        return isHanYu;
    }
    
    /**
     * float转String
     * @param f
     * @return
     */
    public static String format(float f) {
        if ("NaN".equals(String.valueOf(f))) {
            return "0.0";
        }
        DecimalFormat df = new DecimalFormat("0.0000000");
        return df.format(f);
    }
    
    /**
     * 转换为BigDecimal后，比较大小
     * @param value1
     * @param value2
     * @return -1：第二个数大；0：相等；1：第一个数大
     */
    public static int compare(String value1, String value2) {
        BigDecimal d1 = new BigDecimal(value1);
        BigDecimal d2 = new BigDecimal(value2);
        if (d1.compareTo(d2) < 0) {
            return -1;
        } else if (d1.compareTo(d2) == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * 验证邮箱地址是否正确
     * 
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
    
    /**
     * 验证手机号码
     * 
     * @param mobiles
     * @return
     */
    public static boolean checkMobileNumber(String mobileNumber) {
        boolean flag = false;
        try {
            Pattern regex = Pattern
                    .compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证IP地址
     * @param ipAddress
     * @return
     */
    public static boolean isIpv4(String ipAddress) {

        String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

        Pattern pattern = Pattern.compile(ip);
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();

    }

    /**
     * 将指定byte数组转换成16进制字符串
     *
     * @param b
     * @return
     */
    public static String byteToHexString(byte[] b) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }

    /**
     * 将16进制字符串转换成字节数组
     *
     * @param hex
     * @return
     */
    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] hexChars = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (HEX_NUMS_STR.indexOf(hexChars[pos]) << 4 | HEX_NUMS_STR
                    .indexOf(hexChars[pos + 1]));
        }
        return result;
    }
}
