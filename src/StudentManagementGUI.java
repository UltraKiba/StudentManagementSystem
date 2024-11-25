import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class StudentManagementGUI {
    private JFrame frame;
    private JTable studentTable;
    private JButton addButton, updateButton, deleteButton;
    private JTextField nameField, ageField, gradeField;
    private StudentManager studentManager;

    public StudentManagementGUI() {
        studentManager = new StudentManager();
        frame = new JFrame("Student Management System");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Table to show students
        String[] columns = {"Name", "Age", "Grade"};
        studentTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(studentTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Input fields and buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);
        panel.add(new JLabel("Age:"));
        ageField = new JTextField();
        panel.add(ageField);
        panel.add(new JLabel("Grade:"));
        gradeField = new JTextField();
        panel.add(gradeField);
        addButton = new JButton("Add Student");
        panel.add(addButton);
        updateButton = new JButton("Update Student");
        panel.add(updateButton);
        deleteButton = new JButton("Delete Student");
        panel.add(deleteButton);

        frame.add(panel, BorderLayout.SOUTH);

        // Add action listeners
      addButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        String ageText = ageField.getText();
        String grade = gradeField.getText();

        // Validate age input
        try {
            int age = Integer.parseInt(ageText);  // Try parsing the age
            studentManager.addStudent(new Student(name, age, grade));
            refreshTable();
        } catch (NumberFormatException ex) {
            // Show an error message if the age is not a valid number
            JOptionPane.showMessageDialog(frame, "Please enter a valid number for age.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            // Catch any other unexpected errors
            JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
});


        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int rowIndex = studentTable.getSelectedRow();
                if (rowIndex >= 0) {
                    String name = nameField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    String grade = gradeField.getText();
                    studentManager.updateStudent(rowIndex, new Student(name, age, grade));
                    refreshTable();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int rowIndex = studentTable.getSelectedRow();
                if (rowIndex >= 0) {
                    studentManager.deleteStudent(rowIndex);
                    refreshTable();
                }
            }
        });

        // Initial table refresh
        refreshTable();
    }

    public void show() {
        frame.setVisible(true);
    }

    private void refreshTable() {
        List<Student> students = studentManager.getAllStudents();
        String[][] data = new String[students.size()][3];
        for (int i = 0; i < students.size(); i++) {
            data[i][0] = students.get(i).getName();
            data[i][1] = String.valueOf(students.get(i).getAge());
            data[i][2] = students.get(i).getGrade();
        }
        studentTable.setModel(new javax.swing.table.DefaultTableModel(data, new String[]{"Name", "Age", "Grade"}));
    }

    // Main method to run the application
    public static void main(String[] args) {
        System.out.println("Program is starting...");  // Debugging message
        StudentManagementGUI gui = new StudentManagementGUI();
        gui.show();  // Show the GUI window
    }
}
