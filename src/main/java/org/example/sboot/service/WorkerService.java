package org.example.sboot.service;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.example.sboot.domain.Worker;
import org.example.sboot.domain.repo.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkerService {

  @Autowired
  WorkerRepository workerRepository;

  @GraphQLQuery(name = "worker")
  public Worker getById(@GraphQLArgument(name = "id") Long id) {
      return workerRepository.byId(id);
  }
}
