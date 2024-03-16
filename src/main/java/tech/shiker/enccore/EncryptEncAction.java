package tech.shiker.enccore;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;
import tech.shiker.common.SecurityConstant;
import tech.shiker.common.SecurityMethod;

public class EncryptEncAction extends AnAction {

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }


    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        if (project != null) {
            // 获取用户当前选择的文本
            Editor editor = e.getData(CommonDataKeys.EDITOR);
            if (editor != null) {
                SelectionModel selectionModel = editor.getSelectionModel();
                String selectedText = selectionModel.getSelectedText();
                if (selectedText != null && !selectedText.isEmpty()) {
                    // 进行加密操作
                    EncryptResult encryptResult = encrypt(selectedText);
                    if (encryptResult.isEncryptError()) {
                        Messages.showMessageDialog(project, encryptResult.message(), SecurityConstant.ENC_DECRYPT_TITLE, Messages.getInformationIcon());
                    }
                    // 获取选中文本的开始和结束偏移量
                    int startOffset = selectionModel.getSelectionStart();
                    int endOffset = selectionModel.getSelectionEnd();
                    // 获取文档对象并替换选中文本
                    Document document = editor.getDocument();
                    WriteCommandAction.runWriteCommandAction(project, () ->
                            document.replaceString(startOffset, endOffset, String.format(SecurityConstant.ENCRYPT_RESULT, encryptResult.encryptStr())));
                } else {
                    Messages.showInfoMessage(project, SecurityConstant.ENCRYPT_NULL_MESSAGE, SecurityConstant.ENC_DECRYPT_TITLE);
                }
            }
        }
    }

    public EncryptResult encrypt(String sSrc) {
        try {
            // 判断Key是否正确
            if (DecryptedSettingState.getInstance().decryptedKey == null) {
                return new EncryptResult("!!!!ERROR!!!", true, SecurityConstant.KEY_NULL_MESSAGE);
            }
            if (DecryptedSettingState.getInstance().decryptedType == null) {
                return new EncryptResult("!!!!ERROR!!!", true, SecurityConstant.TYPE_NULL_MESSAGE);
            }
            if (DecryptedSettingState.getInstance().decryptedInformation == null) {
                return new EncryptResult("!!!!ERROR!!!", true, SecurityConstant.INFORMATION_NULL_MESSAGE);
            }
            // 判断Key是否为16位
            if (DecryptedSettingState.getInstance().decryptedKey.length() != 16) {
                Messages.showInfoMessage(SecurityConstant.KEY_INVALID_MESSAGE, SecurityConstant.ENC_DECRYPT_TITLE);
                return new EncryptResult("!!!!ERROR!!!", true, SecurityConstant.KEY_INVALID_MESSAGE);
            }
            SecurityMethod securityMethod = SecurityMethod.decryptMethod(DecryptedSettingState.getInstance().decryptedType, DecryptedSettingState.getInstance().decryptedInformation);
            if (securityMethod == null) {
                return new EncryptResult("!!!!ERROR!!!", true, SecurityConstant.DECRYPT_UNKNOWN_MESSAGE);
            }
            return securityMethod.decryptInstance().encrypt(sSrc, DecryptedSettingState.getInstance().decryptedKey);
        } catch (Exception ex) {
            return new EncryptResult("!!!!ERROR!!!", true, String.format(SecurityConstant.ENCRYPT_ERR_MESSAGE, sSrc, ex.getMessage()));
        }
    }
}
