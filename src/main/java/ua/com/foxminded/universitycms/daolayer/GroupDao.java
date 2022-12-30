package ua.com.foxminded.universitycms.daolayer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.universitycms.models.Group;

@Repository
public interface GroupDao extends JpaRepository<Group, Long> {

	Optional<Group> getByGroupName(String groupName);

	@Query(value = "SELECT groups.* FROM groups LEFT JOIN students ON groups.group_id = students.group_id GROUP BY groups.group_id HAVING COUNT(students.group_id) <= ?1", nativeQuery = true)
	List<Group> findGroupsByStudentNumber(int studentNumber);

}