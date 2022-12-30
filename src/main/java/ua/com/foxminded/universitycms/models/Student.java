package ua.com.foxminded.universitycms.models;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Student extends Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "STUDENT_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "group_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	Group group;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "students_courses", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
	private List<Course> courses = new ArrayList<>();

	public Student() {
	}

	public Student(Long id, Group group, String firstName, String lastName) {

		super(firstName, lastName);
		this.id = id;
		this.group = group;

	}

	public Long getStudentId() {

		return id;

	}

	public void setStudentId(Long id) {

		this.id = id;

	}

	public Group getGroup() {

		return group;

	}

	public void setGroup(Group group) {

		this.group = group;

	}

	public void addCourse(Course course) {

		courses.add(course);
		course.getStudents().add(this);

	}

	public void removeCourse(Course course) {

		courses.remove(course);
		course.getStudents().remove(this);

	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", group=" + group + ", courses=" + courses + super.toString();
	}

}