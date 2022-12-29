package ua.com.foxminded.universitycms.servicelayer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxminded.universitycms.daolayer.GroupDao;
import ua.com.foxminded.universitycms.models.Group;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

	@InjectMocks
	private GroupService groupService;

	@Mock
	private GroupDao groupDao;

	@Test
	void testFindGroupsByStudentNumber() {

		Group group1 = new Group(1L, "AA-07");
		Group group2 = new Group(2L, "AA-08");
		List<Group> groupList = new ArrayList<>();
		groupList.add(group1);
		groupList.add(group2);
		Mockito.when(groupDao.findGroupsByStudentNumber(Mockito.any(Integer.class))).thenReturn(groupList);
		assertThat(groupService.findGroupsByStudentNumber(1)).isEqualTo(groupList);
		Mockito.verify(groupDao).findGroupsByStudentNumber(1);

	}

}