import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
public class DataStreamSearchApp extends JFrame {
    private JTextArea originalTextArea;
    private JTextArea filteredTextArea;
    private JTextField searchTextField;

    public DataStreamSearchApp() {
        setTitle("DataStreams File Search");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        originalTextArea = new JTextArea(20, 40);
        filteredTextArea = new JTextArea(20, 40);
        searchTextField = new JTextField(20);
        JButton loadButton = new JButton("Load File");
        JButton searchButton = new JButton("Search");
        JButton quitButton = new JButton("Quit");

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFile();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchFile();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Arrange the components on the GUI
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.add(new JScrollPane(originalTextArea));
        mainPanel.add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel();
        rightPanel.add(new JScrollPane(filteredTextArea));
        mainPanel.add(rightPanel, BorderLayout.EAST);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(searchTextField);
        bottomPanel.add(loadButton);
        bottomPanel.add(searchButton);
        bottomPanel.add(quitButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setVisible(true);
    }

    private void loadFile() {
        // Use JFileChooser to select and load a text file
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            Path filePath = fileChooser.getSelectedFile().toPath();

            try {
                Stream<String> lines = Files.lines(filePath);
                originalTextArea.setText("");
                lines.forEach(line -> originalTextArea.append(line + "\n"));
                lines.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void searchFile() {
        // Read the search string from the text field
        String searchString = searchTextField.getText();

        if (searchString.isEmpty()) {
            return;
        }

        // Filter the original text using a Stream and lambda expression
        String originalText = originalTextArea.getText();
        String[] lines = originalText.split("\n");

        Stream<String> filteredLines = Stream.of(lines).filter(line -> line.contains(searchString));
        filteredTextArea.setText("");
        filteredLines.forEach(line -> filteredTextArea.append(line + "\n"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DataStreamSearchApp();
        });
    }
}
