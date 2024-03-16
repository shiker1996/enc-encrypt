package tech.shiker.enccore;

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
final class DecryptedSettingState implements PersistentStateComponent<DecryptedSettingState> {

    public String decryptedType = "AES";
    public String decryptedKey = "hi World Decrypt";
    public String decryptedInformation = "AES/ECB/PKCS5Padding";

    static DecryptedSettingState getInstance() {
        return ApplicationManager.getApplication().getService(DecryptedSettingState.class);
    }

    @Nullable
    @Override
    public DecryptedSettingState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull DecryptedSettingState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

}