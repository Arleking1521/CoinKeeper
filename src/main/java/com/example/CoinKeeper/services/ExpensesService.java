package com.example.CoinKeeper.services;

import com.example.CoinKeeper.models.Expenses;
import com.example.CoinKeeper.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpensesService {
    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpensesService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expenses findById(Long id){ return expenseRepository.getOne(id); }

    public List<Expenses> findAll(){ return expenseRepository.findAll(); }

    public void saveExpenses(Expenses expense){ expenseRepository.save(expense); }

    public void deleteById(Long id){ expenseRepository.deleteById(id); }

}
