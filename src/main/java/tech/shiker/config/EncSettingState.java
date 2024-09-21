package tech.shiker.config;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

@State(
        name = "org.intellij.sdk.settings.AppSettingsState",
        storages = @Storage("SdkSettingsPlugin.xml")
)
public final class EncSettingState implements PersistentStateComponent<EncSettingState> {

    public String decryptedType = "AES";
    public String decryptedKey = "Hi World Encrypt";
    public String decryptedInformation = "AES/ECB/PKCS5Padding";
    public String decryptedVi = "";
    public String isHtmlView = "Off";
    public Integer decryptedIteration = 1000;
    public String decryptedSalt = "";
    public String decryptedProperties = "key\\b[ \\t]*:[ \\t]*";

    public static EncSettingState getInstance() {
        return ApplicationManager.getApplication().getService(EncSettingState.class);
    }

    @Override
    public @NotNull EncSettingState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull EncSettingState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

}