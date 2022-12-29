package ua.com.foxminded.universitycms.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "courses")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COURSE_ID")
	private Long id;

	@Column(name = "COURSE_NAME")
	private String courseName;

	@Column(name = "COURSE_DESCRIPTION")
	private String courseDescription;

	@ManyToMany(mappedBy = "courses")
	private List<Student> students = new ArrayList<>();

	public Course() {
	}

	public Course(Long id, String courseName, String courseDescription) {

		this.id = id;
		this.courseName = courseName;
		this.courseDescription = courseDescription;

	}

	public Long getCourseId() {

		return id;

	}

	public void setCourseId(Long id) {

		this.id = id;

	}

	public String getCourseName() {

		return courseName;

	}

	public void setCourseName(String courseName) {

		this.courseName = courseName;

	}

	public String getCourseDescription() {

		return courseDescription;

	}

	public void setCourseDescription(String courseDescription) {

		this.courseDescription = courseDescription;

	}

	public List<Student> getStudents() {

		return students;

	}

	@Override
	public String toString() {
		return "Course [courseId=" + id + ", courseName=" + courseName + ", courseDescription="
				+ courseDescription + "]";
	}

}