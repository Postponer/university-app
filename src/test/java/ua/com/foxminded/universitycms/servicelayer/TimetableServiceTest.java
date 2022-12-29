package ua.com.foxminded.universitycms.servicelayer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;

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
class TimetableServiceTest {

	private TimetableService timetableService;
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public TimetableServiceTest(JdbcTemplate jdbcTemplate, TimetableService timetableService) {

		this.jdbcTemplate = jdbcTemplate;
		this.timetableService = timetableService;

	}

	@BeforeEach
	void reset() {

		jdbcTemplate.update(
				"truncate table timetables_events, timetables, events, students_courses, students ,courses, teachers_groups, groups, teachers");
		jdbcTemplate.update("ALTER SEQUENCE groups_group_id_seq RESTART WITH 1");
		jdbcTemplate.update("ALTER SEQUENCE courses_course_id_seq RESTART WITH 1");
		jdbcTemplate.update("ALTER SEQUENCE teachers_teacher_id_seq RESTART WITH 1");
		jdbcTemplate.update("ALTER SEQUENCE events_event_id_seq RESTART WITH 1");
		jdbcTemplate.update("ALTER SEQUENCE timetables_timetable_id_seq RESTART WITH 1");

	}

	@Test
	void testAddTimetableToEvent() {

		jdbcTemplate.update("insert into courses (course_name, course_description) values (?, ?)", "Math",
				"Math course");
		jdbcTemplate.update("insert into groups (group_name) values (?)", "AA-01");
		jdbcTemplate.update("insert into teachers (first_name, last_name) values (?, ?)", "John", "Doe");
		jdbcTemplate.update(
				"insert into events (event_date, event_time, course_id, group_id, teacher_id) values (?, ?, ?, ?, ?)",
				LocalDate.now(), LocalTime.now(), 1, 1, 1);
		jdbcTemplate.update("insert into timetables (timetable_id) values (?)", 1);

		timetableService.addTimetableToEvent(1L, 1L);
		assertEquals(1, jdbcTemplate.queryForObject("select count(*) from timetables_events", Integer.class));

	}

	@Test
	void testRemoveTimetableFromEvent() {

		jdbcTemplate.update("insert into courses (course_name, course_description) values (?, ?)", "Math",
				"Math course");
		jdbcTemplate.update("insert into groups (group_name) values (?)", "AA-01");
		jdbcTemplate.update("insert into teachers (first_name, last_name) values (?, ?)", "John", "Doe");
		jdbcTemplate.update(
				"insert into events (event_date, event_time, course_id, group_id, teacher_id) values (?, ?, ?, ?, ?)",
				LocalDate.now(), LocalTime.now(), 1, 1, 1);
		jdbcTemplate.update("insert into timetables (timetable_id) values (?)", 1);

		timetableService.removeTimetableFromEvent(1L, 1L);
		assertEquals(0, jdbcTemplate.queryForObject("select count(*) from timetables_events", Integer.class));

	}

	@Test
	void testRemoveTimetableFromEventWhenExceptionIsThrown() {

		assertEquals(false, timetableService.removeTimetableFromEvent(1L, 1L));

	}

}