package tech.shiker.config;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;
import tech.shiker.common.SecurityMethod;

import javax.swing.*;
import java.util.Objects;

public class EncToolComponent {


    private final JPanel myMainPanel;
    private final ComboBox<String> decryptedTypeBox = new ComboBox<>();
    private final ComboBox<String> decryptedInformationBox = new ComboBox<>();
    private final JBTextField decryptedKeyText = new JBTextField("hi World Decrypt");
    private final JBTextField decryptedIv = new JBTextField();
    private final JBTextField decryptedSalt = new JBTextField();
    private final JCheckBox isHtmlView = new JCheckBox ("Off", false);
    private final JSpinner decryptedIteration = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));

    public EncToolComponent() {
        JLabel decryptedIvLabel = new JBLabel("Enter decrypted iv:");
        JLabel decryptedSaltLabel = new JBLabel("Enter decrypted salt:");
        JLabel decryptedStepLabel = new JBLabel("Enter decrypted iteration count:");
        SecurityMethod.getType2Information().keySet().stream().sorted().forEach(decryptedTypeBox::addItem);
        myMainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Html compare view:"), isHtmlView, 1, false)
                .addLabeledComponent(new JBLabel("Enter decrypted type: "), decryptedTypeBox, 1, false)
                .addLabeledComponent(new JBLabel("Enter decrypted information:"), decryptedInformationBox, 1, false)
                .addLabeledComponent(new JBLabel("Enter decrypted key:"), decryptedKeyText, 1, false)
                .addLabeledComponent(decryptedIvLabel, decryptedIv, 1, false)
                .addLabeledComponent(decryptedSaltLabel, decryptedSalt, 1, false)
                .addLabeledComponent(decryptedStepLabel, decryptedIteration, 1, false)
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
                ||decryptedInformation.startsWith("PBEWith"))) {
            decryptedIv.setVisible(true);
            decryptedIvLabel.setVisible(true);
        }else{
            decryptedIv.setVisible(false);
            decryptedIvLabel.setVisible(false);
        }
    }

    private void updateDecryptedIteration(JSpinner decryptedIteration, JLabel decryptedIterationLabel, String decryptedType) {
        // 根据解密信息的不同，更新IV的可见性
        if (decryptedType != null && decryptedType.startsWith("PBEWith")) {
            decryptedIteration.setVisible(true);
            decryptedIterationLabel.setVisible(true);
        }else{
            decryptedIteration.setVisible(false);
            decryptedIterationLabel.setVisible(false);
        }
    }

    private void updateDecryptedSalt(JBTextField decryptedSalt, JLabel decryptedSaltLabel, String decryptedType){
        if (decryptedType != null && decryptedType.startsWith("PBEWith")) {
            decryptedSalt.setVisible(true);
            decryptedSaltLabel.setVisible(true);
        }else{
            decryptedSalt.setVisible(false);
            decryptedSaltLabel.setVisible(false);
        }
    }

    // 更新城市ComboBox的内容
    private void updateInformationComboBox(String decryptedType, ComboBox<String> decryptedInformationBox) {
        String[] decryptedInformationArr = SecurityMethod.getType2Information().get(decryptedType).stream().sorted().toArray(String[]::new);
        decryptedInformationBox.setModel(new DefaultComboBoxModel<>(decryptedInformationArr));
        decryptedInformationBox.setSelectedItem(decryptedInformationArr[0]);
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

    @NotNull
    public String getDecryptedIv() {
        return decryptedIv.getText();
    }

    @NotNull
    public String getIsHtmlView() {
        return isHtmlView.getText();
    }

    public Integer getDecryptedIteration(){
        return (Integer) decryptedIteration.getValue();
    }

    @NotNull
    public String getDecryptedSalt(){
        return decryptedSalt.getText();
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

    public void setIsHtmlView(@NotNull String isHtmlViewStr){
        isHtmlView.setText(isHtmlViewStr);
    }

    public void setDecryptedIteration(Integer decryptedIterations){
        decryptedIteration.setValue(decryptedIterations);
    }

    public void setDecryptedSalt(String decryptedSaltStr){
        decryptedSalt.setText(decryptedSaltStr);
    }
}
