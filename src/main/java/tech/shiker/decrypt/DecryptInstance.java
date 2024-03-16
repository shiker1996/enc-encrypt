package tech.shiker.decrypt;

import tech.shiker.encdecrypt.DecryptResult;

public interface DecryptInstance {

    DecryptResult decrypt(String src, String key) throws Exception;

    DecryptResult decrypt(String src, String key, String index) throws Exception;
}
