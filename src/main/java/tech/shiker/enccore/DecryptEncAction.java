package tech.shiker.enccore;

import com.intellij.diff.DiffContentFactory;
import com.intellij.diff.DiffManager;
import com.intellij.diff.requests.SimpleDiffRequest;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import tech.shiker.common.SecurityConstant;
import tech.shiker.common.SecurityMethod;
import tech.shiker.config.EncSettingState;
import tech.shiker.page.ComparisonFrame;

import java.io.IOException;
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
                    if("Off".equals(EncSettingState.getInstance().isHtmlView)){
                        processFileV2(project, virtualFile);
                    }else{
                        processFile(virtualFile);
                    }
                } catch (Exception ex) {
                    Messages.showMessageDialog(ex.getMessage(), SecurityConstant.ENC_DECRYPT_TITLE, Messages.getInformationIcon());
                }
            } else {
                Messages.showMessageDialog(SecurityConstant.UN_SUPPORT_MESSAGE, SecurityConstant.ENC_DECRYPT_TITLE, Messages.getInformationIcon());
            }
        }
    }

    private boolean isYamlOrPropertiesFile(VirtualFile virtualFile) {
        String fileName = virtualFile.getName();
        return fileName.endsWith(".yml") || fileName.endsWith(".properties");
    }

    private void processFileV2(Project project, VirtualFile virtualFile) throws IOException {
        String text = new String(virtualFile.contentsToByteArray());
        // 使用正则表达式匹配并解密ENC()格式的字符串
        Pattern pattern = Pattern.compile("ENC\\(([^)]+)\\)");
        Matcher matcher = pattern.matcher(text);
        StringBuilder decryptedContent = new StringBuilder();
        int lastEnd = 0;
        StringBuilder message = new StringBuilder();
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            String encryptedText = matcher.group(1); // 获取ENC()包裹的字符串
            DecryptResult decryptResult = decrypt(encryptedText); // 解密

            decryptedContent.append(text, lastEnd, start); // 将上次解密位置到本次解密位置之间的内容添加到解密后的文本中
            if (!decryptResult.isDecryptError()) {
                decryptedContent.append(decryptResult.decryptStr());
            } else {
                message.append(decryptResult.message()).append("\n");
                decryptedContent.append(decryptResult.decryptStr());
            }
            lastEnd = end; // 更新上次解密位置
        }
        decryptedContent.append(text, lastEnd, text.length());
        if(!message.isEmpty()){
            Messages.showMessageDialog(message.toString(), SecurityConstant.ENC_DECRYPT_TITLE, Messages.getInformationIcon());
        }
        DiffContentFactory contentFactory = DiffContentFactory.getInstance();
        SimpleDiffRequest diffRequest = new SimpleDiffRequest("ENC Compare",
                contentFactory.create(text),
                contentFactory.create(decryptedContent.toString()),
                "Source file", "Decrypted file");

        DiffManager.getInstance().showDiff(project, diffRequest);
    }

    private void processFile(VirtualFile virtualFile) throws Exception {
        String text = new String(virtualFile.contentsToByteArray());
        // 使用正则表达式匹配并解密ENC()格式的字符串
        Pattern pattern = Pattern.compile("ENC\\(([^)]+)\\)");
        Matcher matcher = pattern.matcher(text);
        StringBuilder decryptedContent = new StringBuilder();
        StringBuilder originalContent = new StringBuilder();
        int lastEnd = 0;
        StringBuilder message = new StringBuilder();
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            String encryptedText = matcher.group(1); // 获取ENC()包裹的字符串
            DecryptResult decryptResult = decrypt(encryptedText); // 解密

            decryptedContent.append(text, lastEnd, start); // 将上次解密位置到本次解密位置之间的内容添加到解密后的文本中
            if (!decryptResult.isDecryptError()) {
                decryptedContent.append("<font color='green'>").append(decryptResult.decryptStr()).append("</font>");
            } else {
                message.append(decryptResult.message()).append("\n");
                decryptedContent.append("<font color='red'>").append(decryptResult.decryptStr()).append("</font>");
            }
            originalContent.append(text, lastEnd, start);
            originalContent.append("<font color='blue'>ENC(").append(encryptedText).append(")</font>");
            lastEnd = end; // 更新上次解密位置
        }
        if(!message.isEmpty()){
            Messages.showMessageDialog(message.toString(), SecurityConstant.ENC_DECRYPT_TITLE, Messages.getInformationIcon());
        }
        decryptedContent.append(text, lastEnd, text.length());
        originalContent.append(text, lastEnd, text.length());
        String decryptedStr = decryptedContent.toString().replace("\n", "<br>");
        decryptedStr = decryptedStr.replace(" ", "&nbsp;");
        String originalStr = originalContent.toString().replace("\n", "<br>");
        originalStr = originalStr.replace(" ", "&nbsp;");
        showComparisonWindow(new DecryptedVirtualFile(virtualFile, originalStr), new DecryptedVirtualFile(virtualFile, decryptedStr));
    }

    private void showComparisonWindow(VirtualFile originalFile, VirtualFile decryptedFile) throws IOException {
        new ComparisonFrame(originalFile, decryptedFile);
    }

    public DecryptResult decrypt(String sSrc) {
        try {
            // 判断Key是否正确
            if (EncSettingState.getInstance().decryptedKey == null) {
                return new DecryptResult("!!!!ERROR!!!", true, SecurityConstant.KEY_NULL_MESSAGE);
            }
            if (EncSettingState.getInstance().decryptedType == null) {
                return new DecryptResult("!!!!ERROR!!!", true, SecurityConstant.TYPE_NULL_MESSAGE);
            }
            if (EncSettingState.getInstance().decryptedInformation == null) {
                return new DecryptResult("!!!!ERROR!!!", true, SecurityConstant.INFORMATION_NULL_MESSAGE);
            }
            SecurityMethod securityMethod = SecurityMethod.decryptMethod(EncSettingState.getInstance().decryptedType, EncSettingState.getInstance().decryptedInformation);
            if (securityMethod == null){
                return new DecryptResult("!!!!ERROR!!!", true, SecurityConstant.DECRYPT_UNKNOWN_MESSAGE);
            }
            return securityMethod.decryptInstance().decrypt(sSrc, EncSettingState.getInstance().decryptedKey, EncSettingState.getInstance().decryptedVi);
        } catch (Exception ex) {
            return new DecryptResult("!!!!ERROR!!!", true, String.format(SecurityConstant.DECRYPT_ERR_MESSAGE, sSrc, ex.getMessage()));
        }
    }
}
