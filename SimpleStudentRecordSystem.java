import java.io.*;
import java.util.*;

public class SimpleStudentRecordSystem {

    // --- Read students from CSV ---
    static List<String[]> ReadCSVStudents() {
        String csvFile = "student_records.csv"; 
        List<String[]> studentData = new ArrayList<>();
        String line;
        String splitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(splitBy);
                studentData.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return studentData;
    }

    // --- Add a new line to CSV ---
    static void AddLineStudent(String[] line) {
        String csvFile = "student_records.csv";

        try (FileWriter writer = new FileWriter(csvFile, true)) {
            writer.write(String.join(",", line) + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- Search students ---
    static void SearchStudent(List<String[]> studentData) {
        Scanner input = new Scanner(System.in);
        System.out.println("Which option do you want to search? (Please enter the number)");
        String[] options = {"ID", "Name", "Faculty", "Department", "Semester", "GPA", "Email", "Advisor"};

        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + " - " + options[i]);
        }

        int choiceNum = Integer.parseInt(input.nextLine()) - 1;
        System.out.println("You have chosen: " + options[choiceNum] + ". Please enter your search: ");
        String searchWord = input.nextLine().trim().toLowerCase();

        // Print header
        for (String o : options) {
            System.out.print(o + "\t");
        }
        System.out.println();

        // Print matching rows
        for (String[] s : studentData) {
            if (s[choiceNum].toLowerCase().equals(searchWord)) {
                for (String st : s) {
                    System.out.print(st + "\t");
                }
                System.out.println();
            }
        }
    }

    // --- Add new student interactively ---
    static void AddStudent() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter following information:");

        System.out.print("ID: ");
        String id = input.nextLine();
        System.out.print("Name: ");
        String name = input.nextLine();
        System.out.print("Faculty: ");
        String faculty = input.nextLine();
        System.out.print("Department: ");
        String department = input.nextLine();
        System.out.print("Semester: ");
        String semester = input.nextLine();
        System.out.print("GPA: ");
        String GPA = input.nextLine();
        System.out.print("E-mail: ");
        String email = input.nextLine();
        System.out.print("Advisor: ");
        String advisor = input.nextLine();

        String[] studentData = {id, name, faculty, department, semester, GPA, email, advisor};
        AddLineStudent(studentData);
    }

    // --- Edit student details ---
    static void EditDetails(List<String[]> studentData) {
        Scanner input = new Scanner(System.in);
        System.out.println("Do you want to search student ID first? (1 - Yes, 0 - No)");
        String search = input.nextLine();
        if (search.equals("1")) {
            SearchStudent(studentData);
        }

        System.out.print("Please enter student's ID: ");
        String ID = input.nextLine().trim().toLowerCase();

        String[] studentToEdit = null;
        for (String[] s : studentData) {
            if (s[0].toLowerCase().equals(ID)) {
                studentToEdit = s;
                break;
            }
        }

        if (studentToEdit == null) {
            System.out.println("Student not found!");
            return;
        }

        System.out.println("Editing student: " + Arrays.toString(studentToEdit));
        System.out.println("Leave field empty to keep current value.");

        System.out.print("Name (" + studentToEdit[1] + "): ");
        String name = input.nextLine();
        if (!name.isEmpty()) studentToEdit[1] = name;

        System.out.print("Faculty (" + studentToEdit[2] + "): ");
        String faculty = input.nextLine();
        if (!faculty.isEmpty()) studentToEdit[2] = faculty;

        System.out.print("Department (" + studentToEdit[3] + "): ");
        String department = input.nextLine();
        if (!department.isEmpty()) studentToEdit[3] = department;

        System.out.print("Semester (" + studentToEdit[4] + "): ");
        String semester = input.nextLine();
        if (!semester.isEmpty()) studentToEdit[4] = semester;

        System.out.print("GPA (" + studentToEdit[5] + "): ");
        String GPA = input.nextLine();
        if (!GPA.isEmpty()) studentToEdit[5] = GPA;

        System.out.print("E-mail (" + studentToEdit[6] + "): ");
        String email = input.nextLine();
        if (!email.isEmpty()) studentToEdit[6] = email;

        System.out.print("Advisor (" + studentToEdit[7] + "): ");
        String advisor = input.nextLine();
        if (!advisor.isEmpty()) studentToEdit[7] = advisor;

        // Rewrite file with updated data
        try (FileWriter writer = new FileWriter("student_records.csv", false)) {
            for (String[] s : studentData) {
                writer.write(String.join(",", s) + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Student details updated successfully!");
    }

    // --- Main Menu ---
    public static void main(String[] args) {
        List<String[]> studentData = ReadCSVStudents();
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Student Record System ---");
            System.out.println("1. Display All Students");
            System.out.println("2. Search Student");
            System.out.println("3. Add Student");
            System.out.println("4. Edit Student");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            String choice = input.nextLine();
            switch (choice) {
                case "1":
                    for (String[] s : studentData) {
                        System.out.println(Arrays.toString(s));
                    }
                    break;
                case "2":
                    SearchStudent(studentData);
                    break;
                case "3":
                    AddStudent();
                    studentData = ReadCSVStudents(); // reload
                    break;
                case "4":
                    EditDetails(studentData);
                    studentData = ReadCSVStudents(); // reload
                    break;
                case "5":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
