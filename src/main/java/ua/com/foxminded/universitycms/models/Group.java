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
@Table(name = "groups")
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "GROUP_ID")
	private Long id;

	@Column(name = "GROUP_NAME")
	private String groupName;

	@ManyToMany(mappedBy = "groups")
	private List<Teacher> teachers = new ArrayList<>();

	public Group() {
	}

	public Group(String groupName) {

		this.groupName = groupName;

	}

	public Long getGroupId() {

		return id;

	}

	public String getGroupName() {

		return groupName;

	}

	public void setGroupName(String groupName) {

		this.groupName = groupName;

	}

	public List<Teacher> getTeachers() {

		return teachers;

	}

	@Override
	public String toString() {
		return "Group [groupId=" + id + ", groupName=" + groupName + "]";
	}

}