package relevebancaire.workflow.activiti.controller;


import java.util.List;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import relevebancaire.workflow.activiti.model.ReleveBancaire;
import relevebancaire.workflow.activiti.proxy.ActivitiWorkflowProxy;
import relevebancaire.workflow.activiti.service.ProcessService;

@RestController
public class ActivitiControllerImpl implements ActivitiController {


  Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

  @Autowired
  ActivitiWorkflowProxy activitiWorkflowProxy;

  @Autowired
  private ProcessService processService;


  /**
  those @Ovveride methods are implemented for feign client
   **/

  @Override
  public ResponseEntity<ReleveBancaire> getReleveBancaireById(Long relevebancaireId) {
    return new ResponseEntity<>(activitiWorkflowProxy.getReleveBancaireById(relevebancaireId), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<List<ReleveBancaire>> getReleveBancaires() {
    return new ResponseEntity<>(activitiWorkflowProxy.getReleveBancaires(), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<ReleveBancaire> qualificationrelevebancaire(Long relevebancaireId) {
    activitiWorkflowProxy.qualificationrelevebancaire(relevebancaireId);
    return new ResponseEntity<>(HttpStatus.OK);
  }


  @RequestMapping(value = "/process")
  public String startProcessInstance(@RequestParam Long relevebancaireId) {
    return processService.startTheProcess(relevebancaireId);
  }

  // Retrieve the tasks assigned to an employee
  @RequestMapping(value = "/tasks")
  public String getTasks(@RequestParam String assignee) {
    List<Task> tasks = processService.getTasks(assignee);

    return tasks.toString();
  }

  // Complete the task by their ID
  @RequestMapping(value = "/completetask")
  public String completeTask(@RequestParam String taskId) {
    processService.completeTask(taskId);
    return "Task with id " + taskId + " has been completed!";
  }


}
