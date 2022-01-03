import java.util.Scanner;

public class StudentMarking {

    public final String MENU_TEMPLATE =
            "%nWelcome to the Student System. Please enter an option 0 to 3%n"
                    + "0. Exit%n"
                    + "1. Generate a student ID%n"
                    + "2. Capture marks for students%n"
                    + "3. List student IDs and average mark%n";

    public final String NOT_FOUND_TEMPLATE = "No student id of %s exists";
    public final String ENTER_MARK_TEMPLATE = "Please enter mark %d for student %s%n";
    public final String STUDENT_ID_TEMPLATE = "Your studID is %s";
    public final String NAME_RESPONSE_TEMPLATE = "You entered a given name of %s and a surname of %s%n";
    public final String LOW_HIGH_TEMPLATE = "Student %s has a lowest mark of %d%nA highest mark of %d%n";
    public final String AVG_MARKS_TEMPLATE = "Student ***%s*** has an average of  %.2f%n";
    public final String COLUMN_1_TEMPLATE = "   *%n";
    public final String COLUMN_2_TEMPLATE = "   *           *%n";
    public final String CHART_KEY_TEMPLATE = "Highest     Lowest%n   %d           %d%n";
    public final String REPORT_PER_STUD_TEMPLATE = "| Student ID   %d is %s | Average is  %.2f |%n";

    /**
     * Creates a student ID based on user input
     *
     * @param sc Scanner reading {@link System#in} re-used from {@link StudentMarking#main(String[])}
     * @return a studentID according to the pattern specified in {@link StudentMarking#STUDENT_ID_TEMPLATE}
     */

    public String generateStudId(Scanner sc)
    {
        String studId;
        String firstName;
        String secondName;

        System.out.printf("Please enter your given name and surname (Enter 0 to return to main menu)%n");
        sc.nextLine();
        String studentName = String.valueOf(sc.nextLine());

        if (studentName.equals("0"))
        {
            return null;
        }

        String[] splitStr = studentName.trim().split("\\s+");

        firstName = splitStr[0];
        secondName = splitStr[1];

        int firstNameLength1 = firstName.length();
        int secondNameLength1 = secondName.length();
        String secondNameLength2; // second string for second name to add '0' to length if its single digits

        if (secondNameLength1 <= 9)
        {
            secondNameLength2 = "0" + secondNameLength1;
        }
        else
        {
            secondNameLength2 = secondNameLength1 + "";
        }

        int middleOfFirstName = firstNameLength1 / 2;
        int middleOfSecondName = secondNameLength1 / 2;

        studId = "" + Character.toUpperCase(firstName.charAt(0)) + Character.toUpperCase(secondName.charAt(0)) + secondNameLength2
                + firstName.charAt(middleOfFirstName) + secondName.charAt(middleOfSecondName);
        System.out.printf(NAME_RESPONSE_TEMPLATE, firstName, secondName);
        System.out.printf(STUDENT_ID_TEMPLATE, studId);
        return studId;
    }

    /**
     * Reads three marks (restricted to a floor and ceiling) for a student and returns their mean
     *
     * @param sc     Scanner reading {@link System#in} re-used from {@link StudentMarking#main(String[])}
     * @param studId a well-formed ID created by {@link StudentMarking#generateStudId(Scanner)}
     * @return the mean of the three marks entered for the student
     */

    public double captureMarks(Scanner sc, String studId) {

        final int MAX_MARK = 100;
        final int MIN_MARK = 0;
        int markOne, markTwo, markThree;
        String yesOrNo;

        while (true) {
            System.out.printf(ENTER_MARK_TEMPLATE, 1, studId);
            markOne = sc.nextInt();
            sc.nextLine();
            if (markOne < MIN_MARK || markOne > MAX_MARK) {
                continue;
            }
            break;
        }

        while (true) {
            System.out.printf(ENTER_MARK_TEMPLATE, 2, studId);
            markTwo = sc.nextInt();
            sc.nextLine();
            if (markTwo < MIN_MARK || markTwo > MAX_MARK) {
                continue;
            }
            break;
        }

        while (true) {
            System.out.printf(ENTER_MARK_TEMPLATE, 3, studId);
            markThree = sc.nextInt();
            sc.nextLine();
            if (markThree < MIN_MARK || markThree > MAX_MARK) {
                continue;
            }
            break;
        }

        int max = Math.max(markOne, Math.max(markTwo, markThree));
        int min = Math.min(markOne, Math.min(markTwo, markThree));
        double avgPreDivide = markOne + markTwo + markThree;
        double avg = avgPreDivide / 3.0;

        System.out.printf(LOW_HIGH_TEMPLATE, studId, min, max);
        System.out.printf(AVG_MARKS_TEMPLATE, studId, avg);

        while(true) {
            System.out.printf("Would you like to print a bar chart? [y/n]%n");
            yesOrNo = sc.nextLine();
            if (yesOrNo.equals("y"))
            {
                printBarChart(studId, max, min);
                return avg;
            }
            if (yesOrNo.equals("n"))
            {
                return avg;
            } else {
                System.out.printf("That input is incorrect%n");
                continue;
            }
        }
    }

    /**
     * outputs a simple character-based vertical bar chart with 2 columns
     *
     * @param studId a well-formed ID created by {@link StudentMarking#generateStudId(Scanner)}
     * @param high   a student's highest mark
     * @param low    a student's lowest mark
     */

    public void printBarChart(String studId, int high, int low) {

        int lcv = 0;

        System.out.printf("Student id statistics: %s%n", studId);

        while (lcv < high-low)
        {
            System.out.printf(COLUMN_1_TEMPLATE);
            lcv = lcv + 1;
        }
        lcv = 0;
        while (lcv < low)
        {
            System.out.printf(COLUMN_2_TEMPLATE);
            lcv = lcv + 1;
        }

        System.out.printf(CHART_KEY_TEMPLATE, high, low);
    }

    /**
     * Prints a specially formatted report, one line per student
     *
     * @param studList student IDs originally generated by {@link StudentMarking#generateStudId(Scanner)}
     * @param count    the total number of students in the system
     * @param avgArray mean (average) marks
     */

    public void reportPerStud(String[] studList, int count, double[] avgArray) {

        int lcv = 0;
        while (lcv < count) {
            System.out.printf(REPORT_PER_STUD_TEMPLATE, lcv + 1, studList[lcv], avgArray[lcv]);
            lcv = lcv + 1;
        }
    }

    /**
     * The main menu
     */

    public void displayMenu()
    {
        System.out.printf(MENU_TEMPLATE);
    }

    /**
     * The controlling logic of the program. Creates and re-uses a {@link Scanner} that reads from {@link System#in}.
     *
     * @param args Command-line parameters (ignored)
     */

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        StudentMarking sm = new StudentMarking();
        final int EXIT_CODE = 0;
        final int MAX_STUDENTS = 5;

        String[] keepStudId = new String[MAX_STUDENTS];
        double[] avgArray = new double[MAX_STUDENTS];

        int studentAmount = 0;
        int selection;

        do {
            sm.displayMenu();
            selection = sc.nextInt(); // scanner answer is saved to int variable 'selection'
            switch (selection) { // switch case decided by int 'selection' value
                case 0:
                    System.out.printf("Goodbye%n"); // end
                    break;

                case 1:
                    if(studentAmount != MAX_STUDENTS) // if statement runs (if student amount is not max students (5))
                    {
                        keepStudId[studentAmount] = sm.generateStudId(sc); /* output of generateStudId returned to keepStudId array
                        with variable studentAmount used as index (counterweight) */
                        studentAmount++; // increment student amount to keep in time
                        break;
                    }
                    else
                    {
                        System.out.printf("You cannot add any more students to the array");
                        continue;
                    }

                case 2:
                    System.out.printf("Please enter the studId to capture their marks (Enter 0 to return to main menu)%n");
                    sc.nextLine();
                    String verifyId = sc.nextLine();
                    boolean found = false;
                    if (verifyId.equals("0")) // if statement to re-loop menu if 0 entered
                    {
                    continue;
                    }
                    for (int i = 0; i < keepStudId.length; i++) // search through entries in keepStudId array
                    {
                        if (verifyId.equals(keepStudId[i])) // if a match between sc input and keepStudId array is found...
                        {
                            avgArray[i] = sm.captureMarks(sc, verifyId); /* then call captureMarks method (passing scanner and
                            ID (verifyId)) and save returned average to avgArray at index position */
                            found = true;
                        }
                    }
                    if (!found)
                    {
                        System.out.printf(sm.NOT_FOUND_TEMPLATE, verifyId);
                    }
                    break;

                case 3:
                    sm.reportPerStud(keepStudId, studentAmount, avgArray);
                    break;

                default:
                    System.out.printf("You have entered an invalid option. Enter 0, 1, 2 or 3%n");
            }
        } while(selection != EXIT_CODE);
    }
}