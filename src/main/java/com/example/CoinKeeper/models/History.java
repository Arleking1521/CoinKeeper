package com.example.CoinKeeper.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "records")

public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_name")
    private String from;

    @Column(name = "to_name")
    private String to;

    @ManyToOne(fetch = FetchType.EAGER)
    private Forex forex;

    @Column(name = "summa")
    private Float sum;

    @Column(name = "date_rec")
    private String date;

    @Column(name = "comm")
    private String comment;
}
