package ua.com.foxminded.universitycms.daolayer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import ua.com.foxminded.universitycms.models.Student;

@Repository
public interface StudentDao extends JpaRepository<Student, Long> {

	@Query(value = "SELECT s FROM Student s WHERE s.groupId = ?1")
	Optional<Student> getByGroupId(int groupId);

	@Query(value = "SELECT s FROM Student s WHERE s.firstName = ?1")
	Optional<Student> getByFirstName(String firstName);

	@Query(value = "SELECT s FROM Student s WHERE s.lastName = ?1")
	Optional<Student> getByLastName(String lastName);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "UPDATE Student s SET s.groupId = ?2, s.firstName = ?3, s.lastName = ?4 WHERE studentId = ?1")
	void update(Long studentId, int groupId, String firstName, String lastName);

	@Query(value = "SELECT s FROM Student s JOIN s.courses c WHERE c.courseName = ?1")
	List<Student> findStudentsByCourse(String courseName);

	@Modifying
	@Transactional
	@Query(value = "INSERT INTO students_courses (student_id, course_id) VALUES (?1, ?2)", nativeQuery = true)
	void addStudentToCourse(int studentId, int courseId);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM students_courses WHERE student_id = ?1 AND course_id = ?2", nativeQuery = true)
	void removeStudentFromCourse(int studentId, int courseId);

}