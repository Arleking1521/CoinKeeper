package com.example.CoinKeeper.repositories;

import com.example.CoinKeeper.models.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsRepository extends JpaRepository<Accounts, Long> {
}
