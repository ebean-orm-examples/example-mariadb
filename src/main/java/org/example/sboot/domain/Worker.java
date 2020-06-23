package org.example.sboot.domain;

import io.ebean.annotation.History;
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

    String firstName;
    String lastName;
    String maritalStatus;
    int numOfChildren;
    Date dateOfBirth;
    @OneToMany(
            mappedBy = "worker",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    List<Contract> contracts;
    @OneToMany(
            mappedBy = "worker",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    List<Payment> payments;
}
