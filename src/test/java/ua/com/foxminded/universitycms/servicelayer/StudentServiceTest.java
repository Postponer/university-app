package ua.com.foxminded.universitycms.servicelayer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import ua.com.foxminded.universitycms.Config;
import ua.com.foxminded.universitycms.UniversityApplication;

@ActiveProfiles("test")
@SpringBootTest(classes = { Config.class, UniversityApplication.class })
class StudentServiceTest {

	private StudentService studentService;
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public StudentServiceTest(JdbcTemplate jdbcTemplate, StudentService studentService) {

		this.jdbcTemplate = jdbcTemplate;
		this.studentService = studentService;

	}

	@BeforeEach
	void reset() {

		jdbcTemplate.update(
				"truncate table students_courses, students, timetables_events, events, courses, teachers_groups, groups");
		jdbcTemplate.update("ALTER SEQUENCE groups_group_id_seq RESTART WITH 1");
		jdbcTemplate.update("ALTER SEQUENCE courses_course_id_seq RESTART WITH 1");
		jdbcTemplate.update("ALTER SEQUENCE students_student_id_seq RESTART WITH 1");

	}

	@Test
	void testAddStudentToCourse() {

		jdbcTemplate.update("insert into groups (group_name) values (?)", "AA-01");
		jdbcTemplate.update("insert into students (group_id, first_name, last_name) values (?, ?, ?)", 1, "John",
				"Doe");
		jdbcTemplate.update("insert into courses (course_name, course_description) values (?, ?)", "Math",
				"Math course");
		studentService.addStudentToCourse(1L, 1L);
		assertEquals(1, jdbcTemplate.queryForObject("select count(*) from students_courses", Integer.class));

	}

	@Test
	void testRemoveStudentFromCourse() {

		jdbcTemplate.update("insert into groups (group_name) values (?)", "AA-01");
		jdbcTemplate.update("insert into students (group_id, first_name, last_name) values (?, ?, ?)", 1, "John",
				"Doe");
		jdbcTemplate.update("insert into courses (course_name, course_description) values (?, ?)", "Math",
				"Math course");
		jdbcTemplate.update("insert into students_courses (student_id, course_id) values (?, ?)", 1, 1);
		studentService.removeStudentFromCourse(1L, 1L);
		assertEquals(0, jdbcTemplate.queryForObject("select count(*) from students_courses", Integer.class));

	}

	@Test
	void testRemoveStudentFromCourseWhenExceptionIsThrown() {

		assertEquals(false, studentService.removeStudentFromCourse(1L, 1L));

	}

}