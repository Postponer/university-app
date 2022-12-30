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
class TeacherServiceTest {

	private TeacherService teacherService;
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public TeacherServiceTest(JdbcTemplate jdbcTemplate, TeacherService teacherService) {

		this.jdbcTemplate = jdbcTemplate;
		this.teacherService = teacherService;

	}

	@BeforeEach
	void reset() {

		jdbcTemplate.update("truncate table students_courses, students, timetables_events, events, teachers_groups, teachers, groups");
		jdbcTemplate.update("ALTER SEQUENCE groups_group_id_seq RESTART WITH 1");
		jdbcTemplate.update("ALTER SEQUENCE teachers_teacher_id_seq RESTART WITH 1");

	}

	@Test
	void testAddTeacherToGroup() {

		jdbcTemplate.update("insert into groups (group_name) values (?)", "AA-01");
		jdbcTemplate.update("insert into teachers (first_name, last_name) values (?, ?)", "John", "Doe");
		teacherService.addTeacherToGroup(1L, 1L);
		assertEquals(1, jdbcTemplate.queryForObject("select count(*) from teachers_groups", Integer.class));

	}

	@Test
	void testRemoveTeacherFromGroup() {

		jdbcTemplate.update("insert into groups (group_name) values (?)", "AA-01");
		jdbcTemplate.update("insert into teachers (first_name, last_name) values (?, ?)", "John", "Doe");
		jdbcTemplate.update("insert into teachers_groups (teacher_id, group_id) values (?, ?)", 1, 1);
		teacherService.removeTeacherFromGroup(1L, 1L);
		assertEquals(0, jdbcTemplate.queryForObject("select count(*) from teachers_groups", Integer.class));

	}

	@Test
	void testRemoveTeacherFromGroupWhenExceptionIsThrown() {

		assertEquals(false, teacherService.removeTeacherFromGroup(1L, 1L));

	}

}