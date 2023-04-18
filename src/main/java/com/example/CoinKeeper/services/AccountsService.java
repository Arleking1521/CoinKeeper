package com.example.CoinKeeper.services;

import com.example.CoinKeeper.models.Accounts;
import com.example.CoinKeeper.repositories.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountsService {
    private final AccountsRepository accountsRepository;

    @Autowired
    public AccountsService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    public Accounts findById(Long id){ return accountsRepository.getOne(id); }

    public List<Accounts> findAll(){ return accountsRepository.findAll(); }

    public void saveAccounts(Accounts accounts){ accountsRepository.save(accounts); }

    public void deleteById(Long id){ accountsRepository.deleteById(id); }

}
