package tech.shiker.config;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;
import tech.shiker.common.SecurityConstant;
import tech.shiker.common.SecurityMethod;

import javax.swing.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class EncToolComponent {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("META-INF.EncToolBundle");

    private final JPanel myMainPanel;
    private final ComboBox<String> decryptedTypeBox = new ComboBox<>();
    private final ComboBox<String> decryptedInformationBox = new ComboBox<>();
    private final JBTextField decryptedKeyText = new JBTextField("Hi World Encrypt");
    private final JBTextField decryptedIv = new JBTextField();
    private final JBTextField decryptedSalt = new JBTextField();
    private final JCheckBox isHtmlView = new JCheckBox("Off", false);
    private final JBTextField decryptedProperties = new JBTextField("key\\b[ \\t]*:[ \\t]*");
    private final JSpinner decryptedIteration = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));

    public EncToolComponent() {
        JLabel decryptedIvLabel = new JBLabel(bundle.getString(SecurityConstant.DECRYPT_IV_LABEL));
        JLabel decryptedSaltLabel = new JBLabel(bundle.getString(SecurityConstant.DECRYPT_SALT_LABEL));
        JLabel decryptedStepLabel = new JBLabel(bundle.getString(SecurityConstant.DECRYPT_STEP_LABEL));
        decryptedProperties.setToolTipText(bundle.getString(SecurityConstant.DECRYPTED_PROPERTIES_TIP));
        SecurityMethod.getType2Information().keySet().forEach(decryptedTypeBox::addItem);

        myMainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel(bundle.getString(SecurityConstant.IS_HTML_VIEW_LABEL)), isHtmlView, 1, false)
                .addLabeledComponent(new JBLabel(bundle.getString(SecurityConstant.DECRYPTED_TYPE_LABEL)), decryptedTypeBox, 1, false)
                .addLabeledComponent(new JBLabel(bundle.getString(SecurityConstant.DECRYPTED_INFORMATION_LABEL)), decryptedInformationBox, 1, false)
                .addLabeledComponent(new JBLabel(bundle.getString(SecurityConstant.DECRYPTED_KEY_LABEL)), decryptedKeyText, 1, false)
                .addLabeledComponent(decryptedIvLabel, decryptedIv, 1, false)
                .addLabeledComponent(decryptedSaltLabel, decryptedSalt, 1, false)
                .addLabeledComponent(decryptedStepLabel, decryptedIteration, 1, false)
                .addLabeledComponent(new JBLabel(bundle.getString(SecurityConstant.DECRYPTED_MATCH_KEY_LABEL)), decryptedProperties, 1, false)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();

        decryptedTypeBox.addActionListener(e -> {
            String decryptedType = (String) decryptedTypeBox.getSelectedItem();
            updateInformationComboBox(decryptedType, decryptedInformationBox);
            updateDecryptedIteration(decryptedIteration, decryptedStepLabel, decryptedType);
            updateDecryptedSalt(decryptedSalt, decryptedSaltLabel, decryptedType);
        });
        decryptedInformationBox.addActionListener(e -> {
            String decryptedInformation = (String) decryptedInformationBox.getSelectedItem();
            updateDecryptedVi(decryptedIv, decryptedIvLabel, decryptedInformation);
        });
        isHtmlView.addActionListener(e -> {
            if (isHtmlView.isSelected()) {
                isHtmlView.setText("On");
            } else {
                isHtmlView.setText("Off");
            }
        });
    }

    private void updateDecryptedVi(JBTextField decryptedIv, JLabel decryptedIvLabel, String decryptedInformation) {
        // 根据解密信息的不同，更新IV的可见性
        if (decryptedInformation != null
                && ((decryptedInformation.contains("CBC"))
                || decryptedInformation.startsWith("PBEWith"))) {
            decryptedIv.setVisible(true);
            decryptedIvLabel.setVisible(true);
        } else {
            decryptedIv.setVisible(false);
            decryptedIvLabel.setVisible(false);
        }
    }

    private void updateDecryptedIteration(JSpinner decryptedIteration, JLabel decryptedIterationLabel, String decryptedType) {
        // 根据解密信息的不同，更新IV的可见性
        if (decryptedType != null && decryptedType.startsWith("PBEWith")) {
            decryptedIteration.setVisible(true);
            decryptedIterationLabel.setVisible(true);
        } else {
            decryptedIteration.setVisible(false);
            decryptedIterationLabel.setVisible(false);
        }
    }

    private void updateDecryptedSalt(JBTextField decryptedSalt, JLabel decryptedSaltLabel, String decryptedType) {
        if (decryptedType != null && decryptedType.startsWith("PBEWith")) {
            decryptedSalt.setVisible(true);
            decryptedSaltLabel.setVisible(true);
        } else {
            decryptedSalt.setVisible(false);
            decryptedSaltLabel.setVisible(false);
        }
    }

    // 更新城市ComboBox的内容
    private void updateInformationComboBox(String decryptedType, ComboBox<String> decryptedInformationBox) {
        String[] decryptedInformationArr = SecurityMethod.getType2Information().get(decryptedType).toArray(new String[0]);
        decryptedInformationBox.setModel(new DefaultComboBoxModel<>(decryptedInformationArr));
        decryptedInformationBox.setSelectedItem(decryptedInformationArr[0]);
    }

    public String getDecryptedProperties() {
        return decryptedProperties.getText();
    }

    public void setDecryptedProperties(@NotNull String decryptedPropertiesStr) {
        decryptedProperties.setText(decryptedPropertiesStr);
    }

    public JPanel getPanel() {
        return myMainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return decryptedKeyText;
    }

    @NotNull
    public String getDecryptedType() {
        return (String) Objects.requireNonNull(decryptedTypeBox.getSelectedItem());
    }

    @NotNull
    public String getDecryptedKey() {
        return decryptedKeyText.getText();
    }

    @NotNull
    public String getDecryptedInformation() {
        return (String) Objects.requireNonNull(decryptedInformationBox.getSelectedItem());
    }

    public void setDecryptedInformation(@NotNull String decryptedInformationStr) {
        decryptedInformationBox.setSelectedItem(decryptedInformationStr);
    }

    @NotNull
    public String getDecryptedIv() {
        return decryptedIv.getText();
    }

    public void setDecryptedIv(@NotNull String decryptedIvStr) {
        decryptedIv.setText(decryptedIvStr);
    }

    @NotNull
    public String getIsHtmlView() {
        return isHtmlView.getText();
    }

    public void setIsHtmlView(@NotNull String isHtmlViewStr) {
        isHtmlView.setText(isHtmlViewStr);
    }

    public Integer getDecryptedIteration() {
        return (Integer) decryptedIteration.getValue();
    }

    public void setDecryptedIteration(Integer decryptedIterations) {
        decryptedIteration.setValue(decryptedIterations);
    }

    @NotNull
    public String getDecryptedSalt() {
        return decryptedSalt.getText();
    }

    public void setDecryptedSalt(String decryptedSaltStr) {
        decryptedSalt.setText(decryptedSaltStr);
    }

    public void setDecryptedTypeText(@NotNull String decryptedType) {
        decryptedTypeBox.setSelectedItem(decryptedType);
    }

    public void setDecryptedKeyText(@NotNull String decryptedKey) {
        decryptedKeyText.setText(decryptedKey);
    }
}
