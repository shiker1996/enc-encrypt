package tech.shiker.security.impl;

import tech.shiker.common.NoPaddingUtil;
import tech.shiker.common.SecurityConstant;
import tech.shiker.common.SecurityInfo;
import tech.shiker.enccore.DecryptResult;
import tech.shiker.enccore.EncryptResult;
import tech.shiker.security.SecurityInstance;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.ResourceBundle;

public class ECBSecurityInstance implements SecurityInstance {
    private final SecurityInfo securityInfo;

    private static final ResourceBundle bundle = ResourceBundle.getBundle("META-INF.EncToolBundle");

    public ECBSecurityInstance(SecurityInfo securityInfo) {
        this.securityInfo = securityInfo;
    }

    public EncryptResult encrypt(String src, String key) throws Exception {
        // 判断Key是否为16位
        if (key.length() != securityInfo.keyLength()) {
            return new EncryptResult("!!!!ERROR!!!", true, String.format(bundle.getString(SecurityConstant.KEY_INVALID_MESSAGE), securityInfo.keyLength()));
        }
        Cipher cipher = Cipher.getInstance(securityInfo.decryptInformation());
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), securityInfo.decryptType());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedBytes = cipher.doFinal(securityInfo.handlePadding() ? NoPaddingUtil.addPadding(src) : src.getBytes());
        return new EncryptResult(Base64.getEncoder().encodeToString(encryptedBytes), false, null);
    }

    public DecryptResult decrypt(String src, String key) throws Exception {
        // 判断Key是否为16位
        if (key.length() != securityInfo.keyLength()) {
            return new DecryptResult("!!!!ERROR!!!", true, String.format(bundle.getString(SecurityConstant.KEY_INVALID_MESSAGE), securityInfo.keyLength()));
        }
        Cipher cipher = Cipher.getInstance(securityInfo.decryptInformation());
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), securityInfo.decryptType());
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] encryptedBytes = Base64.getDecoder().decode(src);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new DecryptResult(new String(securityInfo.handlePadding() ? NoPaddingUtil.removePadding(decryptedBytes) : decryptedBytes, StandardCharsets.UTF_8).trim(), false, null);
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
