package tech.shiker.common;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.spec.AlgorithmParameterSpec;

public class Result {
    private final SecretKey secretKey;

    private final AlgorithmParameterSpec paramSpec;

    private final Cipher cipher;

    public Result(SecretKey secretKey, AlgorithmParameterSpec paramSpec, Cipher cipher) {
        this.secretKey = secretKey;
        this.paramSpec = paramSpec;
        this.cipher = cipher;
    }

    public SecretKey secretKey() {
        return secretKey;
    }

    public AlgorithmParameterSpec paramSpec() {
        return paramSpec;
    }

    public Cipher cipher() {
        return cipher;
    }
}
