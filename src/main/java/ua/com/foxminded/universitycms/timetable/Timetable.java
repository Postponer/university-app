package ua.com.foxminded.universitycms.timetable;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "timetables")
public class Timetable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "timetables_events", joinColumns = @JoinColumn(name = "timetable_id"), inverseJoinColumns = @JoinColumn(name = "event_id"))
	private List<Event> events = new ArrayList<>();

	public Timetable(Long id) {
		
		this.id = id;
		
	}
	
	public Long getId() {
		
		return id;
		
	}

	public void setId(Long id) {
		
		this.id = id;
		
	}

	public void addEvent(Event event) {
		
		events.add(event);
		event.getTimetables().add(this);
		
	}
	
	public void removeEvent(Event event) {
		
		events.remove(event);
		event.getTimetables().remove(this);
		
	}
	
}