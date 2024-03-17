package tech.shiker.config;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class EncToolConfigurable implements Configurable {

    private EncToolComponent mySettingsComponent;

    // A default constructor with no arguments is required because this implementation
    // is registered in an applicationConfigurable EP

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "ENC Decrypt";
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
        boolean modified = !mySettingsComponent.getDecryptedType().equals(settings.decryptedKey);
        modified |= !mySettingsComponent.getDecryptedKey().equals(settings.decryptedKey);
        modified |= !mySettingsComponent.getDecryptedInformation().equals(settings.decryptedInformation);
        return modified;
    }

    @Override
    public void apply() {
        EncSettingState settings = EncSettingState.getInstance();
        settings.decryptedType = mySettingsComponent.getDecryptedType();
        settings.decryptedKey = mySettingsComponent.getDecryptedKey();
        settings.decryptedInformation = mySettingsComponent.getDecryptedInformation();
    }

    @Override
    public void reset() {
        EncSettingState settings = EncSettingState.getInstance();
        mySettingsComponent.setDecryptedKeyText(settings.decryptedKey);
        mySettingsComponent.setDecryptedTypeText(settings.decryptedType);
        mySettingsComponent.setDecryptedInformation(settings.decryptedInformation);
    }

    @Override
    public void disposeUIResources() {
        mySettingsComponent = null;
    }

}