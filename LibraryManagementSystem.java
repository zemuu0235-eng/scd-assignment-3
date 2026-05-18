
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

// --- Custom Exception Classes ---
class EmptyFieldException extends Exception {
    public EmptyFieldException(String msg) { super(msg); }
}
class InvalidRollNumberException extends Exception {
    public InvalidRollNumberException(String msg) { super(msg); }
}
class InvalidDateException extends Exception {
    public InvalidDateException(String msg) { super(msg); }
}
class NullSelectionException extends Exception {
    public NullSelectionException(String msg) { super(msg); }
}

public class LibraryManagementSystem extends JFrame {
    // Components
    private JTextField txtName, txtRoll, txtTitle, txtIssueDate, txtReturnDate;
    private JTextArea txtRemarks;
    private JComboBox<String> comboCategory;
    private JRadioButton rbNew, rbOld;
    private ButtonGroup typeGroup;

    public LibraryManagementSystem() {
        // Frame Settings
        setTitle("Library Management System");
        setSize(700, 850);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main background panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(225, 235, 245)); // Light blueish background

        // White Container Card
        JPanel container = new JPanel();
        container.setBounds(50, 40, 600, 720);
        container.setBackground(Color.WHITE);
        container.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        container.setLayout(null);

        // Title
        JLabel lblHeading = new JLabel("Library Management System", SwingConstants.CENTER);
        lblHeading.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblHeading.setForeground(new Color(44, 62, 80));
        lblHeading.setBounds(0, 30, 600, 50);
        container.add(lblHeading);

        // Form Fields
        addFormLabel(container, "Student Name", 120);
        txtName = new JTextField();
        txtName.setBounds(250, 120, 300, 35);
        container.add(txtName);

        addFormLabel(container, "Roll Number", 180);
        txtRoll = new JTextField();
        txtRoll.setBounds(250, 180, 300, 35);
        container.add(txtRoll);

        addFormLabel(container, "Book Title", 240);
        txtTitle = new JTextField();
        txtTitle.setBounds(250, 240, 300, 35);
        container.add(txtTitle);

        addFormLabel(container, "Book Category", 300);
        String[] categories = {"Select Category", "Programming", "Mathematics", "Science", "History"};
        comboCategory = new JComboBox<>(categories);
        comboCategory.setBounds(250, 300, 300, 35);
        container.add(comboCategory);

        addFormLabel(container, "Book Type", 360);
        rbNew = new JRadioButton("New Edition");
        rbOld = new JRadioButton("Old Edition");
        rbNew.setBounds(250, 360, 120, 30);
        rbOld.setBounds(400, 360, 120, 30);
        rbNew.setBackground(Color.WHITE);
        rbOld.setBackground(Color.WHITE);
        typeGroup = new ButtonGroup();
        typeGroup.add(rbNew);
        typeGroup.add(rbOld);
        container.add(rbNew);
        container.add(rbOld);

        addFormLabel(container, "Issue Date", 420);
        txtIssueDate = new JTextField();
        txtIssueDate.setBounds(250, 420, 300, 35);
        container.add(txtIssueDate);

        addFormLabel(container, "Return Date", 480);
        txtReturnDate = new JTextField();
        txtReturnDate.setBounds(250, 480, 300, 35);
        container.add(txtReturnDate);

        addFormLabel(container, "Remarks", 540);
        txtRemarks = new JTextArea();
        txtRemarks.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        txtRemarks.setBounds(250, 540, 300, 80);
        container.add(txtRemarks);

        // --- Buttons with Updated Colors ---

        // Issue Button (Green)
        JButton btnIssue = new JButton("Issue Book");
        btnIssue.setBounds(50, 650, 150, 45);
        btnIssue.setBackground(new Color(40, 167, 69));
        btnIssue.setForeground(Color.WHITE);
        btnIssue.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnIssue.addActionListener(e -> handleIssue());

        // Reset Button (Yellow/Orange)
        JButton btnReset = new JButton("Reset");
        btnReset.setBounds(225, 650, 150, 45);
        btnReset.setBackground(new Color(255, 193, 7));
        btnReset.setForeground(Color.WHITE);
        btnReset.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnReset.addActionListener(e -> resetFields());

        // Exit Button (Red)
        JButton btnExit = new JButton("Exit");
        btnExit.setBounds(400, 650, 150, 45);
        btnExit.setBackground(new Color(220, 53, 69));
        btnExit.setForeground(Color.WHITE);
        btnExit.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnExit.addActionListener(e -> System.exit(0));

        container.add(btnIssue);
        container.add(btnReset);
        container.add(btnExit);

        mainPanel.add(container);
        add(mainPanel);
    }

    private void addFormLabel(JPanel p, String text, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label.setBounds(50, y, 150, 30);
        p.add(label);
    }

    private void handleIssue() {
        try {
            // 1. EmptyFieldException
            if (txtName.getText().trim().isEmpty() || txtRoll.getText().trim().isEmpty() || txtTitle.getText().trim().isEmpty()) {
                throw new EmptyFieldException("Required fields cannot be empty!");
            }

            // 2. InvalidRollNumberException
            if (!txtRoll.getText().matches("\\d+")) {
                throw new InvalidRollNumberException("Roll Number must contain only digits!");
            }

            // 5. NumberFormatException (triggered if Roll Number fails conversion)
            int rollNum = Integer.parseInt(txtRoll.getText());

            // 4. NullSelectionException
            if (comboCategory.getSelectedIndex() == 0 || (!rbNew.isSelected() && !rbOld.isSelected())) {
                throw new NullSelectionException("Please select Book Category and Book Type!");
            }

            // 6. DateTimeParseException
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate issueDate, returnDate;
            try {
                issueDate = LocalDate.parse(txtIssueDate.getText(), dtf);
                returnDate = LocalDate.parse(txtReturnDate.getText(), dtf);
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, "Invalid Date Format! Use YYYY-MM-DD", "Date Format Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 3. InvalidDateException
            if (returnDate.isBefore(issueDate)) {
                throw new InvalidDateException("Return date cannot be earlier than issue date!");
            }

            // Success Output (Image 2 logic)
            String result = String.format(
                    "Book Issued Successfully!\n\nStudent Name: %s\nRoll Number: %d\nBook Title: %s\nCategory: %s\nBook Type: %s\nIssue Date: %s\nReturn Date: %s\nRemarks: %s",
                    txtName.getText(), rollNum, txtTitle.getText(), comboCategory.getSelectedItem(),
                    rbNew.isSelected() ? "New Edition" : "Old Edition", issueDate, returnDate, txtRemarks.getText()
            );
            JOptionPane.showMessageDialog(this, result, "Message", JOptionPane.INFORMATION_MESSAGE);

        } catch (EmptyFieldException | InvalidRollNumberException | InvalidDateException | NullSelectionException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.WARNING_MESSAGE);
        } finally {
            // 7. finally block
            System.out.println("Operation Completed!");
        }
    }

    private void resetFields() {
        txtName.setText("");
        txtRoll.setText("");
        txtTitle.setText("");
        txtIssueDate.setText("");
        txtReturnDate.setText("");
        txtRemarks.setText("");
        comboCategory.setSelectedIndex(0);
        typeGroup.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LibraryManagementSystem().setVisible(true);
        });
    }
}