package ua.com.foxminded.universitycms.daolayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.universitycms.models.Teacher;

@Repository
public interface TeacherDao extends JpaRepository<Teacher, Long> {

}