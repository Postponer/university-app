package ua.com.foxminded.universitycms.servicelayer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import ua.com.foxminded.universitycms.daolayer.EventDao;
import ua.com.foxminded.universitycms.daolayer.TimetableDao;
import ua.com.foxminded.universitycms.timetable.Timetable;

@Service
public class TimetableService {

	private TimetableDao timetableDao;
	private EventDao eventDao;
	Logger logger = LoggerFactory.getLogger(TimetableService.class);

	public TimetableService(TimetableDao timetableDao, EventDao eventDao) {

		this.timetableDao = timetableDao;
		this.eventDao = eventDao;

	}

	public Timetable getById(Long timetableId) {

		logger.debug("Getting timetable by id: {}", timetableId);
		Timetable timetable = timetableDao.findById(timetableId).orElse(null);
		logger.info("{} has been gotten by id: {}", timetable, timetableId);

		return timetable;

	}

	public List<Timetable> getAll() {

		logger.debug("Getting all timetables");
		List<Timetable> timetableList = timetableDao.findAll();
		logger.info("All timetables have been gotten");

		return timetableList;

	}

	public Timetable save(Timetable timetable) {

		logger.debug("Saving {}", timetable);
		timetableDao.save(timetable);
		Timetable savedTimetable = timetableDao.findById(timetable.getTimetableId()).orElse(null);
		logger.info("{} has been saved", timetable);

		return savedTimetable;

	}

	public boolean delete(Long timetableId) {

		logger.debug("Deleting timetable with id: {}", timetableId);

		try {

			timetableDao.deleteById(timetableId);
			logger.info("Timetable with id: {} has been deleted", timetableId);
			return true;

		} catch (Exception e) {

			logger.error("Exception occurred during timetable deletion by id: {}, message. Exception: ", timetableId,
					e);
			return false;

		}

	}

	@Transactional
	public void addTimetableToEvent(Long timetableId, Long eventId) {

		logger.info("Adding timetable with id: {} to event with id {}", timetableId, eventId);
		Timetable timetable = timetableDao.findById(timetableId).orElse(null);
		timetable.addEvent(eventDao.findById(eventId).orElse(null));
		timetableDao.save(timetable);
		logger.info("Timetable with id: {} has been added to event with id: {}", timetableId, eventId);

	}

	@Transactional
	public boolean removeTimetableFromEvent(Long timetableId, Long eventId) {

		logger.info("Removing timetable with id: {} from event with id: {}", timetableId, eventId);

		try {

			Timetable timetable = timetableDao.findById(timetableId).orElse(null);
			timetable.removeEvent(eventDao.findById(eventId).orElse(null));
			timetableDao.save(timetable);

			logger.info("Timetable with id: {} has been removed from event with id: {}", timetableId, eventId);
			return true;

		} catch (Exception e) {

			logger.error(
					"Exception occurred during timetable removal with id: {} from event with id: {}, message. Exception:",
					timetableId, eventId, e);
			return false;

		}

	}

}