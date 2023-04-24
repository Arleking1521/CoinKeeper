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
@Table(name = "income")
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "balance")
    private Integer balance;

    @Column(name = "plans")
    private Integer plans;

    @ManyToOne(fetch = FetchType.EAGER)
    private Images images;

    @ManyToOne(fetch = FetchType.EAGER)
    private Forex forex;
}
