package ua.com.foxminded.universitycms.daolayer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.universitycms.models.Course;

@Repository
public interface CourseDao extends JpaRepository<Course, Long> {

	Optional<Course> getByCourseName(String courseName);

	Optional<Course> getByCourseDescription(String courseDescription);

}