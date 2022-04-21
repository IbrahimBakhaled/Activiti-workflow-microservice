package relevebancaire.workflow.activiti.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import relevebancaire.workflow.activiti.model.ReleveBancaire;
import relevebancaire.workflow.activiti.proxy.ActivitiWorkflowProxy;

@RestController
public class ActivitiControllerImpl implements ActivitiController {

  @Autowired
  ActivitiWorkflowProxy activitiWorkflowProxy;

  @Override
  public ResponseEntity<ReleveBancaire> getReleveBancaireById(Long relevebancaireId) {
    return new ResponseEntity<>(activitiWorkflowProxy.getReleveBancaireById(relevebancaireId), HttpStatus.OK);
  }



}
