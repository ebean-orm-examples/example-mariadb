package org.example.sboot.service;

import io.ebean.Version;
import org.example.sboot.domain.Contract;
import org.example.sboot.domain.Payment;
import org.example.sboot.domain.Worker;
import org.example.sboot.domain.repo.WorkerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest
public class HistoryAsOfTests {

  @Autowired
  WorkerRepository workerRepository;

  @Test
  public void testAsOfQueries() {

    // Initial worker creation
    Worker worker = new Worker();
    worker.setFirstName("Layla");
    worker.setLastName("Mercer");
    worker.setMaritalStatus("Single");
    worker.setNumOfChildren(0);

    workerRepository.save(worker);

    sleep();
    Timestamp tsV1 = Timestamp.from(Instant.now());
    sleep();

    // First update
    worker.setMaritalStatus("Married");
    worker.setDateOfBirth(getDate("25-07-1986"));
    Contract contract = new Contract();
    contract.setBaseSalary(1000);
    contract.setProjectName("Project1");
    contract.setStartDate(getDate("01-01-2020"));
    worker.getContracts().add(contract);

    workerRepository.save(worker);

    sleep();
    Timestamp tsV2 = Timestamp.from(Instant.now());
    sleep();

    // Second update
    worker.setNumOfChildren(1);
    contract.setEndDate(getDate("01-04-2020"));
    contract.setBaseSalary(2000);
    Payment payment = new Payment();
    payment.setAmount(BigDecimal.valueOf(1000.2));
    payment.setCurrency("USD");
    //payment.setPayedAt(Timestamp.from(Instant.now()));
    payment.setPayedAt(Instant.now());
    payment.setExecutedAt(LocalDateTime.now());
    worker.getPayments().add(payment);
    workerRepository.save(worker);

    sleep();
    Timestamp tsV3 = Timestamp.from(Instant.now());

    Worker workerV1 = workerRepository.findVersionAsOfById(worker.getId(), tsV1);
    assertEquals("Single", workerV1.getMaritalStatus());
    assertEquals(0, workerV1.getNumOfChildren());
    assertEquals(0, workerV1.getContracts().size());
    assertEquals(0, workerV1.getPayments().size());

    Worker workerV2 = workerRepository.findVersionAsOfById(worker.getId(), tsV2);
    assertEquals("Married", workerV2.getMaritalStatus());
    assertEquals(0, workerV2.getNumOfChildren());
    assertEquals(1, workerV2.getContracts().size());
    assertEquals(1000, workerV2.getContracts().get(0).getBaseSalary());
    assertEquals(0, workerV2.getPayments().size());

    Worker workerV3 = workerRepository.findVersionAsOfById(worker.getId(), tsV3);
    assertEquals("Married", workerV3.getMaritalStatus());
    assertEquals(1, workerV3.getNumOfChildren());
    assertEquals(1, workerV3.getContracts().size());
    assertEquals(2000, workerV3.getContracts().get(0).getBaseSalary());
    assertEquals(1, workerV3.getPayments().size());

    List<Version<Worker>> workerVersions = workerRepository.findVersionsById(worker.getId(), tsV2, tsV3);
    assertEquals(2, workerVersions.size());
  }

  private void sleep() {
    try {
      Thread.sleep(10000);
    } catch (Exception ex) {
    }
  }

  private Date getDate(String dateStr) {
    try {
      SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
      return new Date(formatter.parse(dateStr).getTime());
    } catch (Exception ex) {
      return null;
    }
  }
}
