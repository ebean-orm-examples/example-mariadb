package org.example.sboot.domain.repo;

import io.ebean.Database;
import io.ebean.ExpressionList;
import io.ebean.PagedList;
import io.ebean.Version;
import org.example.sboot.domain.Worker;
import org.example.sboot.domain.query.QWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class WorkerRepository extends BeanRepository<Long, Worker>{

    @Autowired
    public WorkerRepository(Database server) {
        super(Worker.class, server);
    }

    public List<Version<Worker>> findVersionsById(Long id, Timestamp start, Timestamp end) {
        return server.find(Worker.class)
                .where()
                .eq("id", id)
                .findVersionsBetween(start, end);
    }

    public Worker findVersionAsOfById(Long id, Timestamp timestamp) {
        return server.find(Worker.class)
                .asOf(timestamp)
                .fetchLazy("payments", "contracts")
                .where()
                .eq("id", id)
                .setLabel("foo")
                .findOne();
    }

    public Worker findByFirstName(String firstName) {
      return new QWorker().firstName.ieq(firstName).findOne();
    }
}
