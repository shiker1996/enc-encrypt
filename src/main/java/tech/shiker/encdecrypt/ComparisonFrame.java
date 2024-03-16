package tech.shiker.encdecrypt;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ComparisonFrame extends JFrame {

    public ComparisonFrame(VirtualFile originalFile, VirtualFile decryptedFile) throws IOException {
        super(originalFile.getName());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);

        // 创建原始文件的文本区域和滚动面板
        JEditorPane originalArea = new JEditorPane();
        originalArea.setContentType(DecryptConstant.CONTENT_TYPE);
        originalArea.setText(new String(originalFile.contentsToByteArray()));
        JBScrollPane originalScrollPane = new JBScrollPane(originalArea);

        // 创建解密文件的文本区域和滚动面板
        JEditorPane decryptedArea = new JEditorPane();
        decryptedArea.setContentType(DecryptConstant.CONTENT_TYPE);
        decryptedArea.setText(new String(decryptedFile.contentsToByteArray()));
        JBScrollPane decryptedScrollPane = new JBScrollPane(decryptedArea);

        // 创建标题字体
        Font titleFont = new Font(Font.SANS_SERIF, Font.BOLD, 16);

        // 创建标题面板并添加标题
        JPanel originalTitlePanel = new JPanel(new BorderLayout());
        JLabel originalTitleLabel = new JLabel(DecryptConstant.ORIGINAL_FILE_TITLE);
        originalTitleLabel.setFont(titleFont);
        originalTitlePanel.add(originalTitleLabel, BorderLayout.NORTH);
        originalTitlePanel.add(originalScrollPane, BorderLayout.CENTER);

        JPanel decryptedTitlePanel = new JPanel(new BorderLayout());
        JLabel decryptedTitleLabel = new JLabel(DecryptConstant.DECRYPT_FILE_TITLE);
        decryptedTitleLabel.setFont(titleFont);
        decryptedTitlePanel.add(decryptedTitleLabel, BorderLayout.NORTH);
        decryptedTitlePanel.add(decryptedScrollPane, BorderLayout.CENTER);

        // 创建内容面板
        JPanel contentPanel = new JPanel(new GridLayout(1, 2));
        contentPanel.add(originalTitlePanel);
        contentPanel.add(decryptedTitlePanel);

        // 将内容面板添加到主面板
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
