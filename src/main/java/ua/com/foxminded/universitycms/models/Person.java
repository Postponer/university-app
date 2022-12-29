package ua.com.foxminded.universitycms.models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Person {

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	public Person() {
	}

	public Person(String firstName, String lastName) {

		this.firstName = firstName;
		this.lastName = lastName;

	}

	public String getFirstName() {

		return firstName;

	}

	public void setFirstName(String firstName) {

		this.firstName = firstName;

	}

	public String getLastName() {

		return lastName;

	}

	public void setLastName(String lastName) {

		this.lastName = lastName;

	}

	@Override
	public String toString() {
		return ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}

}