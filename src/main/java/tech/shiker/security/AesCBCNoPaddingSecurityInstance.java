package tech.shiker.security;

import com.intellij.openapi.ui.Messages;
import tech.shiker.common.SecurityConstant;
import tech.shiker.common.SecurityMethod;
import tech.shiker.config.EncSettingState;
import tech.shiker.enccore.DecryptResult;
import tech.shiker.enccore.EncryptResult;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AesCBCNoPaddingSecurityInstance implements SecurityInstance{

    @Override
    public EncryptResult encrypt(String src, String key, String index) throws Exception {
        // 判断Key是否正确
        if (index == null) {
            return new EncryptResult("!!!!ERROR!!!", true, SecurityConstant.IV_NULL_MESSAGE);
        }
        // 判断Key是否为16位
        if (index.length() != 16) {
            Messages.showInfoMessage(SecurityConstant.KEY_INVALID_MESSAGE, SecurityConstant.ENC_DECRYPT_TITLE);
            return new EncryptResult("!!!!ERROR!!!", true, SecurityConstant.IV_INVALID_MESSAGE);
        }
        Cipher cipher = Cipher.getInstance(SecurityMethod.AES_CBC_ZERO_NO_PADDING.decryptInformation());
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), SecurityMethod.AES_CBC_ZERO_NO_PADDING.decryptType());
        IvParameterSpec iv = new IvParameterSpec(index.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
        byte[] encryptedBytes = cipher.doFinal(addPadding(src));
        return new EncryptResult(Base64.getEncoder().encodeToString(encryptedBytes), false, null);
    }

    @Override
    public DecryptResult decrypt(String src, String key, String index) throws Exception {
        // 判断Key是否正确
        if (index == null) {
            return new DecryptResult("!!!!ERROR!!!", true, SecurityConstant.IV_NULL_MESSAGE);
        }
        // 判断Key是否为16位
        if (index.length() != 16) {
            Messages.showInfoMessage(SecurityConstant.KEY_INVALID_MESSAGE, SecurityConstant.ENC_DECRYPT_TITLE);
            return new DecryptResult("!!!!ERROR!!!", true, SecurityConstant.IV_INVALID_MESSAGE);
        }
        Cipher cipher = Cipher.getInstance(SecurityMethod.AES_CBC_ZERO_NO_PADDING.decryptInformation());
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), SecurityMethod.AES_CBC_ZERO_NO_PADDING.decryptType());
        IvParameterSpec iv = new IvParameterSpec(index.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
        byte[] encryptedBytes = Base64.getDecoder().decode(src);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new DecryptResult(new String(removePadding(decryptedBytes)), false, null);
    }

    public static byte[] addPadding(String input) {
        int blockSize = 16;
        int paddingLength = blockSize - (input.length() % blockSize);
        byte[] paddedInput = new byte[input.length() + paddingLength];
        System.arraycopy(input.getBytes(StandardCharsets.UTF_8), 0, paddedInput, 0, input.length());
        for (int i = input.length(); i < paddedInput.length; i++) {
            paddedInput[i] = (byte) paddingLength;
        }
        return paddedInput;
    }

    public static byte[] removePadding(byte[] input) {
        int paddingLength = input[input.length - 1];
        byte[] unPaddedInput = new byte[input.length - paddingLength];
        System.arraycopy(input, 0, unPaddedInput, 0, unPaddedInput.length);
        return unPaddedInput;
    }
}