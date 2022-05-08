package relevebancaire.workflow.activiti.service;


import static javax.swing.text.html.HTML.Tag.SELECT;
import static org.hibernate.hql.internal.antlr.HqlSqlTokenTypes.FROM;
import static org.hibernate.hql.internal.antlr.HqlSqlTokenTypes.WHERE;

import com.sun.source.tree.VariableTree;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.lang.model.element.VariableElement;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.history.HistoricVariableInstanceQuery;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.impl.persistence.entity.ByteArrayEntity;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntity;
import org.activiti.engine.impl.persistence.entity.VariableInstance;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntity;
import org.activiti.engine.impl.variable.ValueFields;
import org.activiti.engine.impl.variable.VariableTypes;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import org.apache.tomcat.util.descriptor.tld.TldRuleSet.Variable;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import relevebancaire.workflow.activiti.dao.TaskRepository;
import relevebancaire.workflow.activiti.dto.TaskDto;
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


  /**
   *
   * @param tasks for angular
   * @return
   */

  String releveBancaireId = null;
  String variableName= null;
  String variableTypeName = null;

   public List<TaskDto> getTasks(String assignee) {
    ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().singleResult();
    List<HistoricVariableInstance> historicVariableInstance = historyService.createHistoricVariableInstanceQuery().list();
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    TaskService taskService = processEngine.getTaskService();
//    List<Task> tasks = taskService.createTaskQuery().taskDefinitionKey("sid-4131471b-5de5-4760-97c7-c651b27c0258").includeProcessVariables().orderByTaskCreateTime().desc().list();
//    for (Task task : tasks) {
//      Map<String, Object> variables = task.getProcessVariables();
//      for (Entry<String, Object> stringObjectEntry : variables.entrySet()) {
//      releveBancaireId = stringObjectEntry.getValue().toString();
//        System.out.println("this output coming from the value of Map " + releveBancaireId);
//      }
//    }
    for (HistoricVariableInstance variableInstance : historicVariableInstance) {
      variableName = variableInstance.getVariableName();
      variableTypeName = variableInstance.getVariableTypeName();
    }
    List<Task> taskList = taskService.createTaskQuery().taskAssignee(assignee).list();
    String finalVariableName = variableName;
    String finalVariableTypeName = variableTypeName;
     return taskList.stream().map(task ->{
      TaskDto taskDto = new TaskDto();
           List<Task> tasks = taskService.createTaskQuery().taskDefinitionKey("sid-4131471b-5de5-4760-97c7-c651b27c0258").includeProcessVariables().orderByTaskCreateTime().desc().list();
           for (Task Itask : tasks) {
             Map<String, Object> variables = Itask.getProcessVariables();
             for (Entry<String, Object> stringObjectEntry : variables.entrySet()) {
               releveBancaireId = stringObjectEntry.getValue().toString();
      taskDto.setReleveBancaireId(Integer.parseInt(releveBancaireId));
               System.out.println("this output coming from the value of Map " + releveBancaireId);
             }
      taskDto.setVariableName(finalVariableName);
      taskDto.setVariableTypeName(finalVariableTypeName);
      taskDto.setProccessesName(processDefinition.getName());
      taskDto.setId(task.getId());
      taskDto.setAssignee(task.getAssignee());
      taskDto.setName(task.getName());
      taskDto.setDescription(task.getDescription());
      taskDto.setPriority(task.getPriority());
      taskDto.setProcessDefinitionId(task.getProcessDefinitionId());
      taskDto.setCreateTime(task.getCreateTime());
      System.out.println("shwoing task dto " + taskDto);


           }
           return taskDto;
    }
    ).collect(Collectors.toList());
  }

  public TaskDto getTaskById(String id){
    ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().singleResult();
    List<HistoricVariableInstance> historicVariableInstance = historyService.createHistoricVariableInstanceQuery().list();
    String variableName= null;
    String variableTypeName = null;
    List<Task> tasks = taskService.createTaskQuery().taskDefinitionKey("sid-4131471b-5de5-4760-97c7-c651b27c0258").includeProcessVariables().orderByTaskCreateTime().desc().list();
    for (HistoricVariableInstance variableInstance : historicVariableInstance) {
      variableName = variableInstance.getVariableName();
      variableTypeName = variableInstance.getVariableTypeName();
    }
    for (Task task : tasks) {
      Map<String, Object> variables = task.getProcessVariables();
      for (Entry<String, Object> stringObjectEntry : variables.entrySet()) {
        System.out.println("task by id <String, Object> " + stringObjectEntry.getValue());
        releveBancaireId = stringObjectEntry.getValue().toString();
      }
    }
    Task task = taskService.createTaskQuery().taskId(id).singleResult();
    String finalVariableName = variableName;
    String finalVariableTypeName = variableTypeName;
    String finalReleveBancaireId = releveBancaireId;
    TaskDto taskDto = new TaskDto();
    taskDto.setReleveBancaireId(Integer.parseInt(finalReleveBancaireId));
    taskDto.setVariableName(finalVariableName);
    taskDto.setVariableTypeName(finalVariableTypeName);
    taskDto.setProccessesName(processDefinition.getName());
    taskDto.setId(task.getId());
    taskDto.setAssignee(task.getAssignee());
    taskDto.setName(task.getName());
    taskDto.setDescription(task.getDescription());
    taskDto.setPriority(task.getPriority());
    taskDto.setProcessDefinitionId(task.getProcessDefinitionId());
    taskDto.setCreateTime(task.getCreateTime());
    System.out.println("shwoing task by id " + taskDto);
    return taskDto;
  }


  // complete the task
  public void completeTask(String taskId) {
    taskService.complete(taskId);
  }

}
