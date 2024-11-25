import java.io.*;
import java.util.*;

public class StudentManager {
    private static final String FILE_NAME = "students.csv";
    private List<Student> students;

    public StudentManager() {
        students = new ArrayList<>();
        loadStudentsFromFile();
    }

    public void addStudent(Student student) {
        students.add(student);
        saveStudentsToFile();
    }

    public void updateStudent(int index, Student student) {
        students.set(index, student);
        saveStudentsToFile();
    }

    public void deleteStudent(int index) {
        students.remove(index);
        saveStudentsToFile();
    }

    public List<Student> getAllStudents() {
        return students;
    }

    private void saveStudentsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Student student : students) {
                writer.write(student.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStudentsFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();  // Create the file if it doesn't exist
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;  // Exit if file doesn't exist, so we don't attempt to read it
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                students.add(new Student(data[0], Integer.parseInt(data[1]), data[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
