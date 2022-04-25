package relevebancaire.workflow.activiti.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import relevebancaire.workflow.activiti.model.ReleveBancaire;

public interface ActivitiController {

  @GetMapping("/relevebancaire/{relevebancaireId}")
  ResponseEntity<ReleveBancaire> getReleveBancaireById(@PathVariable Long relevebancaireId);

  @GetMapping("/relevebancaire")
  ResponseEntity<List<ReleveBancaire>> getReleveBancaires();

  @PostMapping("/relevebancaire/qualification/{relevebancaireId}")
  ResponseEntity<ReleveBancaire> qualificationrelevebancaire(@PathVariable Long relevebancaireId);

}
