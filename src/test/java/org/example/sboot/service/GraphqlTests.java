package org.example.sboot.service;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import org.example.sboot.TestUtils;
import org.example.sboot.domain.Contract;
import org.example.sboot.domain.Payment;
import org.example.sboot.domain.Worker;
import org.example.sboot.domain.repo.WorkerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GraphqlTests {

  @Autowired
  WorkerRepository workerRepository;

  @Autowired
  WorkerService workerService;

  @Test
  public void testGraphql() throws Exception{

    // Initial worker creation
    Worker worker = new Worker();
    worker.setFirstName("Aden");
    worker.setLastName("Patton");
    worker.setMaritalStatus("Single");
    worker.setNumOfChildren(0);

    Contract contract = new Contract();
    contract.setBaseSalary(1000);
    contract.setProjectName("Project1");
    contract.setStartDate(TestUtils.getDate("01-01-2020"));
    worker.getContracts().add(contract);

    Payment payment = new Payment();
    payment.setPayedAt(Instant.now());
    payment.setPayedAmount(BigDecimal.valueOf(1000L));
    payment.setPayedAmountCurrency("USD");
    payment.setExecutedAt(LocalDateTime.now());
    worker.getPayments().add(payment);

    workerRepository.save(worker);

    GraphQLSchema schema = new GraphQLSchemaGenerator()
      .withOperationsFromSingleton(workerService) //register the service
      .generate(); //done ;)
    GraphQL graphQL = new GraphQL.Builder(schema)
      .build();

    System.out.println("######### Query 0 - Only Worker ###########");
    ExecutionResult result0 = graphQL.execute(
      "{ worker (id: 1) { firstName, lastName }}");
    System.out.println("Returned JSON : " + result0.getData());
    String expectedJson = "{worker={firstName:Aden, lastName:Patton}}";
    JSONAssert.assertEquals(expectedJson, result0.getData().toString(), JSONCompareMode.STRICT);

    System.out.println("######### Query 1 - Worker + Contracts ###########");

    ExecutionResult result1 = graphQL.execute(
      "{ worker (id: 1) { firstName, lastName, contracts { projectName } }}");
    System.out.println("Returned JSON : " + result1.getData());
    expectedJson = "{worker={firstName=Aden, lastName=Patton, contracts=[{projectName=Project1}]}}";
    JSONAssert.assertEquals(expectedJson, result1.getData().toString(), JSONCompareMode.STRICT);

    System.out.println("######### Query 2 -  Worker + Payments ###########");

    ExecutionResult result2 = graphQL.execute(
      "{ worker (id: 1) { firstName, lastName, payments { payedAmountCurrency } }}");
    System.out.println("Returned JSON : " + result2.getData());
    expectedJson = "{worker={firstName:Aden, lastName:Patton, payments:[{payedAmountCurrency:USD}]}}";
    JSONAssert.assertEquals(expectedJson, result2.getData().toString(), JSONCompareMode.STRICT);

    System.out.println("######### Query 3 +  Worker + Contracts + Payments ###########");

    ExecutionResult result3 = graphQL.execute(
      "{ worker (id: 1) { firstName, lastName, contracts { projectName }, payments { payedAmountCurrency } }}");
    System.out.println("Returned JSON : " + result3.getData());
    expectedJson = "{worker={firstName:Aden, lastName:Patton, contracts=[{projectName=Project1}], payments:[{payedAmountCurrency:USD}]}}";
    JSONAssert.assertEquals(expectedJson, result3.getData().toString(), JSONCompareMode.STRICT);
  }

}
