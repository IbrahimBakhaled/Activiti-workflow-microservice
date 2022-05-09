package relevebancaire.workflow.activiti.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import relevebancaire.workflow.activiti.dto.TaskDto;

/**
 * relevebancaire.workflow.activiti.dao 07/05/2022 microservice-relevebancaire-activiti-workflow
 **/

@Transactional
public interface TaskRepository extends JpaRepository<TaskDto, Integer> {

  void deleteByTaskId(String taskId);
}
