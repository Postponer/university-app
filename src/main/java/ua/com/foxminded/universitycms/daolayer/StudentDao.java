package ua.com.foxminded.universitycms.daolayer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.universitycms.models.Student;

@Repository
public interface StudentDao extends JpaRepository<Student, Long> {

	List<Student> getByGroupId(Long groupId);
	
	Optional<Student> getByFirstName(String firstName);

	Optional<Student> getByLastName(String lastName);

	@Query(value = "SELECT s FROM Student s JOIN s.courses c WHERE c.courseName = ?1")
	List<Student> findStudentsByCourse(String courseName);

}