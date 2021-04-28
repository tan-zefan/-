import java.util.Base64;
import javax.crypto.Cipher;
import qrCode.QRCodeCreater;
import qrCode.TwoDimensionCode;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
/**
 * Java RSA 加密工具类
 * 参考： https://blog.csdn.net/qy20115549/article/details/83105736
 */
public class RSAUtils {
    /**
     * 随机生成密钥对
     */
    public static void genKeyPair() throws NoSuchAlgorithmException {
        CreateKeyPair.getnewkey();
    }
    /**
     * RSA私钥加密
     *
     * @param str       加密字符串
     * @param publicKey 私钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str, String privateKey) throws Exception {
        //base64编码的私钥
        byte[] decoded = Base64.getDecoder().decode(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, priKey);
        String outStr = Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }
    /**
     * RSA公钥解密
     *
     * @param str        解密字符串
     * @param privateKey 公钥
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str, String publicKey) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = Base64.getDecoder().decode(str);
        //base64编码的私钥
        byte[] decoded = Base64.getDecoder().decode(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));//.generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }
    public static void main(String[] args) throws Exception {
        // long temp = System.currentTimeMillis();
        String imgPath = "./a.png";
        //生成公钥和私钥
        genKeyPair();
        String publicKey = CreateKeyPair.getPublicKey();
        String PrivateKey = CreateKeyPair.getPrivateKey();
        //加密字符串
        System.out.println("公钥:" + publicKey);
        System.out.println("私钥:" + PrivateKey);
        // System.out.println("生成密钥消耗时间:" + (System.currentTimeMillis() - temp) / 1000.0 + "秒");
        String message = "dfadfsadfalas";
        System.out.println("原文:" + message);
        // temp = System.currentTimeMillis();
        String messageEn = encrypt(message, PrivateKey);
        TwoDimensionCode handler = new TwoDimensionCode();
        handler.encoderQRCode(messageEn, imgPath, "png");
        System.out.println("密文:" + messageEn);
        // System.out.println("加密消耗时间:" + (System.currentTimeMillis() - temp) / 1000.0 + "秒");
        // temp = System.currentTimeMillis();
        String messageDe = decrypt(handler.decoderQRCode(imgPath), publicKey);
        System.out.println("解密:" + messageDe);
        // System.out.println("解密消耗时间:" + (System.currentTimeMillis() - temp) / 1000.0 + "秒");
    }
}
