package com.runbuddy.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;
import java.util.Scanner;
/** 加密密码的
 * Created by Administrator on 2017/3/11.
 */
public class DesTool {
    private static String basicKey = "RunBuddy@2017";

    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);
        System.out.println("-->>please choose your operation(1:encrypt,2:decode):");
        int type = scanner.nextInt();
        if(type == 1){
            System.out.println("please input the string which you want to encrypt:");
            String encryptValue = DesTool.enc(scanner.next());
            System.out.println("after encrypt:" + encryptValue);
        }else if (type == 2) {
            System.out.println("请输入要解密的密文:");
            String encryptValue = dec(scanner.next());
            System.out.println("after decode:" + encryptValue);
        } else {
            System.out.println("-->>please choose your operation(1:encrypt,2:decode):");
        }




    }

    //encrypt
    public static String enc(String str){
        byte[] encryptResult = DesTool.encrypt(str.getBytes());
        String encryptValue = DesTool.byte2hex(encryptResult);
        return encryptValue;
    }
    //decode
    public static String dec(String str){
        byte[] decodeResult = DesTool.decrypt(str.getBytes());
        String decryptValue = new String(decodeResult);
        return decryptValue;
    }


    public static byte[] encrypt(byte[] datasource){
        try{
            //DES算法需要一个可信任的随机数源
            SecureRandom secureRandom = new SecureRandom();
            //创建一个desKeySpec对象
            DESKeySpec desKey = new DESKeySpec(basicKey.getBytes());
            //创建一个秘钥工厂
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            //将DESKeySpec对象转换成SecretKey对象
            SecretKey secretKey = keyFactory.generateSecret(desKey);
            //Cipher对象实际完成加密工作
            Cipher cipher = Cipher.getInstance("DES");
            //用秘钥初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE,secretKey,secureRandom);
            //正式执行加密
            return cipher.doFinal(datasource);

        }catch (Throwable e){
            e.printStackTrace();
        }
        return null;
    }


    public static byte[] decrypt(byte[] src){
        try {
            //DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
            // 创建一个DESKeySpec对象
            DESKeySpec desKey = new DESKeySpec(basicKey.getBytes());
            // 创建一个密匙工厂
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // 将DESKeySpec对象转换成SecretKey对象
            SecretKey securekey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance("DES");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
            // 真正开始解密操作
            return cipher.doFinal(src);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String byte2hex(byte[] b){
        String hs = "";
        String stmp = "";

        for(int n = 0;n < b.length ; n++){
            stmp  = (Integer.toHexString(b[n] & 0XFF));
            if(stmp.length() == 1){
                hs = hs + "0" + stmp;
            }else{
                hs=hs+stmp;
            }
        }
        return hs.toUpperCase();


    }

    public static byte[] hex2byte(byte[] b){
        if ((b.length % 2) != 0) {
            throw new IllegalArgumentException("the length is not even number!");
        }
        byte[] b2 = new byte[b.length / 2];
        for(int n=0;n<b.length;n+=2){
            String item = new String(b,n,2);
            b2[n /2] = (byte)Integer.parseInt(item,16);
        }
        return b2;
    }


}
