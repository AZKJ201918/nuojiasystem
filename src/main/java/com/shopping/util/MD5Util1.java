package com.shopping.util;

import org.apache.commons.codec.digest.DigestUtils;


public class MD5Util1 {


    public static String getMD5(String source){
        return DigestUtils.md5Hex(source);
    }

}
