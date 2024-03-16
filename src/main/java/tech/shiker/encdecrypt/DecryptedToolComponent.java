package tech.shiker.encdecrypt;

import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class DecryptedToolComponent {


    private final JPanel myMainPanel;
    private final JBTextField decryptedTypeText = new JBTextField("AES");
    private final JBTextField decryptedKeyText = new JBTextField("hi World Decrypt");
    private final JBTextField decryptedInformation = new JBTextField("AES/ECB/PKCS5Padding");

    public DecryptedToolComponent() {
        myMainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Enter decrypted type: "), decryptedTypeText, 1, false)
                .addLabeledComponent(new JBLabel("Enter decrypted key:"), decryptedKeyText, 1, false)
                .addLabeledComponent(new JBLabel("Enter decrypted information:"), decryptedInformation, 1, false)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return myMainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return decryptedKeyText;
    }

    @NotNull
    public String getDecryptedType() {
        return decryptedTypeText.getText();
    }

    @NotNull
    public String getDecryptedKey() {
        return decryptedKeyText.getText();
    }

    @NotNull
    public String getDecryptedInformation() {
        return decryptedInformation.getText();
    }

    public void setDecryptedTypeText(@NotNull String decryptedType){
        decryptedTypeText.setText(decryptedType);
    }

    public void setDecryptedKeyText(@NotNull String decryptedKey){
        decryptedKeyText.setText(decryptedKey);
    }

    public void setDecryptedInformation(@NotNull String decryptedInformationStr){
        decryptedInformation.setText(decryptedInformationStr);
    }
}
