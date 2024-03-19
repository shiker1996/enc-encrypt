package tech.shiker.config;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;
import tech.shiker.common.SecurityMethod;

import javax.swing.*;

public class EncToolComponent {


    private final JPanel myMainPanel;
    private final ComboBox<String> decryptedTypeBox = new ComboBox<>();
    private String decryptedType = "AES";
    private final JBTextField decryptedKeyText = new JBTextField("hi World Decrypt");
    private final ComboBox<String> decryptedInformationBox = new ComboBox<>();
    private String decryptedInformation = "AES/ECB/PKCS5Padding";
    private final JBTextField decryptedIv = new JBTextField();

    public EncToolComponent() {
        SecurityMethod.getAllDecryptType().forEach(decryptedTypeBox::addItem);
        SecurityMethod.getAllDecryptInformation().forEach(decryptedInformationBox::addItem);decryptedTypeBox.addActionListener(e -> decryptedType = (String) decryptedTypeBox.getSelectedItem());
        decryptedInformationBox.addActionListener(e -> decryptedInformation = (String) decryptedInformationBox.getSelectedItem());
        myMainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Enter decrypted type: "), decryptedTypeBox, 1, false)
                .addLabeledComponent(new JBLabel("Enter decrypted key:"), decryptedKeyText, 1, false)
                .addLabeledComponent(new JBLabel("Enter decrypted information:"), decryptedInformationBox, 1, false)
                .addLabeledComponent(new JBLabel("Enter decrypted iv:"), decryptedIv, 1,false)
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
        return decryptedType;
    }

    @NotNull
    public String getDecryptedKey() {
        return decryptedKeyText.getText();
    }

    @NotNull
    public String getDecryptedInformation() {
        return decryptedInformation;
    }

    @NotNull
    public String getDecryptedIv() {
        return decryptedIv.getText();
    }

    public void setDecryptedTypeText(@NotNull String decryptedType){
        decryptedTypeBox.setSelectedItem(decryptedType);
    }

    public void setDecryptedKeyText(@NotNull String decryptedKey){
        decryptedKeyText.setText(decryptedKey);
    }

    public void setDecryptedInformation(@NotNull String decryptedInformationStr){
        decryptedInformationBox.setSelectedItem(decryptedInformationStr);
    }

    public void setDecryptedIv(@NotNull String decryptedIvStr){
        decryptedIv.setText(decryptedIvStr);
    }
}
