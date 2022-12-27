package ua.com.foxminded.universitycms.applicationrunnertaskexecutor;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import ua.com.foxminded.universitycms.models.Group;
import ua.com.foxminded.universitycms.models.Student;
import ua.com.foxminded.universitycms.servicelayer.GroupService;
import ua.com.foxminded.universitycms.servicelayer.StudentService;

@Profile("!test")
@Component
public class ApplicationRunnerTaskExecutor implements ApplicationRunner {

	private GroupService groupService;
	private StudentService studentService;

	public ApplicationRunnerTaskExecutor(GroupService groupService, StudentService studentService) {

		this.groupService = groupService;
		this.studentService = studentService;

	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		Group group = new Group("AA-01");
		groupService.save(group);

	}

}