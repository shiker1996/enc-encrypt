package tech.shiker.security;

import com.github.weisj.jsvg.S;
import org.codehaus.plexus.util.Base64;
import tech.shiker.common.SecurityMethod;
import tech.shiker.enccore.DecryptResult;
import tech.shiker.enccore.EncryptResult;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class AesECBPkcs5NoPaddingSecurityInstance implements SecurityInstance {

    @Override
    public EncryptResult encrypt(String src, String encryptedKey) throws Exception {
        Cipher cipher = Cipher.getInstance(SecurityMethod.AES_ECB_PKCS5_NO_PADDING.decryptInformation());
        byte[] raw = encryptedKey.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec keySpec = new SecretKeySpec(raw, SecurityMethod.AES_ECB_PKCS5_NO_PADDING.decryptType());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedBytes = cipher.doFinal(src.getBytes());
        encryptedBytes = new Base64().encode(encryptedBytes);
        return new EncryptResult(new String(encryptedBytes), false, null);
    }

    @Override
    public EncryptResult encrypt(String src, String key, String index) throws Exception {
        return null;
    }

    @Override
    public DecryptResult decrypt(String src, String decryptedKey) throws Exception{
        byte[] raw = decryptedKey.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec keySpec = new SecretKeySpec(raw, SecurityMethod.AES_ECB_PKCS5_NO_PADDING.decryptType());
        Cipher cipher = Cipher.getInstance(SecurityMethod.AES_ECB_PKCS5_NO_PADDING.decryptInformation());
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] encrypted1 = new Base64().decode(src.getBytes());//先用base64解密
        byte[] original = cipher.doFinal(encrypted1);
        return new DecryptResult(new String(original, StandardCharsets.UTF_8), false, null);
    }

    @Override
    public DecryptResult decrypt(String src, String key, String index) throws Exception {
        return null;
    }
}
