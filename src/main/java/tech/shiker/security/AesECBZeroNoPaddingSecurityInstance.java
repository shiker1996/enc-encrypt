package tech.shiker.security;

import tech.shiker.common.SecurityMethod;
import tech.shiker.enccore.DecryptResult;
import tech.shiker.enccore.EncryptResult;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AesECBZeroNoPaddingSecurityInstance implements SecurityInstance{
    @Override
    public EncryptResult encrypt(String src, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(SecurityMethod.AES_ECB_ZERO_NO_PADDING.decryptInformation(),"SunJCE");
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), SecurityMethod.AES_ECB_ZERO_NO_PADDING.decryptType());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedBytes = cipher.doFinal(padString(src).getBytes(StandardCharsets.UTF_8));
        return new EncryptResult(Base64.getEncoder().encodeToString(encryptedBytes), false, null);
    }

    @Override
    public EncryptResult encrypt(String src, String key, String index) throws Exception {
        return null;
    }

    @Override
    public DecryptResult decrypt(String src, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(SecurityMethod.AES_ECB_ZERO_NO_PADDING.decryptInformation(), "SunJCE");
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8),  SecurityMethod.AES_ECB_ZERO_NO_PADDING.decryptType());
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] encryptedBytes = Base64.getDecoder().decode(src);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new DecryptResult(new String(decryptedBytes, StandardCharsets.UTF_8).trim(), false, null);
    }

    @Override
    public DecryptResult decrypt(String src, String key, String index) throws Exception {
        return null;
    }

    // 填充字符串到16的倍数长度
    private static String padString(String input) {
        int paddingLength = 16 - input.length() % 16;
        return input + "\0".repeat(paddingLength);
    }
}
