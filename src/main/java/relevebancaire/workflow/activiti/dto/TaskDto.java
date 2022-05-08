package relevebancaire.workflow.activiti.dto;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * relevebancaire.workflow.activiti.dto 29/04/2022 microservice-relevebancaire-activiti-workflow
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
@Entity
public class TaskDto {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int taskId;
  private int releveBancaireId;
  private String variableName;
  private String variableTypeName;
  private String proccessesName;
  private String id;
  private String assignee;
  private String name;
  private String description;
  private int priority;
  private String processDefinitionId;
  private String processInstanceId;
  private Date createTime;

}
