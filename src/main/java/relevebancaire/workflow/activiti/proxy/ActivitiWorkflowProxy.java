package relevebancaire.workflow.activiti.proxy;


import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import relevebancaire.workflow.activiti.model.ReleveBancaire;

@FeignClient(name = "relevebancaire", url = "http://localhost:8080")
@RibbonClient(name = "relevebancaire")
    public interface ActivitiWorkflowProxy {


  @GetMapping("/relevebancaire/{relevebancaireId}")
  ReleveBancaire getReleveBancaireById(@PathVariable Long relevebancaireId);


}
