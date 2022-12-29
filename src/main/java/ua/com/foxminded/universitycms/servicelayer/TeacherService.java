package ua.com.foxminded.universitycms.servicelayer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import ua.com.foxminded.universitycms.daolayer.GroupDao;
import ua.com.foxminded.universitycms.daolayer.TeacherDao;
import ua.com.foxminded.universitycms.models.Teacher;

@Service
public class TeacherService {

	private TeacherDao teacherDao;
	private GroupDao groupDao;
	Logger logger = LoggerFactory.getLogger(TeacherService.class);

	public TeacherService(TeacherDao teacherDao, GroupDao groupDao) {

		this.teacherDao = teacherDao;
		this.groupDao = groupDao;

	}

	public Teacher getById(Long teacherId) {

		logger.debug("Getting teacher by id: {}", teacherId);
		Teacher teacher = teacherDao.findById(teacherId).orElse(null);
		logger.info("{} has been gotten by id: {}", teacher, teacherId);

		return teacher;

	}

	public List<Teacher> getAll() {

		logger.debug("Getting all teachers");
		List<Teacher> teacherList = teacherDao.findAll();
		logger.info("All teachers have been gotten");

		return teacherList;

	}

	public Teacher save(Teacher teacher) {

		logger.debug("Saving {}", teacher);
		teacherDao.save(teacher);
		Teacher savedTeacher = teacherDao.findById(teacher.getTeacherId()).orElse(null);
		logger.info("{} has been saved", teacher);

		return savedTeacher;

	}

	public Teacher update(Long teacherId, String firstName, String lastName) {

		logger.debug("Updating teacher with id: {} with this parameters: {}", teacherId, firstName + ", " + lastName);
		teacherDao.save(new Teacher(teacherId, firstName, lastName));
		Teacher updatedTeacher = teacherDao.findById(teacherId).orElse(null);
		logger.info("Teacher with id: {} has been updated with this parameters: {}", teacherId,
				firstName + ", " + lastName);

		return updatedTeacher;

	}

	public boolean delete(Long teacherId) {

		logger.debug("Deleting teacher with id: {}", teacherId);

		try {

			teacherDao.deleteById(teacherId);
			logger.info("Teacher with id: {} has been deleted", teacherId);
			return true;

		} catch (Exception e) {

			logger.error("Exception occurred during teacher deletion by id: {}, message. Exception: ", teacherId, e);
			return false;

		}

	}

	@Transactional
	public void addTeacherToGroup(Long teacherId, Long groupId) {

		logger.info("Adding student with id: {} to course with id {}", teacherId, groupId);
		Teacher teacher = teacherDao.findById(teacherId).orElse(null);
		teacher.addGroup(groupDao.findById(groupId).orElse(null));
		teacherDao.save(teacher);
		logger.info("Student with id: {} has been added to course with id: {}", teacherId, groupId);

	}

	@Transactional
	public boolean removeTeacherFromGroup(Long teacherId, Long groupId) {

		logger.info("Removing teacher with id: {} from group with id: {}", teacherId, groupId);

		try {

			Teacher teacher = teacherDao.findById(teacherId).orElse(null);
			teacher.removeGroup(groupDao.findById(groupId).orElse(null));
			teacherDao.save(teacher);

			logger.info("Teacher with id: {} has been removed from group with id: {}", teacherId, groupId);
			return true;

		} catch (Exception e) {

			logger.error(
					"Exception occurred during teacher removal with id: {} from group with id: {}, message. Exception:",
					teacherId, groupId, e);
			return false;

		}

	}

}