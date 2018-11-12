import java.util.ArrayList;
import java.util.List;

public class Course implements Course_Interface {

	private String courseName;
	private String courseCode;
	private MyPriorityQueue<Registration> waitlistQueue;
	private List<Student> roster;
	private int maxCapacity;

	public Course(String name, String code, int capacity) {
		courseName = name;
		courseCode = code;
		maxCapacity = capacity;
		waitlistQueue = new MyPriorityQueue<>(maxCapacity);
		roster = new ArrayList<>();
	}

	/**
	 * Accessor for course name
	 *
	 * @return course name
	 */
	public String getCourseName(){
	    return courseName;
    }

	/**
	 * Accessor for course code
	 *
	 * @return course code
	 */
	public String getCourseCode(){
	    return courseCode;
    }

	/**
	 * Accessor for course capacity
	 *
	 * @return course capacity
	 */
	public int getCourseCapacity(){
	    return maxCapacity;
    }

	/**
	 * Accessor for Hold.Course Roster
	 *
	 * @return course roster
	 */
	public List<Student> getCourseRoster(){
	    return roster;
    }

	/**
	 * Checks whether the course enrollment has reached its capacity
	 *
	 * @return Returns true if the number of enrolled students is equal to
	 * capacity, false otherwise
	 */
	public boolean isFull(){
	    return roster.size() == maxCapacity;
    }

	/**
	 * Enqueues the student to the waitlist for this course
	 *
	 * @param r Registration to be waitlisted
	 */
	public void addToWaitlist(Registration r){
	    r.setTimestamp();
	    r.getStudent().waitlistCourse(r.getCourse());
	    waitlistQueue.offer(r);
    }

	/**
	 * Enrolls the next student in the waitlist to the course. Does nothing if
	 * the waitlist is empty
	 *
	 * @return Registration Request that was processed
	 */
	public Registration processWaitlist(){
	    if(waitlistQueue.peek() == null){
	        return null;
        }else{
            //Remove registration from waitlist
            Registration newReg = waitlistQueue.poll();
            //Enroll student in course
            roster.add(newReg.getStudent());
            //Update students enrollment list
            newReg.getStudent().enrollCourse(newReg.getCourse());
            return newReg;
        }
    }

	@Override
	public String toString() {
		return courseCode;
	}
}
