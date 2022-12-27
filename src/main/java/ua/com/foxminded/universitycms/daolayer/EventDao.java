package ua.com.foxminded.universitycms.daolayer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.universitycms.timetable.Event;

@Repository
public interface EventDao extends JpaRepository<Event, Long> {
	
	@Query(value = "SELECT e FROM Event e WHERE e.eventDate = ?1")
	Optional<Event> getByDate(LocalDate date);
	
	@Query(value = "SELECT e FROM Event e WHERE e.eventTime = ?1")
	Optional<Event> getByTime(LocalTime time);
	
	@Query(value = "SELECT e FROM Event e WHERE e.courseId = ?1")
	Optional<Event> getByCourseId(int courseId);
	
	@Query(value = "SELECT e FROM Event e WHERE e.groupId = ?1")
	Optional<Event> getByGroupId(int groupId);
	
	@Query(value = "SELECT e FROM Event e WHERE e.teacherId = ?1")
	Optional<Event> getByTeacherId(int teacherId);
	
}