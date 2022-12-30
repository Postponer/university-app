package ua.com.foxminded.universitycms.servicelayer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.com.foxminded.universitycms.daolayer.EventDao;
import ua.com.foxminded.universitycms.models.Course;
import ua.com.foxminded.universitycms.models.Group;
import ua.com.foxminded.universitycms.models.Teacher;
import ua.com.foxminded.universitycms.timetable.Event;

@Service
public class EventService {

	private EventDao eventDao;
	Logger logger = LoggerFactory.getLogger(EventService.class);

	public EventService(EventDao eventDao) {

		this.eventDao = eventDao;

	}

	public Event getByDate(LocalDate eventDate) {

		logger.debug("Getting event by date: {}", eventDate);
		Event event = eventDao.getByDate(eventDate).orElse(null);
		logger.info("{} has been gotten by name: {}", event, eventDate);

		return event;

	}

	public Event getByTime(LocalTime eventTime) {

		logger.debug("Getting event by time: {}", eventTime);
		Event event = eventDao.getByTime(eventTime).orElse(null);
		logger.info("{} has been gotten by time: {}", event, eventTime);

		return event;

	}

	public List<Event> getByCourseId(Long courseId) {

		logger.debug("Getting events by course id: {}", courseId);
		List<Event> events = eventDao.getByCourseId(courseId);
		logger.info("{} has been gotten by course id: {}", events, courseId);

		return events;

	}

	List<Event> getByGroupId(Long groupId) {

		logger.debug("Getting events by group id: {}", groupId);
		List<Event> events = eventDao.getByCourseId(groupId);
		logger.info("{} has been gotten by group id: {}", events, groupId);

		return events;

	}

	List<Event> getByTeacherId(Long teacherId) {

		logger.debug("Getting events by teacher id: {}", teacherId);
		List<Event> events = eventDao.getByCourseId(teacherId);
		logger.info("{} has been gotten by teacher id: {}", events, teacherId);

		return events;

	}

	public Event getById(Long eventId) {

		logger.debug("Getting event by id: {}", eventId);
		Event event = eventDao.findById(eventId).orElse(null);
		logger.info("{} has been gotten by id: {}", event, eventId);

		return event;

	}

	public List<Event> getAll() {

		logger.debug("Getting all events");
		List<Event> eventList = eventDao.findAll();
		logger.info("All events have been gotten");

		return eventList;

	}

	public Event save(Event event) {

		logger.debug("Saving {}", event);
		eventDao.save(event);
		Event savedEvent = eventDao.findById(event.getEventId()).orElse(null);
		logger.info("{} has been saved", event);

		return savedEvent;

	}

	public Event update(Long eventId, LocalDate date, LocalTime time, Course course, Group group, Teacher teacher) {

		logger.debug("Updating event with id: {} with this parameters: {}", eventId,
				date + ", " + time + ", " + course + ", " + group + ", " + teacher);
		eventDao.save(new Event(eventId, date, time, course, group, teacher));
		Event updatedEvent = eventDao.findById(eventId).orElse(null);
		logger.info("Event with id: {} has been updated with this parameters: {}", eventId,
				date + ", " + time + ", " + course + ", " + group + ", " + teacher);

		return updatedEvent;

	}

	public boolean delete(Long eventId) {

		logger.debug("Deleting event with id: {}", eventId);

		try {

			eventDao.deleteById(eventId);
			logger.info("Event with id: {} has been deleted", eventId);
			return true;

		} catch (Exception e) {

			logger.error("Exception occurred during event deletion by id: {}, message. Exception: ", eventId, e);
			return false;

		}

	}

}