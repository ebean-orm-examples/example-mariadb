package org.example.sboot.domain;

import io.ebean.annotation.History;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@History
@Table(name = "worker_payment")
public class Payment extends BaseModel {
    LocalDateTime executedAt;
    Instant payedAt;
    @GraphQLQuery(name = "payedAmount", description = "Payed Amount")
    BigDecimal payedAmount;
    @GraphQLQuery(name = "payedAmountCurrency", description = "Payed Amount Currency")
    String payedAmountCurrency;
    @ManyToOne(fetch = FetchType.LAZY)
    Worker worker;
}
