package org.example.sboot.domain;

import io.ebean.annotation.History;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Entity
@History
@Table(name = "worker")
public class Worker extends BaseModel {

    @GraphQLQuery(name = "firstName", description = "Worker's first name")
    String firstName;
    @GraphQLQuery(name = "lastName", description = "Worker's last name")
    String lastName;
    @GraphQLQuery(name = "maritalStatus", description = "marital Status")
    String maritalStatus;
    @GraphQLQuery(name = "numOfChildren", description = "Num Of children")
    int numOfChildren;
    Date dateOfBirth;
    @OneToMany(
            mappedBy = "worker",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @GraphQLQuery(name = "contracts", description = "Worker Contracts")
    List<Contract> contracts;
    @OneToMany(
            mappedBy = "worker",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @GraphQLQuery(name = "payments", description = "Worker Payments")
    List<Payment> payments;
}
