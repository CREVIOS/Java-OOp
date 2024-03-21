
import java.util.*;
import java.util.stream.Collectors;


abstract class Course {
    private String courseName;
    private double credits;

    public Course(String courseName, double credits) {
        this.courseName = courseName;
        this.credits = credits;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }
}


class ArtificialIntelligence extends Course {
    public ArtificialIntelligence() {
        super("Artificial Intelligence", 3.0);
    }
}

class Security extends Course {
    public Security() {
        super("Security", 3.0);
    }
}

class OperationResearch extends Course {
    public OperationResearch() {
        super("Operation Research", 3.0);
    }
}

class Networking extends Course {
    public Networking() {
        super("Networking", 3.0);
    }
}

class EmbeddedSystems extends Course {
    public EmbeddedSystems() {
        super("Embedded Systems", 3.0);
    }
}


interface IEvaluation {
    void setMarks(int marks);

    int getMarks();

    int getMaxMarks();
}

abstract class Evaluation implements IEvaluation {
    private int marks;
    private final int maxMarks;

    public Evaluation(int maxMarks) {
        this.maxMarks = maxMarks;
    }

    @Override
    public void setMarks(int marks) {
        this.marks = marks;
    }

    @Override
    public int getMarks() {
        return this.marks;
    }

    @Override
    public int getMaxMarks() {
        return this.maxMarks;
    }
}

class Midterm extends Evaluation {
    public Midterm(int maxMarks) {
        super(maxMarks);
    }
}

class RegularAssessment extends Evaluation {
    public RegularAssessment(int maxMarks) {
        super(maxMarks);
    }
}

class Final extends Evaluation {
    public Final(int maxMarks) {
        super(maxMarks);
    }
}


class Student {
    private String name;
    private String roll;
    private String email;
    private List<Course> majorCourses;
    private Course optionalCourse;
    private Map<Course, List<IEvaluation>> evaluations;

    public Student(String name, String roll, String email) {
        this.name = name;
        this.roll = roll;
        this.email = email;
        this.majorCourses = new ArrayList<>();
        this.evaluations = new HashMap<>();
    }

    public void addMajorCourse(Course course) {
        if (this.majorCourses.size() < 3) {
            this.majorCourses.add(course);
        } else {
            System.out.println("Already enrolled in 3 major courses.");
        }
    }

    public void setOptionalCourse(Course course) {
        this.optionalCourse = course;
        course.setCredits(1.5);
    }

    public void addEvaluation(Course course, IEvaluation evaluation) {
        this.evaluations.computeIfAbsent(course, k -> new ArrayList<>()).add(evaluation);
    }

    public String calculateGradeForCourse(Course course) {
        List<IEvaluation> courseEvaluations = this.evaluations.getOrDefault(course, new ArrayList<>());
        int totalMarks = courseEvaluations.stream().mapToInt(IEvaluation::getMarks).sum();
        int maxPossibleMarks = courseEvaluations.stream().mapToInt(IEvaluation::getMaxMarks).sum();
        double percentage = ((double) totalMarks / maxPossibleMarks) * 100;
        return getGradeFromPercentage(percentage);
    }

    private String getGradeFromPercentage(double percentage) {
        if (percentage >= 80) return "A+";
        else if (percentage >= 75) return "A";
        else if (percentage >= 70) return "A-";
        else if (percentage >= 65) return "B+";
        else if (percentage >= 60) return "B";
        else if (percentage >= 55) return "B-";
        else if (percentage >= 50) return "C+";
        else if (percentage >= 45) return "C";
        else if (percentage >= 40) return "D";
        else return "F";
    }

    private double getNumericGrade(double percentage) {
        if (percentage >= 80) return 4.00;
        else if (percentage >= 75) return 3.75;
        else if (percentage >= 70) return 3.50;
        else if (percentage >= 65) return 3.25;
        else if (percentage >= 60) return 3.00;
        else if (percentage >= 55) return 2.75;
        else if (percentage >= 50) return 2.50;
        else if (percentage >= 45) return 2.25;
        else if (percentage >= 40) return 2.00;
        else return 0.00;
    }

    public double calculateGPA() {
        double totalPoints = 0.0;
        double totalCredits = 0.0;

        List<Course> allCourses = new ArrayList<>(majorCourses);
        if (optionalCourse != null) allCourses.add(optionalCourse);

        for (Course course : allCourses) {
            List<IEvaluation> courseEvaluations = evaluations.getOrDefault(course, new ArrayList<>());
            int totalMarks = courseEvaluations.stream().mapToInt(IEvaluation::getMarks).sum();
            int maxPossibleMarks = courseEvaluations.stream().mapToInt(IEvaluation::getMaxMarks).sum();
            double percentage = ((double) totalMarks / maxPossibleMarks) * 100;

            double numericGrade = getNumericGrade(percentage);
            totalPoints += numericGrade * course.getCredits();
            totalCredits += course.getCredits();
        }

        return totalCredits > 0 ? totalPoints / totalCredits : 0.0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Course> getMajorCourses() {
        return majorCourses;
    }

    public Course getOptionalCourse() {
        return optionalCourse;
    }

    public Map<Course, List<IEvaluation>> getEvaluations() {
        return evaluations;
    }

    private double getNumericGrade(String grade) {
        switch (grade) {
            case "A+": return 4.00;
            case "A": return 3.75;
            case "A-": return 3.50;
            case "B+": return 3.25;
            case "B": return 3.00;
            case "B-": return 2.75;
            case "C+": return 2.50;
            case "C": return 2.25;
            case "D": return 2.00;
            default: return 0.00;
        }
    }




    public void addCustomEvaluationPlan(Course course, List<IEvaluation> evaluations) {
        if (validateTotalMarks(evaluations)) {
            this.evaluations.put(course, evaluations);
        } else {
            System.out.println("Invalid evaluation plan for " + course.getCourseName() + ". Total marks must be 100.");
        }
    }


    private boolean validateTotalMarks(List<IEvaluation> evaluations) {
        int totalMarks = evaluations.stream().mapToInt(IEvaluation::getMaxMarks).sum();
        return totalMarks == 100;
    }


    public double calculateAverageMarksForCourse(Course course) {
        List<IEvaluation> evaluations = this.evaluations.get(course);
        if (evaluations == null || evaluations.isEmpty()) {
            return 0; // Or handle this case as you see fit
        }

        double totalMarks = 0;
        for (IEvaluation evaluation : evaluations) {
            totalMarks += evaluation.getMarks();
        }

        return totalMarks / evaluations.size();
    }



}

class EducationSystem {
    private List<Student> students = new ArrayList<>();
    private Map<String, Course> courses = new HashMap<>();


    public void addStudent(Student student) {
        students.add(student);
    }


    public void addCourse(Course course) {
        courses.put(course.getCourseName(), course);
    }


    public double calculateGPAForStudent(String roll) {
        for (Student student : students) {
            if (student.getRoll().equals(roll)) {
                return student.calculateGPA();
            }
        }
        return 0;
    }


    public List<Student> getOverallRankList() {
        return students.stream()
                .sorted((s1, s2) -> Double.compare(s2.calculateGPA(), s1.calculateGPA()))
                .collect(Collectors.toList());
    }


    public void displayStudentsGPA() {
        for (Student student : students) {
            System.out.println(student.getName() + " - GPA: " + student.calculateGPA());
        }
    }

    public Map<String, Course> getCourses() {
        return courses;
    }

    public Student getStudentByRoll(String roll) {
        for (Student student : students) {
            if (student.getRoll().equals(roll)) {
                return student;
            }
        }
        return null;
    }

    public void displayCourseBasedRankList(String courseName) {
        Course course = courses.get(courseName);
        if (course == null) {
            System.out.println("Course not found!");
            return;
        }

        // A map to hold average marks for each student in the specified course
        Map<Student, Double> studentMarks = new HashMap<>();

        for (Student student : students) {
            double averageMarks = student.calculateAverageMarksForCourse(course);
            studentMarks.put(student, averageMarks);
        }

        List<Map.Entry<Student, Double>> sortedStudents = studentMarks.entrySet()
                .stream()
                .sorted(Map.Entry.<Student, Double>comparingByValue().reversed())
                .collect(Collectors.toList());

        System.out.println("Rank list for course: " + courseName);
        int rank = 1;
        for (Map.Entry<Student, Double> entry : sortedStudents) {
            System.out.println("Rank " + rank++ + ": " + entry.getKey().getName() + " - Average Marks: " + entry.getValue());
        }
    }


}


public class Main {
    private static EducationSystem educationSystem = new EducationSystem();
    private static Scanner scanner = new Scanner(System.in);

    private static boolean validateTotalMarks(List<IEvaluation> evaluations) {
        int totalMarks = evaluations.stream().mapToInt(IEvaluation::getMaxMarks).sum();
        return totalMarks == 100;
    }

    public static void main(String[] args) {
        setupCourses();

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Display Students' GPA");
            System.out.println("2. Display Overall Rank List");
            System.out.println("3. Add Student");
            System.out.println("4. Add Marks for a Student");
            System.out.println("5. Display Detailed Student Information");
            System.out.println("6. Display Course-Based Rank List");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    educationSystem.displayStudentsGPA();
                    break;
                case 2:
                    List<Student> rankList = educationSystem.getOverallRankList();
                    System.out.println("Overall Rank List:");
                    rankList.forEach(student -> System.out.println(student.getName() + " - GPA: " + student.calculateGPA()));
                    break;
                case 3:
                    addStudentInteractively();
                    break;
                case 4:
                    addMarksForStudent();
                    break;
                case 5:
                    displayDetailedStudentInformation();
                    break;
                case 6:
                    System.out.println("Select a course to view its rank list:");
                    List<Course> courseList = new ArrayList<>(educationSystem.getCourses().values());
                    for (int i = 0; i < courseList.size(); i++) {
                        System.out.println((i + 1) + ". " + courseList.get(i).getCourseName());
                    }
                    System.out.print("Enter your choice: ");
                    int courseChoice = scanner.nextInt();
                    if (courseChoice < 1 || courseChoice > courseList.size()) {
                        System.out.println("Invalid choice, please try again.");
                    } else {
                        Course selectedCourse = courseList.get(courseChoice - 1);
                        educationSystem.displayCourseBasedRankList(selectedCourse.getCourseName());
                    }
                    break;
                case 7:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void setupCourses() {
        educationSystem.addCourse(new ArtificialIntelligence());
        educationSystem.addCourse(new Security());
        educationSystem.addCourse(new OperationResearch());
        educationSystem.addCourse(new Networking());
        educationSystem.addCourse(new EmbeddedSystems());
    }

    private static void addStudentInteractively() {
        scanner.nextLine(); // Consume newline left-over
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        System.out.print("Enter roll number: ");
        String roll = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        Student student = new Student(name, roll, email);

        System.out.println("Available Courses:");
        List<Course> availableCourses = new ArrayList<>(educationSystem.getCourses().values());
        for (int i = 0; i < availableCourses.size(); i++) {
            System.out.println((i + 1) + ". " + availableCourses.get(i).getCourseName());
        }

        System.out.println("Select 3 Major Courses (Enter numbers separated by space):");
        for (int i = 0; i < 3; i++) {
            int courseIndex = scanner.nextInt() - 1; // Adjusting for 0-based index
            student.addMajorCourse(availableCourses.get(courseIndex));
        }

        System.out.println("Select an Optional Course:");
        int optionalCourseIndex = scanner.nextInt() - 1; // Adjusting for 0-based index
        student.setOptionalCourse(availableCourses.get(optionalCourseIndex));

        System.out.println("\nSetting up evaluation schemes for Major Courses:");
        for (Course course : student.getMajorCourses()) {
            setCustomEvaluationSchemeForCourse(student, course);
        }

        System.out.println("\nSetting up evaluation scheme for Optional Course:");
        setCustomEvaluationSchemeForCourse(student, student.getOptionalCourse());

        educationSystem.addStudent(student);
        System.out.println("Student added successfully with custom evaluation plan.");
    }

    private static void setCustomEvaluationSchemeForCourse(Student student, Course course) {
        boolean validPlan = false;
        while (!validPlan) {
            List<IEvaluation> evaluations = new ArrayList<>();

            System.out.println("Select evaluation scheme for " + course.getCourseName() + ":");

            System.out.println("1. Midterm (Options: 30, 20, 40)");
            int midtermMaxMarks = selectEvaluationOption(Arrays.asList(30, 20, 40));
            evaluations.add(new Midterm(midtermMaxMarks));

            System.out.println("2. Regular Assessment (Options: 10, 5 + 5 from attendance)");
            int raMaxMarks = selectEvaluationOption(Arrays.asList(10, 10)); // 10 or 5+5
            evaluations.add(new RegularAssessment(raMaxMarks));

            System.out.println("3. Final (Options: 60, 70)");
            int finalMaxMarks = selectEvaluationOption(Arrays.asList(60, 70));
            evaluations.add(new Final(finalMaxMarks));

            if (validateTotalMarks(evaluations)) {
                student.addCustomEvaluationPlan(course, evaluations);
                validPlan = true;
                System.out.println("Evaluation plan set successfully.");
            } else {
                System.out.println("Invalid evaluation plan. Total marks must be 100. Please try again.");
            }
        }
    }

    private static int selectEvaluationOption(List<Integer> options) {
        int selection = scanner.nextInt();
        while (!options.contains(selection)) {
            System.out.println("Invalid option, please select again: ");
            selection = scanner.nextInt();
        }
        return selection;
    }


    private static void addMarksForStudent() {
        scanner.nextLine();
        System.out.print("Enter student's roll number: ");
        String roll = scanner.nextLine();
        Student student = educationSystem.getStudentByRoll(roll);

        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        System.out.println("Select a course for which to add marks:");
        List<Course> courses = new ArrayList<>();
        courses.addAll(student.getMajorCourses());
        courses.add(student.getOptionalCourse());

        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i + 1) + ". " + courses.get(i).getCourseName());
        }

        int courseIndex = scanner.nextInt() - 1;
        Course selectedCourse = courses.get(courseIndex);

        System.out.println("Enter marks for Midterm:");
        int midtermMarks = scanner.nextInt();

        System.out.println("Enter marks for Regular Assessment:");
        int regularAssessmentMarks = scanner.nextInt();

        System.out.println("Enter marks for Final:");
        int finalMarks = scanner.nextInt();

        List<IEvaluation> evaluations = student.getEvaluations().getOrDefault(selectedCourse, new ArrayList<>());
        evaluations.forEach(evaluation -> {
            if (evaluation instanceof Midterm) {
                evaluation.setMarks(midtermMarks);
            } else if (evaluation instanceof RegularAssessment) {
                evaluation.setMarks(regularAssessmentMarks);
            } else if (evaluation instanceof Final) {
                evaluation.setMarks(finalMarks);
            }
        });

        student.addCustomEvaluationPlan(selectedCourse, evaluations);
        System.out.println("Marks added successfully for " + selectedCourse.getCourseName());
    }

    private static void displayDetailedStudentInformation() {
        System.out.print("Enter student's roll number to view details: ");
        scanner.nextLine();
        String roll = scanner.nextLine();
        Student student = educationSystem.getStudentByRoll(roll);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        System.out.println("Student Name: " + student.getName());
        System.out.println("Roll Number: " + student.getRoll());
        System.out.println("Email: " + student.getEmail());

        System.out.println("Course Grades:");
        student.getMajorCourses().forEach(course -> {
            String grade = student.calculateGradeForCourse(course);
            System.out.println(course.getCourseName() + ": " + grade);
        });

        if (student.getOptionalCourse() != null) {
            String grade = student.calculateGradeForCourse(student.getOptionalCourse());
            System.out.println(student.getOptionalCourse().getCourseName() + " (Optional): " + grade);
        }

        System.out.println("Overall GPA: " + student.calculateGPA());
        displayRank(student);
    }

    private static void displayRank(Student student) {
        List<Student> rankList = educationSystem.getOverallRankList();
        int rank = rankList.indexOf(student) + 1;
        System.out.println("Rank: " + rank + "/" + rankList.size());
    }



}