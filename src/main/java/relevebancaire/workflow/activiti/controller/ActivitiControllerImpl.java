package relevebancaire.workflow.activiti.controller;


import java.util.List;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import relevebancaire.workflow.activiti.dto.TaskDto;
import relevebancaire.workflow.activiti.model.ReleveBancaire;
import relevebancaire.workflow.activiti.proxy.ActivitiWorkflowProxy;
import relevebancaire.workflow.activiti.service.ProcessService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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
  public ResponseEntity<String> startProcessInstance(@RequestParam Long relevebancaireId) {
    return new ResponseEntity<>(processService.startTheProcess(relevebancaireId), HttpStatus.OK);
  }

  // Retrieve the tasks assigned to an employee
  @RequestMapping(value = "/tasks")
  public ResponseEntity<List<TaskDto>> getTasks(@RequestParam String assignee) {
    return new ResponseEntity<>(processService.getTasks(assignee), HttpStatus.OK);

  }


  @GetMapping("/task/{id}")
   public ResponseEntity<TaskDto> getTaskById(@PathVariable int id){
    return new ResponseEntity<>(processService.getTaskById(id), HttpStatus.OK);
  }



  // Complete the task by their ID
  @RequestMapping(value = "/completetask")
  public String completeTask(@RequestParam int taskId) {
    processService.completeTask(taskId);
    return "Task with id " + taskId + " has been completed!";
  }


}
