package com.example.CoinKeeper.repositories;

import com.example.CoinKeeper.models.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {
}
