import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {


    // 加密方法（使用 Base64 编码作为示例，你可以替换为任何加密算法）
    public static String encryptValue(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes());
    }

    public static void encryptAndPrintValues(String input) {
        // 正则表达式匹配 key: value 的模式，并确保值不为空
        String regex = "(\\bkey\\b[ \\t]*:[ \\t]*)(.+)";

        // 编译正则表达式
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        // 遍历所有匹配项并加密值部分
        while (matcher.find()) {
            // 获取捕获的值
            String value = matcher.group(2);
            // 对值进行加密
            String encryptedValue = encryptValue(value);

            // 输出加密后的值
            System.out.println("Original: " + value + ", Encrypted: " + encryptedValue);
        }
    }

    public static void main(String[] args) {
        // 测试输入字符串
        String input = "application:\n" +
                "  key: 123\n" +
                "\n" +
                "app:\n" +
                "  secret-key: 213\n" +
                "  key:\n" +
                "    sds: 123"; // 包含多个键值对的字符串

        // 调用方法进行匹配并加密
        encryptAndPrintValues(input);
    }

}
