package ua.com.foxminded.universitycms.daolayer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import ua.com.foxminded.universitycms.Config;
import ua.com.foxminded.universitycms.UniversityApplication;
import ua.com.foxminded.universitycms.models.Group;
import ua.com.foxminded.universitycms.models.Student;

@ActiveProfiles("test")
@SpringBootTest(classes = { Config.class, UniversityApplication.class })
class GroupDaoTest {

	private GroupDao groupDao;
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public GroupDaoTest(JdbcTemplate jdbcTemplate, GroupDao groupDao) {

		this.jdbcTemplate = jdbcTemplate;
		this.groupDao = groupDao;

	}

	@BeforeEach
	void reset() {

		jdbcTemplate.update(
				"truncate table students_courses, students, timetables_events, events, teachers_groups, groups");
		jdbcTemplate.update("ALTER SEQUENCE groups_group_id_seq RESTART WITH 1");
		jdbcTemplate.update("ALTER SEQUENCE students_student_id_seq RESTART WITH 1");

	}

	@Test
	void testFindGroupsByStudentNumber_shouldReturnAllGroupsWithLessOrEqualStudentsNumber_whenMethodIsExecuted() {

		Group firstGroup = new Group();
		firstGroup.setGroupName("AA-06");
		Group secondGroup = new Group();
		secondGroup.setGroupName("AA-07");
		jdbcTemplate.update("insert into groups (group_name) values (?)", firstGroup.getGroupName());
		jdbcTemplate.update("insert into groups (group_name) values (?)", secondGroup.getGroupName());
		Student student1 = new Student();
		student1.setGroup(firstGroup);
		student1.setFirstName("John");
		student1.setLastName("Doe");
		Student student2 = new Student();
		student2.setGroup(firstGroup);
		student2.setFirstName("Jane");
		student2.setLastName("Doe");
		Student student3 = new Student();
		student3.setGroup(secondGroup);
		student3.setFirstName("Alex");
		student3.setLastName("Miller");
		jdbcTemplate.update("insert into students (group_id, first_name, last_name) values (?, ?, ?)", 1,
				student1.getFirstName(), student1.getLastName());
		jdbcTemplate.update("insert into students (group_id, first_name, last_name) values (?, ?, ?)", 1,
				student2.getFirstName(), student2.getLastName());
		jdbcTemplate.update("insert into students (group_id, first_name, last_name) values (?, ?, ?)", 2,
				student3.getFirstName(), student3.getLastName());
		Group result = groupDao.findGroupsByStudentNumber(1).get(0);
		assertEquals(2, result.getGroupId());
		assertEquals("AA-07", result.getGroupName());

	}

	@Test
	void testFindGroupsByStudentNumber_shouldReturnAllGroups_whenArgumentIsBiggerThanStudentNumber() {

		Group firstGroup = new Group();
		firstGroup.setGroupName("AA-08");
		Group secondGroup = new Group();
		secondGroup.setGroupName("AA-09");
		jdbcTemplate.update("insert into groups (group_name) values (?)", firstGroup.getGroupName());
		jdbcTemplate.update("insert into groups (group_name) values (?)", secondGroup.getGroupName());
		Student student1 = new Student();
		student1.setGroup(firstGroup);
		student1.setFirstName("John");
		student1.setLastName("Doe");
		Student student2 = new Student();
		student1.setGroup(firstGroup);
		student2.setFirstName("Jane");
		student2.setLastName("Doe");
		Student student3 = new Student();
		student3.setGroup(secondGroup);
		student3.setFirstName("Alex");
		student3.setLastName("Miller");
		jdbcTemplate.update("insert into students (group_id, first_name, last_name) values (?, ?, ?)", 1,
				student1.getFirstName(), student1.getLastName());
		jdbcTemplate.update("insert into students (group_id, first_name, last_name) values (?, ?, ?)", 1,
				student2.getFirstName(), student2.getLastName());
		jdbcTemplate.update("insert into students (group_id, first_name, last_name) values (?, ?, ?)", 2,
				student3.getFirstName(), student3.getLastName());
		List<Group> results = groupDao.findGroupsByStudentNumber(4);
		assertThat(results).hasSize(2);
		Group firstResult = results.get(0);
		assertEquals(2, firstResult.getGroupId());
		assertEquals("AA-09", firstResult.getGroupName());
		Group secondResult = results.get(1);
		assertEquals(1, secondResult.getGroupId());
		assertEquals("AA-08", secondResult.getGroupName());

	}

}