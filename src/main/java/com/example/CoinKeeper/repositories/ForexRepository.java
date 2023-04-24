package com.example.CoinKeeper.repositories;

import com.example.CoinKeeper.models.Forex;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForexRepository  extends JpaRepository<Forex, Long> {
}
