package tech.shiker.enccore;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import tech.shiker.common.SecurityConstant;
import tech.shiker.config.EncSettingState;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EncryptPropAction extends AnAction {


    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
        PsiFile psiFile = anActionEvent.getData(CommonDataKeys.PSI_FILE);

        if (project != null && psiFile != null) {
            if (EncSettingState.getInstance().decryptedProperties == null) {
                return;
            }
            String matchedStr = "(%s)(.+)";
            String regex = String.format(matchedStr, EncSettingState.getInstance().decryptedProperties);
            replaceInFile(psiFile, regex, project);
        }
    }

    public void replaceInFile(PsiFile psiFile, String regex, Project project) {
        Document document = psiFile.getViewProvider().getDocument();
        if (document == null) {
            return;
        }
        Pattern pattern = Pattern.compile(regex);
        String fileText = document.getText();
        Matcher matcher = pattern.matcher(fileText);
        StringBuffer resultString = new StringBuffer();
        while (matcher.find()) {
            String key = matcher.group(1);
            String value = matcher.group(2);
            EncryptResult encryptResult = EncryptSupport.encrypt(value);
            if (encryptResult.isEncryptError()) {
                Messages.showMessageDialog(project, encryptResult.message(), SecurityConstant.ENC_DECRYPT_TITLE, Messages.getInformationIcon());
                return;
            }
            matcher.appendReplacement(resultString, key + String.format(SecurityConstant.ENCRYPT_RESULT, encryptResult.encryptStr()));
        }
        matcher.appendTail(resultString);
        WriteCommandAction.runWriteCommandAction(project, () -> {
            document.setText(resultString.toString());
        });
    }


}
