package ua.com.foxminded.universitycms.servicelayer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.com.foxminded.universitycms.daolayer.CourseDao;
import ua.com.foxminded.universitycms.models.Course;

@Service
public class CourseService {

	private CourseDao courseDao;
	Logger logger = LoggerFactory.getLogger(CourseService.class);

	public CourseService(CourseDao courseDao) {

		this.courseDao = courseDao;

	}

	public Course getByName(String courseName) {

		logger.debug("Getting course by name: {}", courseName);
		Course course = courseDao.getByCourseName(courseName).orElse(null);
		logger.info("{} has been gotten by name: {}", course, courseName);

		return course;

	}

	public Course getByDescription(String courseDescription) {

		logger.debug("Getting course by description: {}", courseDescription);
		Course course = courseDao.getByCourseDescription(courseDescription).orElse(null);
		logger.info("{} has been gotten by description: {}", course, courseDescription);

		return course;

	}

	public Course getById(Long courseId) {

		logger.debug("Getting course by id: {}", courseId);
		Course course = courseDao.findById(courseId).orElse(null);
		logger.info("{} has been gotten by id: {}", course, courseId);

		return course;

	}

	public List<Course> getAll() {

		logger.debug("Getting all courses");
		List<Course> courseList = courseDao.findAll();
		logger.info("All courses have been gotten");

		return courseList;

	}

	public Course save(Course course) {

		logger.debug("Saving {}", course);
		courseDao.save(course);
		Course savedCourse = courseDao.findById(course.getCourseId()).orElse(null);
		logger.info("{} has been saved", course);

		return savedCourse;

	}

	public Course update(Long courseId, String courseName, String courseDescription) {

		logger.debug("Updating course with id: {} with this parameters: {}", courseId,
				courseName + ", " + courseDescription);
		courseDao.save(new Course(courseId, courseName, courseDescription));
		Course updatedCourse = courseDao.findById(courseId).orElse(null);
		logger.info("Course with id: {} has been updated with this parameters: {}", courseId,
				courseName + ", " + courseDescription);

		return updatedCourse;

	}

	public boolean delete(Long courseId) {

		logger.debug("Deleting course with id: {}", courseId);

		try {

			courseDao.deleteById(courseId);
			logger.info("Course with id: {} has been deleted", courseId);
			return true;

		} catch (Exception e) {

			logger.error("Exception occurred during course deletion by id: {}, message. Exception: ", courseId, e);
			return false;

		}

	}

}