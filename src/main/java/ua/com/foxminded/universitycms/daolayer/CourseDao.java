package ua.com.foxminded.universitycms.daolayer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import ua.com.foxminded.universitycms.models.Course;

@Repository
public interface CourseDao extends JpaRepository<Course, Long> {

	@Query(value = "SELECT c FROM Course c WHERE c.courseName = ?1")
	Optional<Course> getByName(String courseName);

	@Query(value = "SELECT c FROM Course c WHERE c.courseDescription = ?1")
	Optional<Course> getByDescription(String courseDescription);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "UPDATE Course c SET c.courseName = ?2, c.courseDescription = ?3 WHERE courseId = ?1")
	int update(Long courseId, String courseName, String courseDescription);

}