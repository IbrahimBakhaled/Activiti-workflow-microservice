package relevebancaire.workflow.activiti.service;


import static javax.swing.text.html.HTML.Tag.SELECT;
import static org.hibernate.hql.internal.antlr.HqlSqlTokenTypes.FROM;
import static org.hibernate.hql.internal.antlr.HqlSqlTokenTypes.WHERE;

import com.sun.source.tree.VariableTree;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import relevebancaire.workflow.activiti.dao.TaskRepository;
import relevebancaire.workflow.activiti.dto.TaskDto;
import relevebancaire.workflow.activiti.proxy.ActivitiWorkflowProxy;

@Service
@Transactional
public class ProcessService {

  @Autowired
  ActivitiWorkflowProxy activitiWorkflowProxy;

  @Autowired
  private RuntimeService runtimeService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private RepositoryService repositoryService;

  @Autowired
  private HistoryService historyService;

  @Autowired
  ProcessEngine processEngine;

  @Autowired
  ManagementService managementService;

  @Autowired
  TaskRepository taskRepository;

  public String startTheProcess(Long relevebancaireId) {
//    activitiWorkflowProxy.qualificationrelevebancaire(relevebancaireId);

    Map<String, Object> variables = new HashMap<>();
    variables.put("relevebancaireId", relevebancaireId);

    ProcessInstance processInstance =  runtimeService.startProcessInstanceByKey("relevebancaire-workflow", variables);

    ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().singleResult();
    List<HistoricVariableInstance> historicVariableInstance = historyService.createHistoricVariableInstanceQuery().list();
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    TaskService taskService = processEngine.getTaskService();
    List<Task> taskList = taskService.createTaskQuery().orderByTaskCreateTime().asc().list();

    variables = runtimeService.getVariables(processInstance.getId());
    System.out.println("Variables " + variables);


    for (HistoricVariableInstance variableInstance : historicVariableInstance) {
      variableName = variableInstance.getVariableName();
      variableTypeName = variableInstance.getVariableTypeName();
    }
    int taskId = 0;
    String taskAssignee = null;
    String taskName = null;
    String taskDescription= null;
    int taskPriority = 0;
    String taskProcessDefinitionId= null;
    Date taskCreateTime= null;
    for (Task task: taskList){
      taskId = Integer.parseInt(task.getId());
      taskAssignee = task.getAssignee();
      taskName = task.getName();
      taskDescription = task.getDescription();
      taskPriority = task.getPriority();
      taskProcessDefinitionId = task.getProcessDefinitionId();
      taskCreateTime = task.getCreateTime();
    }
    String finalVariableName = variableName;
    String finalVariableTypeName = variableTypeName;

    int finalTaskId = taskId;
    String finalTaskAssignee = taskAssignee;
    String finalTaskName = taskName;
    String finalTaskDescription = taskDescription;
    String finalTaskProcessDefinitionId = taskProcessDefinitionId;
    int finalTaskPriority = taskPriority;
    Date finalTaskCreateTime = taskCreateTime;

    TaskDto taskDto = new TaskDto();
    Entry<String, Object> testVariable = variables.entrySet().iterator().next();
        taskDto.setReleveBancaireId(Integer.parseInt(testVariable.getValue().toString()));
        taskDto.setVariableName(finalVariableName);
        taskDto.setVariableTypeName(finalVariableTypeName);
        taskDto.setProccessesName(processDefinition.getName());
        taskDto.setTaskId(finalTaskId);
        taskDto.setAssignee(finalTaskAssignee);
        taskDto.setName(finalTaskName);
        taskDto.setDescription(finalTaskDescription);
        taskDto.setPriority(finalTaskPriority);
        taskDto.setProcessDefinitionId(finalTaskProcessDefinitionId);
        taskDto.setCreateTime(finalTaskCreateTime);

        taskRepository.save(taskDto);
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


  /**
   *
   * @param tasks for angular
   * @return
   */

  String releveBancaireId = null;
  String variableName= null;
  String variableTypeName = null;

   public List<TaskDto> getTasks(String assignee) {

    return taskRepository.findAll();
  }

  public TaskDto getTaskById(int id){
    return taskRepository.findByTaskId(id);
  }


  // complete the task
  public void completeTask(int taskId) {

    taskService.complete(String.valueOf(taskId));
    taskRepository.deleteByTaskId(taskId);
  }
}
