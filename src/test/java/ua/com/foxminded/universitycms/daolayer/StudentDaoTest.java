package ua.com.foxminded.universitycms.daolayer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import ua.com.foxminded.universitycms.Config;
import ua.com.foxminded.universitycms.UniversityApplication;
import ua.com.foxminded.universitycms.models.Course;
import ua.com.foxminded.universitycms.models.Group;
import ua.com.foxminded.universitycms.models.Student;

@ActiveProfiles("test")
@SpringBootTest(classes = { Config.class, UniversityApplication.class })
class StudentDaoTest {

	private StudentDao studentDao;
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public StudentDaoTest(JdbcTemplate jdbcTemplate, StudentDao studentDao) {

		this.jdbcTemplate = jdbcTemplate;
		this.studentDao = studentDao;

	}

	@Test
	void testFindStudentsByCourse_shouldReturnAllStudentsRelatedToCourse_whenArgumentIsCourseName() {

		Course course = new Course();
		course.setCourseName("Math");
		course.setCourseDescription("Math Course");
		jdbcTemplate.update("insert into courses (course_name, course_description) values (?, ?)",
				course.getCourseName(), course.getCourseDescription());
		Student student1 = new Student();
		Group group = new Group();
		group.setGroupId(1L);
		group.setGroupName("AA-01");
		jdbcTemplate.update("insert into groups (group_name) values (?)", group.getGroupName());
		student1.setGroup(group);
		student1.setFirstName("John");
		student1.setLastName("Doe");
		jdbcTemplate.update("insert into students (group_id, first_name, last_name) values (?, ?, ?)", 1,
				student1.getFirstName(), student1.getLastName());
		Student student2 = new Student();
		student2.setGroup(group);
		student2.setFirstName("Jane");
		student2.setLastName("Miller");
		jdbcTemplate.update("insert into students (group_id, first_name, last_name) values (?, ?, ?)", 1,
				student2.getFirstName(), student2.getLastName());
		jdbcTemplate.update("insert into students_courses (student_id, course_id) values (?, ?)", 1, 1);
		jdbcTemplate.update("insert into students_courses (student_id, course_id) values (?, ?)", 2, 1);
		List<Student> results = studentDao.findStudentsByCourse("Math");
		assertThat(results).hasSize(2);
		Student firstResult = results.get(0);
		assertEquals(1, firstResult.getStudentId());
		assertEquals("John", firstResult.getFirstName());
		assertEquals("Doe", firstResult.getLastName());
		Student secondResult = results.get(1);
		assertEquals(2, secondResult.getStudentId());
		assertEquals("Jane", secondResult.getFirstName());
		assertEquals("Miller", secondResult.getLastName());

	}

}