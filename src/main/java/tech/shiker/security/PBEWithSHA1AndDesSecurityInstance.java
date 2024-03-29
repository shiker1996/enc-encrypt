package tech.shiker.security;

import tech.shiker.common.SecurityConstant;
import tech.shiker.common.SecurityMethod;
import tech.shiker.enccore.DecryptResult;
import tech.shiker.enccore.EncryptResult;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.util.Base64;

public class PBEWithSHA1AndDesSecurityInstance implements SecurityInstance {

    public EncryptResult encrypt(String src, String key, String salt, Integer iterations ) throws Exception {
        if (salt.length() < 8) {
            return new EncryptResult("!!!!ERROR!!!", true, SecurityConstant.SALT_INVALID_MESSAGE);
        }
        if (iterations == 0) {
            return new EncryptResult("!!!!ERROR!!!", true, SecurityConstant.ITERATIONS_INVALID_MESSAGE);
        }
        byte[] saltBytes = salt.getBytes();
        KeySpec keySpec = new PBEKeySpec(key.toCharArray(), saltBytes, iterations);
        SecretKey secretKey = SecretKeyFactory.getInstance(SecurityMethod.PBE_WITH_SHA1_AND_DES.decryptType()).generateSecret(keySpec);
        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(saltBytes, iterations);
        Cipher cipher = Cipher.getInstance(SecurityMethod.PBE_WITH_SHA1_AND_DES.decryptInformation());
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
        byte[] encryptedBytes = cipher.doFinal(src.getBytes());
        return new EncryptResult(Base64.getEncoder().encodeToString(encryptedBytes), false, null);
    }

    public DecryptResult decrypt(String src, String key, String salt, Integer iterations) throws Exception {
        if (salt.length() < 8) {
            return new DecryptResult("!!!!ERROR!!!", true, SecurityConstant.SALT_INVALID_MESSAGE);
        }
        if (iterations == 0) {
            return new DecryptResult("!!!!ERROR!!!", true, SecurityConstant.ITERATIONS_INVALID_MESSAGE);
        }
        byte[] saltBytes = salt.getBytes();
        KeySpec keySpec = new PBEKeySpec(key.toCharArray(), saltBytes, iterations);
        SecretKey secretKey = SecretKeyFactory.getInstance(SecurityMethod.PBE_WITH_SHA1_AND_DES.decryptType()).generateSecret(keySpec);
        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(saltBytes, iterations);
        Cipher cipher = Cipher.getInstance(SecurityMethod.PBE_WITH_SHA1_AND_DES.decryptInformation());
        cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
        byte[] encryptedBytes = Base64.getDecoder().decode(src);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new DecryptResult(new String(decryptedBytes, StandardCharsets.UTF_8), false, null);
    }

    @Override
    public EncryptResult encrypt(String src, String key, String index, String salt, Integer iterations) throws Exception {
        return encrypt(src, key, salt, iterations);
    }

    @Override
    public DecryptResult decrypt(String src, String key, String index, String salt, Integer iterations) throws Exception {
        return decrypt(src, key, salt, iterations);
    }
}
