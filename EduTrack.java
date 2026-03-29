import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// ==============================
// ReportCard Genie with Letter Grades
// ==============================

class Student {
    private String name;
    private String rollNumber;
    private HashMap<String, Double> grades;

    public Student(String name, String rollNumber) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grades = new HashMap<>();
    }

    public void addGrade(String course, double grade) {
        grades.put(course, grade);
    }

    public double calculateGPA() {
        if (grades.isEmpty()) return 0;
        double total = 0;
        for (double grade : grades.values()) {
            total += grade;
        }
        return total / grades.size();
    }

    // Convert numeric grade to letter grade
    public String getLetterGrade(double grade) {
        if (grade >= 90) return "A";
        else if (grade >= 80) return "B";
        else if (grade >= 70) return "C";
        else if (grade >= 60) return "D";
        else return "F";
    }

    public void displayReport() {
        System.out.println("\nReport Card for " + name + " (Roll No: " + rollNumber + ")");
        System.out.println("----------------------------------------");
        if (grades.isEmpty()) {
            System.out.println("No grades available.");
        } else {
            for (Map.Entry<String, Double> entry : grades.entrySet()) {
                String letter = getLetterGrade(entry.getValue());
                System.out.println(entry.getKey() + ": " + entry.getValue() + " (" + letter + ")");
            }
            double gpa = calculateGPA();
            String gpaLetter = getLetterGrade(gpa);
            System.out.printf("GPA: %.2f (%s)\n", gpa, gpaLetter);
        }
        System.out.println("----------------------------------------");
    }

    public String getRollNumber() {
        return rollNumber;
    }
}

class GradingSystem {
    private ArrayList<Student> students;
    private Scanner scanner;

    public GradingSystem() {
        students = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public Student findStudent(String rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber().equals(rollNumber)) {
                return student;
            }
        }
        return null;
    }

    public void generateReports() {
        if (students.isEmpty()) {
            System.out.println("No students in the system.");
            return;
        }
        for (Student student : students) {
            student.displayReport();
        }
    }

    public void menu() {
        while (true) {
            System.out.println("\n==== ReportCard Genie ====");
            System.out.println("1. Add Student");
            System.out.println("2. Add Grade for Student");
            System.out.println("3. Display Student Report");
            System.out.println("4. Display All Reports");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter roll number: ");
                    String roll = scanner.nextLine();
                    Student student = new Student(name, roll);
                    addStudent(student);
                    System.out.println("Student " + name + " added successfully.");
                    break;

                case "2":
                    System.out.print("Enter roll number of student: ");
                    roll = scanner.nextLine();
                    student = findStudent(roll);
                    if (student != null) {
                        System.out.print("Enter course name: ");
                        String course = scanner.nextLine();
                        double grade = -1;
                        while (grade < 0 || grade > 100) {
                            System.out.print("Enter grade (0-100): ");
                            try {
                                grade = Double.parseDouble(scanner.nextLine());
                                if (grade < 0 || grade > 100) {
                                    System.out.println("Grade must be between 0 and 100.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Enter a number.");
                            }
                        }
                        student.addGrade(course, grade);
                        System.out.println("Grade added for " + student.getRollNumber());
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case "3":
                    System.out.print("Enter roll number of student: ");
                    roll = scanner.nextLine();
                    student = findStudent(roll);
                    if (student != null) {
                        student.displayReport();
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case "4":
                    generateReports();
                    break;

                case "5":
                    System.out.println("Exiting program. Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}

// Main class for online compiler
class Main {
    public static void main(String[] args) {
        GradingSystem system = new GradingSystem();
        system.menu();
    }
}