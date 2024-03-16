package tech.shiker.enccore;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class DecryptedToolConfigurable implements Configurable {

    private DecryptedToolComponent mySettingsComponent;

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
        mySettingsComponent = new DecryptedToolComponent();
        return mySettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        DecryptedSettingState settings = DecryptedSettingState.getInstance();
        boolean modified = !mySettingsComponent.getDecryptedType().equals(settings.decryptedKey);
        modified |= !mySettingsComponent.getDecryptedKey().equals(settings.decryptedKey);
        modified |= !mySettingsComponent.getDecryptedInformation().equals(settings.decryptedInformation);
        return modified;
    }

    @Override
    public void apply() {
        DecryptedSettingState settings = DecryptedSettingState.getInstance();
        settings.decryptedType = mySettingsComponent.getDecryptedType();
        settings.decryptedKey = mySettingsComponent.getDecryptedKey();
        settings.decryptedInformation = mySettingsComponent.getDecryptedInformation();
    }

    @Override
    public void reset() {
        DecryptedSettingState settings = DecryptedSettingState.getInstance();
        mySettingsComponent.setDecryptedKeyText(settings.decryptedKey);
        mySettingsComponent.setDecryptedTypeText(settings.decryptedType);
        mySettingsComponent.setDecryptedInformation(settings.decryptedInformation);
    }

    @Override
    public void disposeUIResources() {
        mySettingsComponent = null;
    }

}