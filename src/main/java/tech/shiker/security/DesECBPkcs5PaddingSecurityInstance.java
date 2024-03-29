package tech.shiker.security;

import tech.shiker.common.SecurityConstant;
import tech.shiker.common.SecurityMethod;
import tech.shiker.enccore.DecryptResult;
import tech.shiker.enccore.EncryptResult;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DesECBPkcs5PaddingSecurityInstance implements SecurityInstance {
    public EncryptResult encrypt(String src, String encryptedKey) throws Exception {
        // 判断Key是否为16位
        if (encryptedKey.length() != 24) {
            return new EncryptResult("!!!!ERROR!!!", true, SecurityConstant.KEY_INVALID_MESSAGE);
        }
        Cipher cipher = Cipher.getInstance(SecurityMethod.DES_ECB_PKCS5_PADDING.decryptInformation());
        byte[] raw = encryptedKey.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec keySpec = new SecretKeySpec(raw, SecurityMethod.DES_ECB_PKCS5_PADDING.decryptType());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedBytes = cipher.doFinal(src.getBytes());
        return new EncryptResult(Base64.getEncoder().encodeToString(encryptedBytes), false, null);
    }

    public DecryptResult decrypt(String src, String decryptedKey) throws Exception{
        // 判断Key是否为16位
        if (decryptedKey.length() != 24) {
            return new DecryptResult("!!!!ERROR!!!", true, SecurityConstant.KEY_INVALID_MESSAGE);
        }
        byte[] raw = decryptedKey.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec keySpec = new SecretKeySpec(raw, SecurityMethod.DES_ECB_PKCS5_PADDING.decryptType());
        Cipher cipher = Cipher.getInstance(SecurityMethod.DES_ECB_PKCS5_PADDING.decryptInformation());
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] encryptedBytes = Base64.getDecoder().decode(src);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new DecryptResult(new String(decryptedBytes, StandardCharsets.UTF_8), false, null);
    }

    @Override
    public EncryptResult encrypt(String src, String key, String index, String salt, Integer iterations) throws Exception {
        return encrypt(src, key);
    }

    @Override
    public DecryptResult decrypt(String src, String key, String index, String salt, Integer iterations) throws Exception {
        return decrypt(src, key);
    }
}
