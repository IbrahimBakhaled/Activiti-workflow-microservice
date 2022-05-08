package relevebancaire.workflow.activiti.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import relevebancaire.workflow.activiti.dto.TaskDto;

/**
 * relevebancaire.workflow.activiti.dao 07/05/2022 microservice-relevebancaire-activiti-workflow
 **/
public interface TaskRepository extends JpaRepository<TaskDto, Integer> {

}
