package tech.shiker.config;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.JBCheckboxMenuItem;
import com.intellij.ui.components.JBCheckBox;
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
    private final JBCheckBox isHtmlView = new JBCheckBox ("Use html viewer", true); ;

    public EncToolComponent() {
        JLabel decryptedIvLabel = new JBLabel("Enter decrypted iv:");
        SecurityMethod.getType2Information().keySet().forEach(decryptedTypeBox::addItem);
        decryptedTypeBox.addActionListener(e -> {
            decryptedType = (String) decryptedTypeBox.getSelectedItem();
            updateInformationComboBox(decryptedType, decryptedInformationBox);
        });
        decryptedInformationBox.addActionListener(e -> {
            decryptedInformation = (String) decryptedInformationBox.getSelectedItem();
            updateDecryptedVi(decryptedIv, decryptedIvLabel, decryptedInformation);
        });
        myMainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Html compare view:"), isHtmlView, 1, false)
                .addLabeledComponent(new JBLabel("Enter decrypted type: "), decryptedTypeBox, 1, false)
                .addLabeledComponent(new JBLabel("Enter decrypted information:"), decryptedInformationBox, 1, false)
                .addLabeledComponent(new JBLabel("Enter decrypted key:"), decryptedKeyText, 1, false)
                .addLabeledComponent(decryptedIvLabel, decryptedIv, 1, false)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    private void updateDecryptedVi(JBTextField decryptedIv, JLabel decryptedIvLabel, String decryptedInformation) {
        // 根据解密信息的不同，更新IV的可见性
        if (decryptedInformation != null && decryptedInformation.contains("CBC")) {
            decryptedIv.setVisible(true);
            decryptedIvLabel.setVisible(true);
        }else{
            decryptedIv.setVisible(false);
            decryptedIvLabel.setVisible(false);
        }
    }

    // 更新城市ComboBox的内容
    private void updateInformationComboBox(String decryptedType, ComboBox<String> decryptedInformationBox) {
        String[] cities = SecurityMethod.getType2Information().get(decryptedType).toArray(new String[0]);
        decryptedInformationBox.setModel(new DefaultComboBoxModel<>(cities));
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

    public boolean getIsHtmlView() {
        return isHtmlView.isSelected();
    }

    public void setDecryptedTypeText(@NotNull String decryptedType) {
        decryptedTypeBox.setSelectedItem(decryptedType);
    }

    public void setDecryptedKeyText(@NotNull String decryptedKey) {
        decryptedKeyText.setText(decryptedKey);
    }

    public void setDecryptedInformation(@NotNull String decryptedInformationStr) {
        decryptedInformationBox.setSelectedItem(decryptedInformationStr);
    }

    public void setDecryptedIv(@NotNull String decryptedIvStr) {
        decryptedIv.setText(decryptedIvStr);
    }

    public void setIsHtmlView(boolean isHtmlViewStr){
        isHtmlView.setSelected(isHtmlViewStr);
    }
}
