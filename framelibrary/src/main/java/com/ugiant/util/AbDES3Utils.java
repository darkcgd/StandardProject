package com.ugiant.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.kobjects.base64.Base64;

/**
 * 
 * @ClassName: com.qust.SecretUtils
 * @Description: 3DES���ܽ��ܹ�����
 * @author zhaokaiqiang
 * @date 2014-11-13 ����11:28:14
 * 
 */
/**
 * 3DES���ܹ�����
 */
public class AbDES3Utils {
	  // ��Կ
    private final static String secretKey = "lightintek@1234567890.com";
    // ����
    private final static String iv = "01234567";
    // �ӽ���ͳһʹ�õı��뷽ʽ
    private final static String encoding = "utf-8";

    /**
     * 3DES����
     * 
     * @param plainText ��ͨ�ı�
     * @return
     * @throws Exception 
     */
    public static String encode(String plainText) throws Exception {
            Key deskey = null;
            DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);

            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
            byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
            return Base64.encode(encryptData);
    }

    /**
     * 3DES����
     * 
     * @param encryptText �����ı�
     * @return
     * @throws Exception
     */
    public static String decode(String encryptText) throws Exception {
            Key deskey = null;
            DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

            byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));

            return new String(decryptData, encoding);
    }
    
    public static void main(String[] args) throws Exception{
//    	String str = encode("123456");
//    	System.out.println(str);
    	System.out.println(decode("rvETkATW7i54qJuS9pWYiw=="));
    }
}
