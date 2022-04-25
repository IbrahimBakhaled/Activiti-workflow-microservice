package relevebancaire.workflow.activiti.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import relevebancaire.workflow.activiti.proxy.ActivitiWorkflowProxy;

@Service
public class ProcessService {

  @Autowired
  ActivitiWorkflowProxy activitiWorkflowProxy;

  @Autowired
  private RuntimeService runtimeService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private RepositoryService repositoryService;


  public String startTheProcess(Long relevebancaireId) {

    activitiWorkflowProxy.qualificationrelevebancaire(relevebancaireId);
    Map<String, Object> variables = new HashMap<>();
    variables.put("relevebancaireId", relevebancaireId);

    runtimeService.startProcessInstanceByKey("relevebancaire-workflow", variables);

    return processInformation();
  }

  public String processInformation() {

    List<Task> taskList = taskService.createTaskQuery().orderByTaskCreateTime().asc().list();

    StringBuilder processAndTaskInfo = new StringBuilder();

    processAndTaskInfo.append("Number of process definition available: "
        + repositoryService.createProcessDefinitionQuery().count() + " | Task Details= ");

    taskList.forEach(task -> {

      processAndTaskInfo.append("ID: " + task.getId() + ", Name: " + task.getName() + ", Assignee: "
          + task.getAssignee() + ", Description: " + task.getDescription());
    });

    return processAndTaskInfo.toString();
  }

  public List<Task> getTasks(String assignee) {
    return taskService.createTaskQuery().taskAssignee(assignee).list();
  }

  // complete the task
  public void completeTask(String taskId) {
    taskService.complete(taskId);
  }

}
