package tech.shiker.security;

import java.nio.charset.StandardCharsets;

public class NoPaddingUtil {
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
