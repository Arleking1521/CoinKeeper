package com.example.CoinKeeper.services;

import com.example.CoinKeeper.models.Forex;
import com.example.CoinKeeper.repositories.ForexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForexService {
    private final ForexRepository forexRepository;

    @Autowired
    public ForexService(ForexRepository forexRepository) {
        this.forexRepository = forexRepository;
    }

    public Forex findById(Long id){ return forexRepository.getOne(id); }

    public List<Forex> findAll(){ return forexRepository.findAll(); }

    public void saveForex(Forex forex){ forexRepository.save(forex); }

    public void deleteById(Long id){ forexRepository.deleteById(id); }
}
