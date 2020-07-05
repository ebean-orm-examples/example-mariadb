package org.example.sboot.domain;

import io.ebean.DB;
import io.ebean.Version;
import io.ebean.test.UserContext;
import org.example.sboot.TestUtils;
import org.example.sboot.domain.repo.WorkerRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HistoryAsOfTests {

  WorkerRepository workerRepository = new WorkerRepository(DB.getDefault());

  @Test
  public void testHistoryQueries() {
    UserContext.setUserId("U1");

    // Initial worker creation
    Worker worker = new Worker();
    worker.setFirstName("Layla");
    worker.setLastName("Mercer");
    worker.setMaritalStatus("Single");
    worker.setNumOfChildren(0);

    workerRepository.save(worker);

    TestUtils.sleep(10);
    Timestamp tsV1 = Timestamp.from(Instant.now());
    TestUtils.sleep(10);

    // First update
    worker.setMaritalStatus("Married");
    worker.setDateOfBirth(TestUtils.getDate("25-07-1986"));
    Contract contract = new Contract();
    contract.setBaseSalary(1000);
    contract.setProjectName("Project1");
    contract.setStartDate(TestUtils.getDate("01-01-2020"));
    worker.getContracts().add(contract);

    workerRepository.save(worker);

    TestUtils.sleep(10);
    Timestamp tsV2 = Timestamp.from(Instant.now());
    TestUtils.sleep(10);

    // Second update
    worker.setNumOfChildren(1);
    contract.setEndDate(TestUtils.getDate("01-04-2020"));
    contract.setBaseSalary(2000);
    Payment payment = new Payment();
    payment.setPayedAmount(BigDecimal.valueOf(1000.2));
    payment.setPayedAmountCurrency("USD");
    payment.setPayedAt(Instant.now());
    payment.setExecutedAt(LocalDateTime.now());
    worker.getPayments().add(payment);
    workerRepository.save(worker);

    TestUtils.sleep(10);
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

}
