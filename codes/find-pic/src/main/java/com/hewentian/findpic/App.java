package com.hewentian.findpic;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class App {
    public static void main(String[] args) {
        String chooseNumTip = "多个编号之间，以英文空格分隔";

        Label picNumLabel = new Label("要筛选的编号: ");
        TextArea textArea = new TextArea(chooseNumTip, 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);

        Panel northPanel = new Panel();
        northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        northPanel.add(picNumLabel);
        northPanel.add(textArea);


        Label sourceDirLabel = new Label("源文件夹:       ");
        TextField sourceDirTextField = new TextField("", 40);
        sourceDirTextField.setEnabled(false);
        Button sourceDirButton = new Button("选择");

        Panel sourceDirPanel = new Panel();
        sourceDirPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        sourceDirPanel.add(sourceDirLabel);
        sourceDirPanel.add(sourceDirTextField);
        sourceDirPanel.add(sourceDirButton);

        sourceDirButton.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            FileSystemView fileSystemView = FileSystemView.getFileSystemView();
            fileChooser.setCurrentDirectory(fileSystemView.getHomeDirectory());
            fileChooser.setDialogTitle("选择文件夹");
            fileChooser.setApproveButtonText("确定");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int result = fileChooser.showOpenDialog(sourceDirPanel);
            if (JFileChooser.APPROVE_OPTION == result) {
                String path = fileChooser.getSelectedFile().getPath();
                sourceDirTextField.setText(path);
            }
        });


        Label targetDirLabel = new Label("目标文件夹:    ");
        TextField targetDirTextField = new TextField("", 40);
        targetDirTextField.setEnabled(false);
        Button targetDirButton = new Button("选择");

        Panel targetDirPanel = new Panel();
        targetDirPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        targetDirPanel.add(targetDirLabel);
        targetDirPanel.add(targetDirTextField);
        targetDirPanel.add(targetDirButton);

        targetDirButton.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            FileSystemView fileSystemView = FileSystemView.getFileSystemView();
            fileChooser.setCurrentDirectory(fileSystemView.getHomeDirectory());
            fileChooser.setDialogTitle("选择文件夹");
            fileChooser.setApproveButtonText("确定");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int result = fileChooser.showOpenDialog(targetDirPanel);
            if (JFileChooser.APPROVE_OPTION == result) {
                String path = fileChooser.getSelectedFile().getPath();
                targetDirTextField.setText(path);
            }
        });


        Panel centerPanel = new Panel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(sourceDirPanel, BorderLayout.NORTH);
        centerPanel.add(targetDirPanel, BorderLayout.CENTER);

        Button startButton = new Button("开始");
        Panel southPanel = new Panel();
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        southPanel.add(startButton);


        Frame frame = new Frame("筛选图片");

        startButton.addActionListener((ActionEvent e) -> {
            String fileNamesStr = textArea.getText().trim();
            if (chooseNumTip.equals(fileNamesStr) || "".equals(fileNamesStr)) {
                JOptionPane.showMessageDialog(frame, "请输入 要筛选的编号");
                return;
            }

            String sourceDir = sourceDirTextField.getText();
            if ("".equals(sourceDir)) {
                JOptionPane.showMessageDialog(frame, "请选择 源文件夹");
                return;
            }

            String targetDir = targetDirTextField.getText();
            if ("".equals(targetDir)) {
                JOptionPane.showMessageDialog(frame, "请选择 目标文件夹");
                return;
            }

            Set<String> fileNames = new HashSet<>();
            for (String fileName : fileNamesStr.split("\\s+")) {
                fileNames.add(fileName);
            }

            File sourceDirFile = new File(sourceDir);
            File[] files = sourceDirFile.listFiles();

            int successCopyCount = 0;

            for (File file : files) {
                String fileName = file.getName();
                if (!file.isFile()) {
                    continue;
                }

                if (fileNames.contains(fileName.substring(0, fileName.lastIndexOf('.')))) {
                    File destFile = new File(targetDir, fileName);

                    if (copy(file, destFile)) {
                        successCopyCount++;
                    }
                }
            }

            JOptionPane.showMessageDialog(frame, "成功复制了 " + successCopyCount + " 个文件");
        });


        final AboutDialog aboutDialog = new AboutDialog(frame);

        MenuItem exitMenuItem = new MenuItem("退出");
        MenuItem aboutMenuItem = new MenuItem("关于");

        exitMenuItem.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

        aboutMenuItem.addActionListener((ActionEvent e) -> {
            aboutDialog.setVisible(true); // pop up dialog
        });

        Menu fileMenu = new Menu("文件");
        Menu helpMenu = new Menu("帮助");

        fileMenu.add(exitMenuItem);
        helpMenu.add(aboutMenuItem);

        MenuBar menuBar = new MenuBar();
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);


        frame.setSize(600, 340);
        frame.setResizable(false);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();

        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }

        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

        frame.setMenuBar(menuBar);

        frame.setLayout(new BorderLayout());
        frame.add(northPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(southPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    static class AboutDialog extends Dialog {
        public AboutDialog(Frame owner) {
            super(owner, "关于", true);

            add(new Label("如果要增加新功能，请联系 陈幸"), BorderLayout.CENTER);

            Button ok = new Button("确定");
            ok.addActionListener((ActionEvent event) -> {
                setVisible(false);
            });

            Panel panel = new Panel();
            panel.add(ok);
            add(panel, BorderLayout.SOUTH);

            setSize(180, 100);

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension dialogSize = getSize();

            if (dialogSize.width > screenSize.width) {
                dialogSize.width = screenSize.width;
            }
            if (dialogSize.height > screenSize.height) {
                dialogSize.height = screenSize.height;
            }

            setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);
        }
    }

    private static boolean copy(File srcFile, File destFile) {
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);

            int bytesRead;
            byte[] buf = new byte[1 * 1024];

            while ((bytesRead = fis.read(buf)) != -1) {
                fos.write(buf, 0, bytesRead);
            }

            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                fos.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }
}
