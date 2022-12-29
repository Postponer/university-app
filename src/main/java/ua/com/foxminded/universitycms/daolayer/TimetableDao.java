package ua.com.foxminded.universitycms.daolayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.universitycms.timetable.Timetable;

@Repository
public interface TimetableDao extends JpaRepository<Timetable, Long> {

}
