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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

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

  public List<TaskDto> getTasks(String assignee) {
//    return taskService.createTaskQuery().taskAssignee(assignee).list();


//
//    StringBuilder processAndTaskInfo = new StringBuilder();
//    taskList.forEach(task -> {
//      processAndTaskInfo.append(
//          "id: "+ task.getId() +", name: " + task.getName() + ", assignee: " + task.getAssignee() + ", description: " + task.getDescription() + " Date: " + task.getDueDate()
//      + "proccess: " + task.getProcessDefinitionId() + "create time: " + task.getCreateTime() +"procces instance id: "+ task.getProcessInstanceId() +"\r"
//      + "formKey: " + task.getFormKey());
//    });
//    return Collections.singletonList(processAndTaskInfo);


    ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().singleResult();
    List<HistoricVariableInstance> historicVariableInstance = historyService.createHistoricVariableInstanceQuery().list();

    List<ProcessInstance> instances = processEngine.getRuntimeService().createProcessInstanceQuery().includeProcessVariables().list();

    /*
     * Get variables of task instance
     */

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    TaskService taskService = processEngine.getTaskService();
    List<Task> tasks = taskService.createTaskQuery().taskDefinitionKey("sid-4131471b-5de5-4760-97c7-c651b27c0258").includeProcessVariables().orderByTaskCreateTime().desc().list();

    String releveBancaireId = null;

    for (Task task : tasks) {
      Map<String, Object> variables = task.getProcessVariables();
      for (Entry<String, Object> stringObjectEntry : variables.entrySet()) {

        System.out.println("the value of Map<String, Object> should be equal to relevebancaireId " + stringObjectEntry.getValue());
      releveBancaireId = stringObjectEntry.getValue().toString();
      }
    }



    String variableName= null;
    String variableTypeName = null;

    for (HistoricVariableInstance variableInstance : historicVariableInstance) {
      variableName = variableInstance.getVariableName();
      variableTypeName = variableInstance.getVariableTypeName();
    }

    List<Task> taskList = taskService.createTaskQuery().taskAssignee(assignee).list();
    String finalVariableName = variableName;
    String finalVariableTypeName = variableTypeName;
    String finalReleveBancaireId = releveBancaireId;
    return taskList.stream().map(task -> new TaskDto(
            finalReleveBancaireId + "\r",
            finalVariableName + "\r",
            finalVariableTypeName + "\r",
        processDefinition.getName() + "\r",
        task.getId() + "\r",
            task.getAssignee() + "\r",

        task.getName() + "\r",
            task.getDescription() + "\r",
            task.getPriority(),
        task.getProcessDefinitionId() + "\r",
            task.getProcessInstanceId() + "\r",
        task.getCreateTime()))
        .collect(Collectors.toList());

  }
  // complete the task
  public void completeTask(String taskId) {
    taskService.complete(taskId);
  }

}
