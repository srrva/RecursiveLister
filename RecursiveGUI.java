import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Stack;

public class RecursiveGUI extends JFrame {
    private JTextArea resultTA;
    public RecursiveGUI() {
        JFrame frame = new JFrame();
        setTitle("Recursive File Listing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        JPanel btnPnl = new JPanel(new BorderLayout());
        JPanel directoryPnl = new JPanel(new BorderLayout());

        resultTA = new JTextArea();
        resultTA.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultTA);
        directoryPnl.add(scrollPane, BorderLayout.CENTER);

        JButton loadBtn = new JButton("Load Directory");
        loadBtn.addActionListener(e -> rdFile());
        btnPnl.add(loadBtn, BorderLayout.WEST);

        JButton quitBtn = new JButton("Quit");
        QuitListener listener = new QuitListener();
        quitBtn.addActionListener(listener);
        btnPnl.add(quitBtn, BorderLayout.EAST);

        add(directoryPnl, BorderLayout.CENTER);
        add(btnPnl, BorderLayout.SOUTH);

    }

    private void rdFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedDirectory = chooser.getSelectedFile();
            listFiles(selectedDirectory);
        } else {
            System.out.println("Directory selection incomplete..");
        }
    }

    private void listFiles(File directory) {
        Stack<File> stack = new Stack<>();
        stack.push(directory);

        StringBuilder fileList = new StringBuilder();

        while (!stack.empty()) {
            File tmpFile = stack.pop();
            if (tmpFile.isFile()) {
                fileList.append(tmpFile.getName()).append("\n");
            } else if (tmpFile.isDirectory()) {
                File[] files = tmpFile.listFiles();
                if (files != null) {
                    for (File file : files) {
                        stack.push(file);
                    }
                }
            }
        }

        resultTA.setText(fileList.toString());
    }
}
