package com.example.CoinKeeper.repositories;

import com.example.CoinKeeper.models.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expenses, Long> {
}
