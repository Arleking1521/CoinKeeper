package com.example.CoinKeeper.repositories;

import com.example.CoinKeeper.models.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<Income, Long> {
}
