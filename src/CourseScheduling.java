import java.io.*;
import java.util.*;

/**
 * Course Registration Manager
 *
 * @author Liam McCarthy A14029718
 * @version 1.0
 * @since 11/11/2018
 */
public class CourseScheduling {

    public static List<Course> courseList = new LinkedList<>();
    public static List<Student> studentList = new LinkedList<>();

    /**
     * Read info file to establish course catalog and student list
     *
     * @param fname
     */
    public static void populateCourseandStudents(String fname) {
        File file = new File(fname);
        try {
            Scanner scanner = new Scanner(file);
            scanner.nextLine();
            boolean loadCourse = true;
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine();
                if (nextLine.isEmpty()) {
                    loadCourse = false;
                    scanner.nextLine();
                    continue;
                }
                Scanner wordscan = new Scanner(nextLine);
                if (loadCourse) {
                    String id = wordscan.next();
                    String cname = wordscan.next();
                    int capacity = wordscan.nextInt();
                    Course course = new Course(cname, id, capacity);
                    courseList.add(course);
                } else {
                    String sname = wordscan.next();
                    String pid = wordscan.next();
                    int coins = wordscan.nextInt();
                    Student student = new Student(pid, sname, coins);
                    studentList.add(student);
                }
                wordscan.close();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
        }
    }

    /**
     * Output successful course enrollment/waitlisting
     *
     * @param s student identifier
     * @param c desired course
     * @param coins request weighting
     * @param enroll true if the student was enrolled
     */
    public static void print(Student s, Course c, int coins, boolean enroll) {
        String signup = ((enroll) ? "Enrolling " : "Waitlisting ");
        String amount = coins + ((coins == 1) ? " coin" : " coins");
        System.out.println(signup + s + " to Course " + c + " with " + amount);
    }

    /**
     * Output unsuccessful course enrollment/waitlisting
     *
     * @param s student identifier
     * @param c desired course
     * @param enroll true if the student was enrolled
     */
    public static void printFail(Student s, Course c, boolean enroll) {
        String signup = ((enroll) ? "enrolled" : "waitlisted");
        System.out.println(s + "is already " + signup + " in Course " + c);
    }

    /**
     * Output course capacity message
     *
     * @param c desired course
     */
    public static void printCapacity(Course c) {
        System.out.println(c + " has already reached maximum capacity."
                + " There are no more seats left");
    }

    /**
     * Output insufficient tokens message
     *
     * @param s student identifier
     * @param c desired course
     */
    public static void printNoCoins(Student s, Course c) {
        System.out.println("Insufficient course coins."
                + " Cannot waitlist " + s + " to " + c);
    }

    /**
     * Output general registration information
     */
    public static void generateOutput() {
        System.out.println("\n\n########COURSE INFORMATION######");
        ListIterator<Course> iter = courseList.listIterator();
        while (iter.hasNext()) {
            Course temp = iter.next();
            System.out.println(temp.getCourseCode()
                    + "  " + temp.getCourseName());
            System.out.println("\tRoster: " + temp.getCourseRoster());
        }

        System.out.println("\n\n########STUDENT INFORMATION######");
        ListIterator<Student> iterS = studentList.listIterator();
        while (iterS.hasNext()) {
            Student temp = iterS.next();
            System.out.println(temp.toString());
            System.out.println("\t Enrolled courses: "
                    + temp.getmyEnrolledCourses());
            System.out.println("\t Waitlisted courses: "
                    + temp.getmyWaitlist());
        }
    }

    //**********************YOUR WORK STARTS HERE*****************************
    /**
     * Returns a reference to the Course object whose courseCode is code
     *
     * @param code Course code of the Course to be returned
     * @return Course Course with the course code passed as a parameter
     */
    public static Course getCourse(String code) {
        ListIterator<Course> iter = courseList.listIterator();

        for(int i = 0; i < courseList.size(); i++){
            if(iter.hasNext()){
                Course currCourse= iter.next();
                if(currCourse.getCourseCode().equals(code)){
                    return currCourse;
                }
            }
        }
        return null;
    }

    /**
     * Returns a reference to the Student object whose StudentID is pid
     *
     * @param pid StudentID of the Student to be returned
     * @return Student student with the id passed as a parameter
     */
    public static Student getStudent(String pid) {
        ListIterator<Student> iter = studentList.listIterator();

        for(int i = 0; i < studentList.size(); i++){
            if(iter.hasNext()){
                Student currStudent= iter.next();
                if(currStudent.getStudentID().equals(pid)){
                    return currStudent;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        populateCourseandStudents(args[0]);
        File file = new File(args[1]);

        try {
            Scanner scLine = new Scanner(file);	//scan lines in file
            System.out.println("####START COURSE SCHEDULING####\n");
            while (scLine.hasNextLine()) {//if the file has next line
                Scanner scWord = new Scanner(scLine.nextLine());
                String property;

                if (scWord.hasNext()) {//if the file has next word
                    property = scWord.next();
                } else {
                    break;
                }
                if (property.equals("register")) {
                    //add a new student to the course list
                    //get the student pid, course, and coins
                    String pid;
                    String courseCode;
                    int coins;
                    pid = scWord.next();
                    courseCode = scWord.next();
                    coins = Integer.parseInt(scWord.next());
                    //get student object and course object
                    //make registration object
                    Registration r = new Registration(getStudent(pid), getCourse(courseCode), coins);
                    //check if student is waitlisted or enrolled in course
                    boolean isWaitlisted = r.getStudent().getmyWaitlist().contains(r.getCourse());
                    boolean isEnrolled = r.getStudent().getmyEnrolledCourses().contains(r.getCourse());
                    //check if student has enough coins to be added
                    boolean enoughCoins = r.getStudent().getCoins() >= coins;
                    //if all apply add registration to the courses waitlist
                    if(isWaitlisted) {
                        printFail(r.getStudent(), r.getCourse(), false);
                    }else if(isEnrolled){
                        printFail(r.getStudent(), r.getCourse(), true);
                    }else if(!enoughCoins){
                        printNoCoins(r.getStudent(), r.getCourse());
                    }else {
                        r.getCourse().addToWaitlist(r);
                        //deduct coins from student
                        r.getStudent().deductCoins(coins);
                        print(r.getStudent(), r.getCourse(), r.getCoins(), false);
                    }

                } else if (property.equals("enroll")) {
                    //process registrations in the waitlist
                    System.out.println("\n####STARTING BATCH ENROLLMENT####");
                    //check how many enrollments are done for each course
                    int quantity = Integer.parseInt(scWord.next());
                    //loop through number of enrollments
                    for(int i = 0; i < quantity; i++){
                        //loop through every course
                        for(Course c : courseList){
                            //check if capacity has been reached for the course
                            if(c.isFull()){
                                printCapacity(c);
                            }else{
                                //processes waitlist for that course
                                Registration newReg = c.processWaitlist();
                                //check if no registrations in waiting list
                                if(newReg != null){
                                    print(newReg.getStudent(), newReg.getCourse(), newReg.getCoins(), true);
                                }

                            }
                        }
                    }

                    System.out.println("####ENDING BATCH ENROLLMENT####\n");
                } else {
                    break;
                }
                scWord.close();
            }
            scLine.close();
            System.out.println("\n####END COURSE SCHEDULING####");
        } catch (FileNotFoundException e) {
            System.err.println("Failed to open " + file);
            System.exit(1);
        }

        generateOutput();
    }//end of main method
}
