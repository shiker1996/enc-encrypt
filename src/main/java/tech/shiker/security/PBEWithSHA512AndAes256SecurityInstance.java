package tech.shiker.security;

import tech.shiker.common.SecurityConstant;
import tech.shiker.common.SecurityMethod;
import tech.shiker.enccore.DecryptResult;
import tech.shiker.enccore.EncryptResult;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.util.Base64;

public class PBEWithSHA512AndAes256SecurityInstance implements SecurityInstance {
    @Override
    public EncryptResult encrypt(String src, String key, String index, String salt, Integer iterations ) throws Exception {
        if (salt.length() < 8) {
            return new EncryptResult("!!!!ERROR!!!", true, SecurityConstant.SALT_INVALID_MESSAGE);
        }
        if (iterations == 0) {
            return new EncryptResult("!!!!ERROR!!!", true, SecurityConstant.ITERATIONS_INVALID_MESSAGE);
        }
        // 判断Key是否为16位
        if (index.length() != 16) {
            return new EncryptResult("!!!!ERROR!!!", true, SecurityConstant.IV_INVALID_MESSAGE);
        }
        byte[] saltBytes = salt.getBytes();
        KeySpec keySpec = new PBEKeySpec(key.toCharArray(), saltBytes, iterations, 128);
        SecretKey secretKey = SecretKeyFactory.getInstance(SecurityMethod.PBE_WITH_SHA512_AND_AES_256.decryptType()).generateSecret(keySpec);
        IvParameterSpec iv = new IvParameterSpec(index.getBytes(StandardCharsets.UTF_8));
        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(saltBytes, iterations, iv);
        Cipher cipher = Cipher.getInstance(SecurityMethod.PBE_WITH_SHA512_AND_AES_256.decryptInformation());
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
        byte[] encryptedBytes = cipher.doFinal(src.getBytes());
        return new EncryptResult(Base64.getEncoder().encodeToString(encryptedBytes), false, null);
    }

    @Override
    public DecryptResult decrypt(String src, String key, String index, String salt, Integer iterations) throws Exception {
        if (salt.length() < 8) {
            return new DecryptResult("!!!!ERROR!!!", true, SecurityConstant.SALT_INVALID_MESSAGE);
        }
        if (iterations == 0) {
            return new DecryptResult("!!!!ERROR!!!", true, SecurityConstant.ITERATIONS_INVALID_MESSAGE);
        }
        // 判断Key是否为16位
        if (index.length() != 16) {
            return new DecryptResult("!!!!ERROR!!!", true, SecurityConstant.IV_INVALID_MESSAGE);
        }
        byte[] saltBytes = salt.getBytes();
        KeySpec keySpec = new PBEKeySpec(key.toCharArray(), saltBytes, iterations, 128);
        SecretKey secretKey = SecretKeyFactory.getInstance(SecurityMethod.PBE_WITH_SHA512_AND_AES_256.decryptType()).generateSecret(keySpec);
        IvParameterSpec iv = new IvParameterSpec(index.getBytes(StandardCharsets.UTF_8));
        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(saltBytes, iterations, iv);
        Cipher cipher = Cipher.getInstance(SecurityMethod.PBE_WITH_SHA512_AND_AES_256.decryptInformation());
        cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
        byte[] encryptedBytes = Base64.getDecoder().decode(src);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new DecryptResult(new String(decryptedBytes, StandardCharsets.UTF_8), false, null);
    }
}
