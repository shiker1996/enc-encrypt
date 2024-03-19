package tech.shiker.security;

import tech.shiker.common.SecurityMethod;
import tech.shiker.enccore.DecryptResult;
import tech.shiker.enccore.EncryptResult;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AesECBPkcs5PaddingSecurityInstance implements SecurityInstance {

    public EncryptResult encrypt(String src, String encryptedKey) throws Exception {
        Cipher cipher = Cipher.getInstance(SecurityMethod.AES_ECB_PKCS5_PADDING.decryptInformation());
        byte[] raw = encryptedKey.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec keySpec = new SecretKeySpec(raw, SecurityMethod.AES_ECB_PKCS5_PADDING.decryptType());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedBytes = cipher.doFinal(src.getBytes());
        return new EncryptResult(Base64.getEncoder().encodeToString(encryptedBytes), false, null);
    }

    @Override
    public EncryptResult encrypt(String src, String key, String index) throws Exception {
        return encrypt(src, key);
    }

    public DecryptResult decrypt(String src, String decryptedKey) throws Exception{
        byte[] raw = decryptedKey.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec keySpec = new SecretKeySpec(raw, SecurityMethod.AES_ECB_PKCS5_PADDING.decryptType());
        Cipher cipher = Cipher.getInstance(SecurityMethod.AES_ECB_PKCS5_PADDING.decryptInformation());
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] encryptedBytes = Base64.getDecoder().decode(src);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new DecryptResult(new String(decryptedBytes, StandardCharsets.UTF_8), false, null);
    }

    @Override
    public DecryptResult decrypt(String src, String key, String index) throws Exception {
        return decrypt(src, key);
    }
}
