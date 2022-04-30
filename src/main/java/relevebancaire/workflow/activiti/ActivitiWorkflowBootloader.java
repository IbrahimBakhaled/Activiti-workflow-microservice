package relevebancaire.workflow.activiti;


import brave.sampler.Sampler;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ByteArrayEntity;
import org.activiti.engine.impl.variable.ValueFields;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableFeignClients("relevebancaire.workflow.activiti")
@EnableEurekaClient
@EnableDiscoveryClient
public class ActivitiWorkflowBootloader {

  public static void main(String[] args) {
    SpringApplication.run(ActivitiWorkflowBootloader.class, args);
  }

  @Bean
  public Sampler defaultSampler(){
    return Sampler.ALWAYS_SAMPLE;
  }
//
//  @Bean
//  CommandLineRunner init(
//      final RuntimeService runtimeService,
//      final TaskService taskService,
//      final ProcessService processService) {
//
//    return new CommandLineRunner() {
//
//      public void run(String... strings) throws Exception {
//        runtimeService.startProcessInstanceByKey("relevebancaire-workflow");
//        processService.processInformation();
//        System.out.println("There are " + runtimeService.createProcessInstanceQuery().count() + " processes in Mysql Database");
//      }
//    };
  }


