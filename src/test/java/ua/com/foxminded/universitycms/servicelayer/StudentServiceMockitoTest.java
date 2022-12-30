package ua.com.foxminded.universitycms.servicelayer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxminded.universitycms.daolayer.StudentDao;
import ua.com.foxminded.universitycms.models.Group;
import ua.com.foxminded.universitycms.models.Student;

@ExtendWith(MockitoExtension.class)
class StudentServiceMockitoTest {

	@InjectMocks
	private StudentService studentService;

	@Mock
	private StudentDao studentDao;

	@Test
	void testFindStudentsByCourse() {

		Group group = new Group(1L, "AA-01");
		Student student1 = new Student(1L, group, "John", "Doe");
		Student student2 = new Student(2L, group, "Jane", "Miller");
		List<Student> studentList = new ArrayList<>();
		studentList.add(student1);
		studentList.add(student2);
		Mockito.when(studentDao.findStudentsByCourse(Mockito.any(String.class))).thenReturn(studentList);
		assertThat(studentService.findStudentsByCourse("Math")).isEqualTo(studentList);
		Mockito.verify(studentDao).findStudentsByCourse("Math");

	}

}