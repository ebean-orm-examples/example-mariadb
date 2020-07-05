package org.example.sboot.domain;

import io.ebean.DB;
import io.ebean.test.UserContext;
import org.example.sboot.domain.query.QWorker;
import org.example.sboot.domain.repo.WorkerRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class QueryBeanTests {

  @Test
  public void testUsingQueryBeans() {
    UserContext.setUserId("U1");

    Worker worker = new Worker();
    worker.setFirstName("Jessie");
    worker.setLastName("Randolph");
    worker.setMaritalStatus("Single");
    worker.setNumOfChildren(5);

    worker.save();

    Worker worker2 = new Worker();
    worker2.setFirstName("Rhiana");
    worker2.setLastName("Alston");
    worker2.setMaritalStatus("Single");
    worker2.setNumOfChildren(5);

    worker2.save();

    List<Worker> workers = new QWorker().firstName.contains("ssi").findList();
    assertEquals(1, workers.size());

    workers = new QWorker().numOfChildren.eq(5).findList();
    assertEquals(2, workers.size());
  }

  @Test
  public void testUsingRepository() {
    UserContext.setUserId("U1");
    WorkerRepository workerRepository = new WorkerRepository(DB.getDefault());

    Worker worker = new Worker();
    worker.setFirstName("Tom");
    worker.setLastName("Jones");
    worker.setMaritalStatus("Single");
    worker.setNumOfChildren(0);

    workerRepository.save(worker);

    Worker verifyWorker = workerRepository.findByFirstName("Tom");
    assertNotNull(verifyWorker);
  }
}
