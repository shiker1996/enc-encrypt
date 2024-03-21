package tech.shiker.config;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = "org.intellij.sdk.settings.AppSettingsState",
        storages = @Storage("SdkSettingsPlugin.xml")
)
public final class EncSettingState implements PersistentStateComponent<EncSettingState> {

    public String decryptedType = "AES";
    public String decryptedKey = "hi World Decrypt";
    public String decryptedInformation = "AES/ECB/PKCS5Padding";
    public String decryptedVi = null;
    public String isHtmlView = "On";

    public static EncSettingState getInstance() {
        return ApplicationManager.getApplication().getService(EncSettingState.class);
    }

    @Nullable
    @Override
    public EncSettingState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull EncSettingState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

}