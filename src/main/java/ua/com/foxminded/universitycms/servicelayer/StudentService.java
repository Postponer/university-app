package ua.com.foxminded.universitycms.servicelayer;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import ua.com.foxminded.universitycms.daolayer.CourseDao;
import ua.com.foxminded.universitycms.daolayer.GroupDao;
import ua.com.foxminded.universitycms.daolayer.StudentDao;
import ua.com.foxminded.universitycms.models.Group;
import ua.com.foxminded.universitycms.models.Student;

@Service
public class StudentService {

	private StudentDao studentDao;
	private GroupDao groupDao;
	private CourseDao courseDao;
	private Scanner scanner = new Scanner(System.in);
	Logger logger = LoggerFactory.getLogger(StudentService.class);

	public StudentService(StudentDao studentDao, GroupDao groupDao, CourseDao courseDao) {

		this.studentDao = studentDao;
		this.groupDao = groupDao;
		this.courseDao = courseDao;

	}

	public List<Student> getByGroupId(Long groupId) {

		logger.info("Getting students by group id: {}", groupId);
		List<Student> students = studentDao.getByGroupId(groupId);
		logger.info("{} has been gotten by group id: {}", students, groupId);

		return students;

	}

	public Student getByFirstName(String firstName) {

		logger.info("Getting student by first name: {}", firstName);
		Student student = studentDao.getByFirstName(firstName).orElse(null);
		logger.info("{} has been gotten by first name: {}", student, firstName);

		return student;

	}

	public Student getByLastName(String lastName) {

		logger.info("Getting student by last name: {}", lastName);
		Student student = studentDao.getByLastName(lastName).orElse(null);
		logger.info("{} has been gotten by last name: {}", student, lastName);

		return student;

	}

	public Student getById(Long studentId) {

		logger.info("Getting student by id: {}", studentId);
		Student student = studentDao.findById(studentId).orElse(null);
		logger.info("{} has been gotten by id: {}", student, studentId);

		return student;

	}

	public List<Student> getAll() {

		logger.info("Getting all students");
		List<Student> studentList = studentDao.findAll();
		logger.info("All courses have been gotten");

		return studentList;

	}

	public Student save(Student student) {

		logger.info("Saving {}", student);
		studentDao.save(student);
		Student savedStudent = studentDao.findById(student.getStudentId()).orElse(null);
		logger.info("{} has been saved", student);

		return savedStudent;

	}

	public Student update(Long studentId, Group group, String firstName, String lastName) {

		logger.info("Updating student with id: {} with this parameters: {}", studentId,
				group + ", " + firstName + ", " + lastName);
		studentDao.save(new Student(studentId, group, firstName, lastName));
		Student updatedStudent = studentDao.findById(studentId).orElse(null);
		logger.info("Student with id: {} has been updated with this parameters: {}", studentId,
				group + ", " + firstName + ", " + lastName);

		return updatedStudent;

	}

	public boolean delete(Long studentId) {

		logger.info("Deleting student with id: {}", studentId);

		try {

			studentDao.deleteById(studentId);
			logger.info("Student with id: {} has been deleted", studentId);
			return true;

		} catch (Exception e) {

			logger.error("Exception occurred during student deletion by id: {}, message. Exception: ", studentId, e);
			return false;

		}

	}

	public List<Student> findStudentsByCourse(String courseName) {

		logger.info("Finding students by course: {}", courseName);
		List<Student> studentList = studentDao.findStudentsByCourse(courseName);
		logger.info("Students have been found by course: {}", courseName);

		return studentList;

	}

	@Transactional
	public void addStudentToCourse(Long studentId, Long courseId) {

		logger.info("Adding student with id: {} to course with id {}", studentId, courseId);
		Student student = studentDao.findById(studentId).orElse(null);
		student.addCourse(courseDao.findById(courseId).orElse(null));
		studentDao.save(student);
		logger.info("Student with id: {} has been added to course with id: {}", studentId, courseId);

	}

	@Transactional
	public boolean removeStudentFromCourse(Long studentId, Long courseId) {

		logger.info("Removing student with id: {} from course with id: {}", studentId, courseId);

		try {

			Student student = studentDao.findById(studentId).orElse(null);
			student.removeCourse(courseDao.findById(courseId).orElse(null));
			studentDao.save(student);

			logger.info("Student with id: {} has been removed from course with id: {}", studentId, courseId);
			return true;

		} catch (Exception e) {

			logger.error(
					"Exception occurred during student removal with id: {} from course with id: {}, message. Exception:",
					studentId, courseId, e);
			return false;

		}

	}

	public void findStudentsByCourse() {

		logger.info("Finding students by course in console menu");
		String courseName;

		System.out.println("Please enter name of the course: ");
		courseName = scanner.nextLine();
		System.out.println(findStudentsByCourse(courseName));
		logger.info("Students have been found by course: {} in console menu", courseName);

	}

	public void addNewStudent() {

		logger.info("Adding new student in console menu");

		try {

			System.out.println("Please enter group_id: ");
			Group group = groupDao.findById(Long.valueOf(scanner.nextLine())).orElse(null);

			System.out.println("Please enter first name: ");
			String firstName = scanner.nextLine();

			System.out.println("Please enter last name: ");
			String lastName = scanner.nextLine();

			save(new Student(0L, group, firstName, lastName));
			logger.info("New student has been added in console menu");

		} catch (NumberFormatException e) {

			logger.error("Exception occurred during adding new student in console menu, message. Exception: ", e);
			System.out.println("Invalid input. Please use numbers.");

		}

	}

	public void deleteStudent() {

		logger.info("Deleting student in console menu");

		try {

			System.out.println("Please enter student_id: ");
			Long studentId = Long.valueOf(scanner.nextLine());

			delete(studentId);
			logger.info("Student has been deleted in console menu");

		} catch (NumberFormatException e) {

			logger.error("Exception occurred during student deletion in console menu, message. Exception: ", e);
			System.out.println("Invalid input. Please use numbers.");

		}

	}

	public void addStudentToCourse() {

		logger.info("Adding student to course in console menu");

		try {

			System.out.println("Please enter student_id: ");
			Long studentId = Long.valueOf(scanner.nextLine());

			System.out.println("Please enter course_id: ");
			Long courseId = Long.valueOf(scanner.nextLine());

			addStudentToCourse(studentId, courseId);
			logger.info("Student with id: {} has been added to course with id: {} in console menu", studentId,
					courseId);

		} catch (NumberFormatException e) {

			logger.error("Exception occurred during student adding to course in console menu, message. Exception: ", e);
			System.out.println("Invalid input. Please use numbers.");

		}

	}

	public void removeStudentFromCourse() {

		logger.info("Removing student from course in console menu");

		try {

			System.out.println("Please enter student_id: ");
			Long studentId = Long.valueOf(scanner.nextLine());

			System.out.println("Please enter course_id: ");
			Long courseId = Long.valueOf(scanner.nextLine());

			removeStudentFromCourse(studentId, courseId);
			logger.info("Student with id: {} has been removed from course with id: {} in console menu", studentId,
					courseId);

		} catch (NumberFormatException e) {

			logger.error("Exception occurred during student removal from course in console menu, message. Exception: ",
					e);
			System.out.println("Invalid input. Please use numbers.");

		}

	}

}