package ua.com.foxminded.universitycms.daolayer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import ua.com.foxminded.universitycms.models.Group;

@Repository
public interface GroupDao extends JpaRepository<Group, Long> {

	@Query(value = "SELECT g FROM Group g WHERE g.groupName = ?1")
	Optional<Group> getByName(String groupName);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "UPDATE Group g SET g.groupName = ?2 WHERE groupId = ?1")
	void update(Long groupId, String groupName);

	@Query(value = "SELECT groups.* FROM groups LEFT JOIN students ON groups.group_id = students.group_id GROUP BY groups.group_id HAVING COUNT(students.group_id) <= ?1", nativeQuery = true)
	List<Group> findGroupsByStudentNumber(int studentNumber);

}