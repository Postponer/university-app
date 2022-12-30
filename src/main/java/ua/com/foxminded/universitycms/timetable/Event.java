package ua.com.foxminded.universitycms.timetable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import ua.com.foxminded.universitycms.models.Course;
import ua.com.foxminded.universitycms.models.Group;
import ua.com.foxminded.universitycms.models.Teacher;

@Entity
@Table(name = "events")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EVENT_ID")
	private Long id;

	@Column(name = "EVENT_DATE")
	@Temporal(TemporalType.DATE)
	private LocalDate date;

	@Column(name = "EVENT_TIME")
	@Temporal(TemporalType.TIME)
	private LocalTime time;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "course_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Course course;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "group_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Group group;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "teacher_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Teacher teacher;
	
	@ManyToMany(mappedBy = "events")
	private List<Timetable> timetables = new ArrayList<>();

	public Event() {
	}

	public Event(Long id, LocalDate date, LocalTime time, Course course, Group group, Teacher teacher) {

		this.id = id;
		this.date = date;
		this.time = time;
		this.course = course;
		this.group = group;
		this.teacher = teacher;

	}

	public Long getEventId() {

		return id;

	}

	public void setEventId(Long id) {

		this.id = id;

	}

	public LocalDate getDate() {

		return date;

	}

	public void setDate(LocalDate date) {

		this.date = date;

	}

	public LocalTime getTime() {

		return time;

	}

	public void setTime(LocalTime time) {

		this.time = time;

	}

	public Course getCourse() {

		return course;

	}

	public void setCourse(Course course) {

		this.course = course;

	}

	public Group getGroup() {

		return group;

	}

	public void setGroup(Group group) {

		this.group = group;

	}

	public Teacher getTeacher() {

		return teacher;

	}

	public void setTeacher(Teacher teacher) {

		this.teacher = teacher;

	}
	
	public List<Timetable> getTimetables() {
		
		return timetables;
		
	}

	@Override
	public String toString() {
		return "Event [date=" + date + ", time=" + time + ", course=" + course + ", group=" + group + ", teacher="
				+ teacher + "]";
	}

}
