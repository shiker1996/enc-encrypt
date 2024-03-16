package tech.shiker.enccore;

import com.intellij.openapi.util.NlsSafe;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DecryptedVirtualFile extends VirtualFile {
    private final VirtualFile originalFile;

    private final byte[] content;

    public DecryptedVirtualFile(VirtualFile originalFile, String content) {
        this.originalFile = originalFile;
        this.content = content.getBytes();
    }

    @Override
    public @NotNull @NlsSafe String getName() {
        return originalFile.getName();
    }

    @Override
    public @NotNull VirtualFileSystem getFileSystem() {
        return originalFile.getFileSystem();
    }

    @Override
    public @NonNls @NotNull String getPath() {
        return originalFile.getPath();
    }

    @Override
    public boolean isWritable() {
        return false;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public VirtualFile getParent() {
        return null;
    }

    @Override
    public VirtualFile[] getChildren() {
        return new VirtualFile[0];
    }

    @Override
    public @NotNull OutputStream getOutputStream(Object o, long l, long l1) throws IOException {
        return originalFile.getOutputStream(o, l, l1);
    }

    @Override
    public byte @NotNull [] contentsToByteArray() throws IOException {
        return content;
    }

    @Override
    public long getTimeStamp() {
        return 0;
    }

    @Override
    public long getLength() {
        return 0;
    }

    @Override
    public void refresh(boolean b, boolean b1, @Nullable Runnable runnable) {

    }

    @Override
    public @NotNull InputStream getInputStream() throws IOException {
        return originalFile.getInputStream();
    }
}