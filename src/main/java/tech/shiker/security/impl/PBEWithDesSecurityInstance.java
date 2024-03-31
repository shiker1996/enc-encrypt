package tech.shiker.security.impl;

import org.jetbrains.annotations.NotNull;
import tech.shiker.common.SecurityConstant;
import tech.shiker.common.SecurityInfo;
import tech.shiker.enccore.DecryptResult;
import tech.shiker.enccore.EncryptResult;
import tech.shiker.security.SecurityInstance;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class PBEWithDesSecurityInstance implements SecurityInstance {
    private final SecurityInfo securityInfo;

    public PBEWithDesSecurityInstance(SecurityInfo securityInfo) {
        this.securityInfo = securityInfo;
    }
    public EncryptResult encrypt(String src, String key, String salt, Integer iterations) throws Exception {     // 判断Key是否为16位
        if (salt.length() < securityInfo.saltLength()) {
            return new EncryptResult("!!!!ERROR!!!", true, SecurityConstant.SALT_INVALID_MESSAGE);
        }
        if (iterations == securityInfo.iterations()) {
            return new EncryptResult("!!!!ERROR!!!", true, SecurityConstant.ITERATIONS_INVALID_MESSAGE);
        }

        Result result = getSecurityCipherInfo(key, salt, iterations);
        result.cipher().init(Cipher.ENCRYPT_MODE, result.secretKey(), result.paramSpec());
        byte[] encryptedBytes = result.cipher().doFinal(src.getBytes());
        return new EncryptResult(Base64.getEncoder().encodeToString(encryptedBytes), false, null);
    }


    public DecryptResult decrypt(String src, String key, String salt, Integer iterations) throws Exception {
        if (salt.length() < securityInfo.saltLength()) {
            return new DecryptResult("!!!!ERROR!!!", true, SecurityConstant.SALT_INVALID_MESSAGE);
        }
        if (iterations == securityInfo.iterations()) {
            return new DecryptResult("!!!!ERROR!!!", true, SecurityConstant.ITERATIONS_INVALID_MESSAGE);
        }
        Result result = getSecurityCipherInfo(key, salt, iterations);
        result.cipher().init(Cipher.DECRYPT_MODE, result.secretKey(), result.paramSpec());
        byte[] encryptedBytes = Base64.getDecoder().decode(src);
        byte[] decryptedBytes = result.cipher().doFinal(encryptedBytes);
        return new DecryptResult(new String(decryptedBytes, StandardCharsets.UTF_8), false, null);
    }

    @NotNull
    private Result getSecurityCipherInfo(String key, String salt, Integer iterations) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException {
        byte[] saltBytes = salt.getBytes();
        KeySpec keySpec = new PBEKeySpec(key.toCharArray(), saltBytes, iterations);
        SecretKey secretKey = SecretKeyFactory.getInstance(securityInfo.decryptType()).generateSecret(keySpec);
        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(saltBytes, iterations);
        Cipher cipher = Cipher.getInstance(securityInfo.decryptInformation());
        return new Result(secretKey, paramSpec, cipher);
    }

    private record Result(SecretKey secretKey, AlgorithmParameterSpec paramSpec, Cipher cipher) {
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
