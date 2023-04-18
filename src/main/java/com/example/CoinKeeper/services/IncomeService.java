package com.example.CoinKeeper.services;

import com.example.CoinKeeper.models.Income;
import com.example.CoinKeeper.repositories.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeService {
    private final IncomeRepository incomeRepository;

    @Autowired
    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    public Income findById(Long id){ return incomeRepository.getOne(id); }

    public List<Income> findAll(){ return incomeRepository.findAll(); }

    public void saveIncomes(Income income){ incomeRepository.save(income); }

    public void deleteById(Long id){ incomeRepository.deleteById(id); }

}
