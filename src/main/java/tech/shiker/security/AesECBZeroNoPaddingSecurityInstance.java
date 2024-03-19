package tech.shiker.security;

import tech.shiker.common.SecurityMethod;
import tech.shiker.enccore.DecryptResult;
import tech.shiker.enccore.EncryptResult;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AesECBZeroNoPaddingSecurityInstance implements SecurityInstance{

    public EncryptResult encrypt(String src, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(SecurityMethod.AES_ECB_ZERO_NO_PADDING.decryptInformation());
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), SecurityMethod.AES_ECB_ZERO_NO_PADDING.decryptType());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedBytes = cipher.doFinal(addPadding(src));
        return new EncryptResult(Base64.getEncoder().encodeToString(encryptedBytes), false, null);
    }

    @Override
    public EncryptResult encrypt(String src, String key, String index) throws Exception {
        return encrypt(src, key);
    }


    public DecryptResult decrypt(String src, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(SecurityMethod.AES_ECB_ZERO_NO_PADDING.decryptInformation());
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8),  SecurityMethod.AES_ECB_ZERO_NO_PADDING.decryptType());
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] encryptedBytes = Base64.getDecoder().decode(src);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new DecryptResult(new String(removePadding(decryptedBytes), StandardCharsets.UTF_8).trim(), false, null);
    }

    @Override
    public DecryptResult decrypt(String src, String key, String index) throws Exception {
        return decrypt(src, key);
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
