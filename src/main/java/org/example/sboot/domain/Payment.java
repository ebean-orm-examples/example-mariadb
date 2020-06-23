package org.example.sboot.domain;

import io.ebean.annotation.History;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@History
@Table(name = "worker_payment")
public class Payment extends BaseModel {
    //@Column(precision = 20, scale = 20, columnDefinition = "DECIMAL(20,20)")
    // When adding precision and scale i get an error saving
    private BigDecimal amount;
    String currency;
    Timestamp payedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    Worker worker;
}
