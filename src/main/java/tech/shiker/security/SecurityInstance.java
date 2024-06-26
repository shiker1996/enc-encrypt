package tech.shiker.security;

import tech.shiker.enccore.DecryptResult;
import tech.shiker.enccore.EncryptResult;

public interface SecurityInstance {

    EncryptResult encrypt(String src, String key, String index, String salt, Integer iterations) throws Exception;

    DecryptResult decrypt(String src, String key, String index, String salt, Integer iterations) throws Exception;
}
