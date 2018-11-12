
import java.util.ArrayList;
import java.util.List;

public class Student implements Student_Interface {

    private String studentID;
    private String name;
    private List<Course> myEnrolledCourses;
    private List<Course> myWaitlist;
    private int courseCoins;

    /**
     * Constructor that populates the instance variables with parameters passed
     *
     * @param id StudentID
     * @param name Name of the student
     * @param coins Hold.Course Coins
     */
    public Student(String id, String name, int coins) {
        this.studentID = id;
        this.name = name;
        this.courseCoins = coins;
        myEnrolledCourses = new ArrayList<>();
        myWaitlist = new ArrayList<>();
    }

    /**
     * Adds course c to the list of enrolled courses Also removes c from the
     * waitlisted courses
     *
     * @param c Hold.Course to be enrolled in
     */
    public void enrollCourse(Course c){
        myEnrolledCourses.add(c);
        myWaitlist.remove(c);
    }

    /**
     * Adds course c to the waitlist
     *
     * @param c course to be waitlisted
     */

    public void waitlistCourse(Course c){
        myWaitlist.add(c);
    }

    /**
     * Accessor for name
     *
     * @return name - Name of the student
     */
    public String getStudentName(){
        return this.name;
    }

    /**
     * Accessor for Hold.Student ID
     *
     * @return studentID - Hold.Student ID
     */
    public String getStudentID(){
        return this.studentID;
    }

    /**
     * Returns a list of all enrolled courses
     *
     * @return List of enrolled courses
     */
    public List<Course> getmyEnrolledCourses(){
        return myEnrolledCourses;
    }

    /**
     * Returns a list of all waitlisted courses
     *
     * @return List of waitlisted courses
     */
    public List<Course> getmyWaitlist(){
        return myWaitlist;
    }

    /**
     * Accessor for course coins
     *
     * @return course coins
     */
    public int getCoins(){
        return courseCoins;
    }

    /**
     * Deducts numCoins from coursecoins
     *
     * @param numCoins Number of coins to be deducted
     */
    public void deductCoins(int numCoins){
        courseCoins = courseCoins - numCoins;
    }

    /**
     * Returns a string representation of the Hold.Student that includes the name and
     * the studentID
     *
     * @return String representation of the student
     */
    @Override
    public String toString() {
        return this.name + "(" + this.studentID + ")";
    }
}
