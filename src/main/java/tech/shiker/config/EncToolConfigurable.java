package tech.shiker.config;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Objects;

public class EncToolConfigurable implements Configurable {

    private EncToolComponent mySettingsComponent;

    // A default constructor with no arguments is required because this implementation
    // is registered in an applicationConfigurable EP

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "ENC Encrypt";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return mySettingsComponent.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        mySettingsComponent = new EncToolComponent();
        return mySettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        EncSettingState settings = EncSettingState.getInstance();
        boolean modified = !Objects.equals(mySettingsComponent.getDecryptedType(), settings.decryptedKey);
        modified |= !Objects.equals(mySettingsComponent.getDecryptedKey(), settings.decryptedKey);
        modified |= !Objects.equals(mySettingsComponent.getDecryptedInformation(), settings.decryptedInformation);
        modified |= !Objects.equals(mySettingsComponent.getDecryptedIv(), settings.decryptedVi);
        modified |= !Objects.equals(mySettingsComponent.getIsHtmlView(), settings.isHtmlView);
        modified |= !Objects.equals(mySettingsComponent.getDecryptedIteration(), settings.decryptedIteration);
        modified |= !Objects.equals(mySettingsComponent.getDecryptedSalt(), settings.decryptedSalt);
        modified |= !Objects.equals(mySettingsComponent.getDecryptedProperties(), settings.decryptedProperties);
        return modified;
    }

    @Override
    public void apply() {
        EncSettingState settings = EncSettingState.getInstance();
        settings.decryptedType = mySettingsComponent.getDecryptedType();
        settings.decryptedKey = mySettingsComponent.getDecryptedKey();
        settings.decryptedInformation = mySettingsComponent.getDecryptedInformation();
        settings.decryptedVi = mySettingsComponent.getDecryptedIv();
        settings.isHtmlView = mySettingsComponent.getIsHtmlView();
        settings.decryptedIteration = mySettingsComponent.getDecryptedIteration();
        settings.decryptedSalt = mySettingsComponent.getDecryptedSalt();
        settings.decryptedProperties = mySettingsComponent.getDecryptedProperties();
    }

    @Override
    public void reset() {
        EncSettingState settings = EncSettingState.getInstance();
        mySettingsComponent.setDecryptedKeyText(settings.decryptedKey);
        mySettingsComponent.setDecryptedTypeText(settings.decryptedType);
        mySettingsComponent.setDecryptedInformation(settings.decryptedInformation);
        mySettingsComponent.setDecryptedIv(settings.decryptedVi);
        mySettingsComponent.setIsHtmlView(settings.isHtmlView);
        mySettingsComponent.setDecryptedIteration(settings.decryptedIteration);
        mySettingsComponent.setDecryptedSalt(settings.decryptedSalt);
        mySettingsComponent.setDecryptedProperties(settings.decryptedProperties);
    }

    @Override
    public void disposeUIResources() {
        mySettingsComponent = null;
    }

}