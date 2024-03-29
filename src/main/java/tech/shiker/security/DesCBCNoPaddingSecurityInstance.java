package tech.shiker.security;

import tech.shiker.common.SecurityConstant;
import tech.shiker.common.SecurityMethod;
import tech.shiker.enccore.DecryptResult;
import tech.shiker.enccore.EncryptResult;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class DesCBCNoPaddingSecurityInstance implements SecurityInstance{

    public EncryptResult encrypt(String src, String key, String index) throws Exception {
        // 判断Key是否为16位
        if (key.length() != 24) {
            return new EncryptResult("!!!!ERROR!!!", true, SecurityConstant.KEY_INVALID_MESSAGE);
        }
        // 判断Key是否正确
        if (index == null) {
            return new EncryptResult("!!!!ERROR!!!", true, SecurityConstant.IV_NULL_MESSAGE);
        }
        // 判断Key是否为24位
        if (index.length() != 8) {
            return new EncryptResult("!!!!ERROR!!!", true, SecurityConstant.IV_INVALID_MESSAGE);
        }
        Cipher cipher = Cipher.getInstance(SecurityMethod.DES_CBC_NO_PADDING.decryptInformation());
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), SecurityMethod.DES_CBC_NO_PADDING.decryptType());
        IvParameterSpec iv = new IvParameterSpec(index.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
        byte[] encryptedBytes = cipher.doFinal(NoPaddingUtil.addPadding(src));
        return new EncryptResult(Base64.getEncoder().encodeToString(encryptedBytes), false, null);
    }

    public DecryptResult decrypt(String src, String key, String index) throws Exception {
        // 判断Key是否为16位
        if (key.length() != 24) {
            return new DecryptResult("!!!!ERROR!!!", true, SecurityConstant.KEY_INVALID_MESSAGE);
        }
        if (index == null) {
            return new DecryptResult("!!!!ERROR!!!", true, SecurityConstant.IV_NULL_MESSAGE);
        }
        // 判断Key是否为16位
        if (index.length() != 8) {
            return new DecryptResult("!!!!ERROR!!!", true, SecurityConstant.IV_INVALID_MESSAGE);
        }
        Cipher cipher = Cipher.getInstance(SecurityMethod.DES_CBC_NO_PADDING.decryptInformation());
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), SecurityMethod.DES_CBC_NO_PADDING.decryptType());
        IvParameterSpec iv = new IvParameterSpec(index.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
        byte[] encryptedBytes = Base64.getDecoder().decode(src);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new DecryptResult(new String(NoPaddingUtil.removePadding(decryptedBytes)), false, null);
    }

    @Override
    public EncryptResult encrypt(String src, String key, String index, String salt, Integer iterations) throws Exception {
        return encrypt(src, key, index);
    }

    @Override
    public DecryptResult decrypt(String src, String key, String index, String salt, Integer iterations) throws Exception {
        return decrypt(src, key, index);
    }
}
