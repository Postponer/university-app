package ua.com.foxminded.universitycms.daolayer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.universitycms.timetable.Event;

@Repository
public interface EventDao extends JpaRepository<Event, Long> {
	
	Optional<Event> getByDate(LocalDate eventDate);
	
	Optional<Event> getByTime(LocalTime eventTime);
	
	List<Event> getByCourseId(Long courseId);
	
	List<Event> getByGroupId(Long groupId);
	
	List<Event> getByTeacherId(Long teacherId);
	
}