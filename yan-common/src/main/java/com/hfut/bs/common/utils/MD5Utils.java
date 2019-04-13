package com.hfut.bs.common.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

/**
 * Created by chenxiong on 18/12/2.
 */
@Service
public class MD5Utils {

    public final static String salt = "d1o2n3g4j5u";

    /**
     * 第一次MD5
     * 对用户输入的密码进行加盐值然后进行MD5加密,加密后的密码是(进行网络传输时展示的密码)
     *
     * @param inputPass
     * @return
     */
    public static String inputPassToFormPass(String inputPass) {
        String data = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(data);
    }

    /**
     * 第二次MD5
     * 表单密码转换成数据库密码，dbPass是db中存储的密码
     *
     * @param formPass
     * @param dbSalt   这个盐值是随即生成的,保存到db当中
     * @return
     */
    public static String formPassToDbPass(String formPass, String dbSalt) {
        String data = dbSalt.charAt(1) + dbSalt.charAt(0) + formPass + dbSalt.charAt(4) + dbSalt.charAt(5);
        return md5(data);
    }

    /**
     * 输入的密码直接转换成数据库密码
     *
     * @param inputPass
     * @param dbSalt
     * @return
     */
    public static String inputPassToDbPass(String inputPass, String dbSalt) {
        String formPass = inputPassToFormPass(inputPass);
        return formPassToDbPass(formPass, dbSalt);
    }

    /**
     * MD5
     * 对传入的字符串进行加密然后返回加密结果。
     *
     * @param data
     * @return
     */
    public static String md5(String data) {
        // 可以直接调用commons包中封装好的DigestUtils类对明文进行加密
        return DigestUtils.md5Hex(data);
    }

//    public static void main(String args[]) {
//        String fromPass = inputPassToFormPass("123456");
//        String dbPass = formPassToDbPass(fromPass, "d1o2n3g4j5u");
//        System.out.println(dbPass);
//    }
}
