package com.example.CoinKeeper.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Currency;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "expenses")
public class Expenses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "balance")
    private Float balance;

    @Column(name = "plans")
    private Float plans;

    @ManyToOne(fetch = FetchType.EAGER)
    private Images images;

    @ManyToOne(fetch = FetchType.EAGER)
    private Forex forex;
}
