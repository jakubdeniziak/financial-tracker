package com.jakubdeniziak.financialtracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity(name = "Transaction")
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private BigDecimal quantity;

    private BigDecimal pricePerUnit;

    private String currency;

    private ZonedDateTime executedAt;

    private String notes;

    @ManyToOne
    @JoinColumn(name = "asset_id", nullable = false)
    private AssetEntity asset;

}
