package tech.shiker.security;

import com.intellij.openapi.ui.Messages;
import tech.shiker.common.SecurityConstant;
import tech.shiker.common.SecurityMethod;
import tech.shiker.enccore.DecryptResult;
import tech.shiker.enccore.EncryptResult;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AesECBNoPaddingSecurityInstance implements SecurityInstance{

    public EncryptResult encrypt(String src, String key) throws Exception {
        // 判断Key是否为16位
        if (key.length() != 16) {
            Messages.showInfoMessage(SecurityConstant.KEY_INVALID_MESSAGE, SecurityConstant.ENC_DECRYPT_TITLE);
            return new EncryptResult("!!!!ERROR!!!", true, SecurityConstant.KEY_INVALID_MESSAGE);
        }
        Cipher cipher = Cipher.getInstance(SecurityMethod.AES_ECB_NO_PADDING.decryptInformation());
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), SecurityMethod.AES_ECB_NO_PADDING.decryptType());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedBytes = cipher.doFinal(NoPaddingUtil.addPadding(src));
        return new EncryptResult(Base64.getEncoder().encodeToString(encryptedBytes), false, null);
    }

    @Override
    public EncryptResult encrypt(String src, String key, String index) throws Exception {
        return encrypt(src, key);
    }


    public DecryptResult decrypt(String src, String key) throws Exception {
        // 判断Key是否为16位
        if (key.length() != 16) {
            Messages.showInfoMessage(SecurityConstant.KEY_INVALID_MESSAGE, SecurityConstant.ENC_DECRYPT_TITLE);
            return new DecryptResult("!!!!ERROR!!!", true, SecurityConstant.KEY_INVALID_MESSAGE);
        }
        Cipher cipher = Cipher.getInstance(SecurityMethod.AES_ECB_NO_PADDING.decryptInformation());
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8),  SecurityMethod.AES_ECB_NO_PADDING.decryptType());
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] encryptedBytes = Base64.getDecoder().decode(src);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new DecryptResult(new String(NoPaddingUtil.removePadding(decryptedBytes), StandardCharsets.UTF_8).trim(), false, null);
    }

    @Override
    public DecryptResult decrypt(String src, String key, String index) throws Exception {
        return decrypt(src, key);
    }
}
