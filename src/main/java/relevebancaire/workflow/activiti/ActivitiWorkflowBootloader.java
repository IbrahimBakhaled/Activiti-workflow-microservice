package relevebancaire.workflow.activiti;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("relevebancaire.workflow.activiti")
@EnableEurekaClient
@EnableDiscoveryClient
public class ActivitiWorkflowBootloader {

  public static void main(String[] args) {
    SpringApplication.run(ActivitiWorkflowBootloader.class, args);
  }

}
