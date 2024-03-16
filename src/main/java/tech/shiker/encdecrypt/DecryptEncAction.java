package tech.shiker.encdecrypt;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.components.JBScrollPane;
import org.codehaus.plexus.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecryptEncAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        if (project != null) {
            VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
            if (virtualFile != null && isYamlOrPropertiesFile(virtualFile)) {
                try {
                    processFile(virtualFile);
                } catch (Exception ex) {
                    Messages.showMessageDialog(ex.getMessage(), DecryptConstant.DECRYPT_TITLE, Messages.getInformationIcon());
                }
            } else {
                Messages.showMessageDialog(DecryptConstant.UN_SUPPORT_MESSAGE, DecryptConstant.DECRYPT_TITLE, Messages.getInformationIcon());
            }
        }
    }

    private boolean isYamlOrPropertiesFile(VirtualFile virtualFile) {
        String fileName = virtualFile.getName();
        return fileName.endsWith(".yml") || fileName.endsWith(".properties");
    }

    private void processFile(VirtualFile virtualFile) throws Exception {
        String text = new String(virtualFile.contentsToByteArray());
        // 使用正则表达式匹配并解密ENC()格式的字符串
        Pattern pattern = Pattern.compile("ENC\\(([^)]+)\\)");
        Matcher matcher = pattern.matcher(text);
        StringBuilder decryptedContent = new StringBuilder();
        StringBuilder originalContent = new StringBuilder();
        int lastEnd = 0;
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            String encryptedText = matcher.group(1); // 获取ENC()包裹的字符串
            DecryptResult decryptResult = decrypt(encryptedText); // 解密

            decryptedContent.append(text, lastEnd, start); // 将上次解密位置到本次解密位置之间的内容添加到解密后的文本中
            if (!decryptResult.isDecryptError()) {
                decryptedContent.append("<font color='green'>").append(decryptResult.decryptStr()).append("</font>");
            } else {
                decryptedContent.append("<font color='red'>").append(decryptResult.decryptStr()).append("</font>");
            }
            originalContent.append(text, lastEnd, start);
            originalContent.append("<font color='blue'>ENC(").append(encryptedText).append(")</font>");
            lastEnd = end; // 更新上次解密位置
        }
        decryptedContent.append(text, lastEnd, text.length());
        originalContent.append(text, lastEnd, text.length());
        String decryptedStr = decryptedContent.toString().replace("\n", "<br>");
        String originalStr = originalContent.toString().replace("\n", "<br>");
        showComparisonWindow(new DecryptedVirtualFile(virtualFile, originalStr), new DecryptedVirtualFile(virtualFile, decryptedStr));
    }

    private void showComparisonWindow(VirtualFile originalFile, VirtualFile decryptedFile) throws IOException {
        JFrame frame = new JFrame(originalFile.getName());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        JEditorPane originalArea = new JEditorPane();
        originalArea.setContentType(DecryptConstant.CONTENT_TYPE);
        originalArea.setText(new String(originalFile.contentsToByteArray()));
        JBScrollPane originalScrollPane = new JBScrollPane(originalArea);

        JEditorPane decryptedArea = new JEditorPane();
        decryptedArea.setContentType(DecryptConstant.CONTENT_TYPE);
        decryptedArea.setText(new String(decryptedFile.contentsToByteArray()));
        JBScrollPane decryptedScrollPane = new JBScrollPane(decryptedArea);

        BoundedRangeModel scrollModel = new DefaultBoundedRangeModel();
        originalScrollPane.getVerticalScrollBar().setModel(scrollModel);
        decryptedScrollPane.getVerticalScrollBar().setModel(scrollModel);

        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(originalScrollPane);
        panel.add(decryptedScrollPane);
        frame.add(panel);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public DecryptResult decrypt(String sSrc) {
        try {
            // 判断Key是否正确
            if (DecryptedSettingState.getInstance().decryptedKey == null) {
                Messages.showInfoMessage(DecryptConstant.KEY_NULL_MESSAGE, DecryptConstant.DECRYPT_TITLE);
                return new DecryptResult("!!!!ERROR!!!", true);
            }
            // 判断Key是否为16位
            if (DecryptedSettingState.getInstance().decryptedKey.length() != 16) {
                Messages.showInfoMessage(DecryptConstant.KEY_INVALID_MESSAGE, DecryptConstant.DECRYPT_TITLE);
                return new DecryptResult("!!!!ERROR!!!", true);
            }
            byte[] raw = DecryptedSettingState.getInstance().decryptedKey.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec keySpec = new SecretKeySpec(raw, DecryptedSettingState.getInstance().decryptedType);
            Cipher cipher = Cipher.getInstance(DecryptedSettingState.getInstance().decryptedInformation);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] encrypted1 = new Base64().decode(sSrc.getBytes());//先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            return new DecryptResult(new String(original, StandardCharsets.UTF_8), false);
        } catch (Exception ex) {
            Messages.showInfoMessage(String.format(DecryptConstant.DECRYPT_ERR_MESSAGE, sSrc, ex.getMessage()), DecryptConstant.DECRYPT_TITLE);
            return new DecryptResult("!!!!ERROR!!!", true);
        }
    }
}
