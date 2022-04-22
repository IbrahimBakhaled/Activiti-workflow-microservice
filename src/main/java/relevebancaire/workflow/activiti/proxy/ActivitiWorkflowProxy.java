package relevebancaire.workflow.activiti.proxy;


import java.util.List;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import relevebancaire.workflow.activiti.model.ReleveBancaire;

@FeignClient(name = "relevebancaire", url = "http://localhost:8080/api/v1")
@RibbonClient(name = "relevebancaire")
    public interface ActivitiWorkflowProxy {


  @GetMapping("/relevebancaire/{relevebancaireId}")
  ReleveBancaire getReleveBancaireById(@PathVariable Long relevebancaireId);

  @GetMapping("/relevebancaire")
  List<ReleveBancaire> getReleveBancaires();


}
