package ua.com.foxminded.universitycms.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "teachers")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Teacher extends Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TEACHER_ID")
	private Long id;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "teachers_groups", joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
	private List<Group> groups = new ArrayList<>();

	public Teacher() {
	}

	public Teacher(Long id, String firstName, String lastName) {

		super(firstName, lastName);
		this.id = id;

	}

	public Long getTeacherId() {

		return id;

	}

	public void setTeacherId(Long id) {

		this.id = id;

	}

	public void addGroup(Group group) {

		groups.add(group);
		group.getTeachers().add(this);

	}

	public void removeGroup(Group group) {

		groups.remove(group);
		group.getTeachers().remove(this);

	}

	@Override
	public String toString() {
		return "Teacher [teacherId=" + id + ", groups=" + groups + super.toString();
	}

}