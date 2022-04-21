package relevebancaire.workflow.activiti.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import relevebancaire.workflow.activiti.model.ReleveBancaire;

public interface ActivitiController {

  @GetMapping("/relevebancaire/{relevebancaireId}")
  ResponseEntity<ReleveBancaire> getReleveBancaireById(@PathVariable Long relevebancaireId);

}
